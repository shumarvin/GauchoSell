package cs48.g05.bbc2016.gauchosell.item;

import java.util.Date;

/**
 * Created by icema_000 on 5/27/2016.
 */
public class Message {

    private String username;
    private String itemName;
    private double price;
    private String messageDetails;
    private long priority;

    public Message(){}
    public Message(String username, String itemName, double price, String messageDetails) {
        this.username = username;
        this.itemName = itemName;
        this.price = price;
        this.messageDetails = messageDetails;
        //Used for easy access to messages/ordering purposes
        long time = new Date().getTime();
        this.priority= (-1)*(time);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getMessageDetails() {
        return messageDetails;
    }

    public void setMessageDetails(String messageDetails) {
        this.messageDetails = messageDetails;
    }

    public long getPriority() {
        return priority;
    }

    public void setPriority(long priority) {
        this.priority = priority;
    }

}
