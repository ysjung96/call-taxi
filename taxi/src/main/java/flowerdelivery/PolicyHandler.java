package flowerdelivery;

import flowerdelivery.config.kafka.KafkaProcessor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @Autowired
    TaxiRepository itemRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaid_StockChange(@Payload Paid paid){
        if(paid.isMe()){
            System.out.println("\n\n##### listener StockChange : " + paid.toJson() + "\n\n");

            Optional<Taxi> itemOptional = itemRepository.findById(paid.getItemId()) ;
            Taxi item = itemOptional.get();
            item.setStockCnt( item.getStockCnt() - paid.getQty() );
            
            if(item.getStockCnt() < 0 ) {
                System.out.println("@@@@@@@ out of stock ");

                // 아이템 재고가 충분치 않을 경우  재고부족 이벤트 생성

                TaxiOutOfStock itemOutOfStock = new TaxiOutOfStock();
                itemOutOfStock.setId(paid.getItemId());
                itemOutOfStock.setOrderId(paid.getOrderId());
                itemOutOfStock.publish();
                
            } else {
                itemRepository.save(item);
            }
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderCancelled_CancelOrder(@Payload OrderCancelled orderCancelled){

        if(orderCancelled.isMe()){
            System.out.println("\n\n##### listener CancelOrder : " + orderCancelled.toJson() + "\n\n");

            Optional<Taxi> itemOptional = itemRepository.findById(orderCancelled.getItemId());
            Taxi item = itemOptional.get();

            if ( item.getStockCnt() - orderCancelled.getQty()  > 0 ) {
                item.setStockCnt(item.getStockCnt() - orderCancelled.getQty());
                itemRepository.save(item);
            }
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}
