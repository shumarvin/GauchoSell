package cs48.g05.bbc2016.gauchosell;

/**
 * Created by icema_000 on 4/29/2016.
 */
public class ItemInformation {
    private double price;
    private String title;
    private String category;
    private EmbeddedImage image;
    private String description;
    private Bid highestBid;

    public ItemInformation(double price, String title, String category, EmbeddedImage image, String description, Bid highestBid) {
        this.price = price;
        this.title = title;
        this.category = category;
        this.image = image;
        this.description = description;
        this.highestBid = highestBid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bid getHighestBid() {
        return highestBid;
    }

    public void setHighestBid(Bid highestBid) {
        this.highestBid = highestBid;
    }
}
