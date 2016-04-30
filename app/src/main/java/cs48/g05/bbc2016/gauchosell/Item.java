package cs48.g05.bbc2016.gauchosell;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by icema_000 on 4/29/2016.
 */

public class Item {
    private ItemInformation itemDescription;
    private String saleStatus;
    private UUID itemID;
    private ArrayList<Bid> bids;

    public Item(ItemInformation itemDescription, String saleStatus) {
        this.itemDescription = itemDescription;
        this.saleStatus = saleStatus;
        this.itemID = UUID.randomUUID();
        this.bids = new ArrayList<Bid>();
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
}
