package healthcenter;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

import healthcenter.external.PaymentHistory;

@Entity
@Table(name="Order_table")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String orderType;
    private Long cardNo;
    private String name;
    private String status;

    @PostPersist
    public void onPostPersist(){
        Ordered ordered = new Ordered();
        BeanUtils.copyProperties(this, ordered);
        ordered.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

//        teamtwohotel.external.PaymentHistory paymentHistory = new teamtwohotel.external.PaymentHistory();
        PaymentHistory payment = new PaymentHistory();
        System.out.println("this.id() : " + this.id);
        payment.setOrderId(this.id);
        payment.setStatus("Reservation OK");
        // mappings goes here
        OrderApplication.applicationContext.getBean(healthcenter.external.PaymentHistoryService.class)
            .pay(payment);


    }

    @PostUpdate
    public void onPostUpdate(){
    	System.out.println("Order Cancel  !!");
        OrderCanceled orderCanceled = new OrderCanceled();
        BeanUtils.copyProperties(this, orderCanceled);
        orderCanceled.publishAfterCommit();


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
    public Long getCardNo() {
        return cardNo;
    }

    public void setCardNo(Long cardNo) {
        this.cardNo = cardNo;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
