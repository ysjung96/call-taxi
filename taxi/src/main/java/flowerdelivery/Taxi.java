package flowerdelivery;

import javax.persistence.*;

import org.springframework.beans.BeanUtils;

@Entity
@Table(name = "Taxi_table")
public class Taxi {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String taxiName;
    private String companyName;
    private Integer stockCnt;
    private Long taxiPrice;

    @PostPersist
    public void onPostPersist() {
        TaxiRegistered itemRegistered = new TaxiRegistered();
        BeanUtils.copyProperties(this, itemRegistered);
        itemRegistered.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate() {
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

    public String getTaxiName() {
        return taxiName;
    }

    public void setTaxiName(String taxiName) {
        this.taxiName = taxiName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setStoreName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getStockCnt() {
        return stockCnt;
    }

    public void setStockCnt(Integer stockCnt) {
        this.stockCnt = stockCnt;
    }

    public Long getItemPrice() {
        return taxiPrice;
    }

    public void setItemPrice(Long itemPrice) {
        this.taxiPrice = itemPrice;
    }
}
