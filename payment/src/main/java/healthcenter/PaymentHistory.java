package healthcenter;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

@Entity
@Table(name="PaymentHistory_table")
public class PaymentHistory {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long orderId;
    private Long cardNo;
    private String status;

    @PrePersist
    public void onPrePersist(){
        try{
            Thread.sleep((long)(Math.random() * 1000));
       } catch (InterruptedException e){
           e.printStackTrace();
       }
    }

    @PostPersist
    public void onPostPersist() {
        try {
            File file = new File("/mnt/efs/LOGS");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write("OS 환경변수 MIN_TAX_PRICE 값: " + System.getenv("MIN_TAX_PRICE"));
            writer.write("\n");
            writer.write("OS 환경변수 PAYMENT_GATEWAY_SERVER 값: " + System.getenv("PAYMENT_GATEWAY_SERVER"));
            writer.write("\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }      

        PaymentApproved paymentApproved = new PaymentApproved();
        paymentApproved.setStatus("Pay Approved!!");
        BeanUtils.copyProperties(this, paymentApproved);
        paymentApproved.publishAfterCommit();
    }
    
    @PostUpdate
    public void onPostUpdate() {
    	PaymentCanceled paymentCanceled = new PaymentCanceled();
    	BeanUtils.copyProperties(this, paymentCanceled);
    	paymentCanceled.publishAfterCommit();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public Long getCardNo() {
        return cardNo;
    }

    public void setCardNo(Long cardNo) {
        this.cardNo = cardNo;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
