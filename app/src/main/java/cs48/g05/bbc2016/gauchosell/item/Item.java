package cs48.g05.bbc2016.gauchosell.item;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by icema_000 on 4/29/2016.
 */

public class Item {
    private ItemInformation itemDescription;
    private String saleStatus;
    private UUID itemID;
    private ArrayList<Bid> bids;
    private long timeCreated;

    public Item(){}
    public Item(ItemInformation itemDescription) {
        this.itemDescription = itemDescription;
        this.saleStatus = "Not Sold";
        this.itemID = UUID.randomUUID();
        this.bids = new ArrayList<Bid>();
        this.timeCreated=new Date().getTime();
    }

    public ItemInformation getItemDescription() { return itemDescription; }

    public void setItemDescription(ItemInformation itemDescription) { this.itemDescription = itemDescription; }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }

    public UUID getItemID() { return itemID; }

    public void setItemID(UUID itemID) { this.itemID = itemID; }

    public ArrayList<Bid> getBids(){
        return this.bids;
    }

    public long getTimeCreated() { return timeCreated; }

    public void setTimeCreated(long timeCreated) { this.timeCreated = timeCreated; }
}
