package flowerdelivery;

public class StockChanged extends AbstractEvent {

    private Long id;

    public StockChanged(){
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
