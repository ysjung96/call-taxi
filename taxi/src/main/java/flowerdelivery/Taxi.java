package flowerdelivery;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name="Item_table")
public class Taxi {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String itemName;
    private String storeName;
    private Integer stockCnt;
    private Long itemPrice;

    @PostPersist
    public void onPostPersist(){
        TaxiRegistered itemRegistered = new TaxiRegistered();
        BeanUtils.copyProperties(this, itemRegistered);
        itemRegistered.publishAfterCommit();


    }

    @PostUpdate
    public void onPostUpdate(){
        StockChanged stockChanged = new StockChanged();
        BeanUtils.copyProperties(this, stockChanged);
        stockChanged.publishAfterCommit();


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    public Integer getStockCnt() {
        return stockCnt;
    }

    public void setStockCnt(Integer stockCnt) {
        this.stockCnt = stockCnt;
    }
    public Long getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Long itemPrice) {
        this.itemPrice = itemPrice;
    }




}
