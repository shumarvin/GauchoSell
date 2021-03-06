package cs48.g05.bbc2016.gauchosell.item;

import java.text.NumberFormat;

/**
 * Created by icema_000 on 4/29/2016.
 */
public class ItemInformation {
    private double price;
    private String title;
    private String category;
    private String image;
    private String description;
    private String seller;

    public ItemInformation(){}

    public ItemInformation(double price, String title, String category, String image, String description, String seller) {
        this.price = price;
        this.title = title;
        this.category = category;
        this.image = image;
        this.description = description;
        this.seller = seller;
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

    public void setCategory(String category) { this.category = category; }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    //toString puts the Bid into money form: $9.53
    public String priceToString() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(price);
    }

    public String getSeller() { return seller; }

    public void setSeller(String seller) { this.seller = seller; }
}
