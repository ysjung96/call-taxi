package flowerdelivery;

public class TaxiRegistered extends AbstractEvent {

    private Long id;
    private String taxiName;
    private String companyName;
    private Long itemPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getItemName() {
        return taxiName;
    }

    public void setItemName(String itemName) {
        this.taxiName = itemName;
    }
    public String getStoreName() {
        return companyName;
    }

    public void setStoreName(String storeName) {
        this.companyName = storeName;
    }
    public Long getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Long itemPrice) {
        this.itemPrice = itemPrice;
    }
}