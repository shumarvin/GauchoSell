package cs48.g05.bbc2016.gauchosell.item;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by icema_000 on 4/29/2016.
 */
//Using this fixes failed to bounce type error
@JsonIgnoreProperties({"likers", "followers"})
public class Item {
    private ItemInformation itemDescription;
    private ArrayList<Bid> bids;
    private long priority;
    private long timeCreated;

    public Item(){ bids=new ArrayList<>();}
    public Item(ItemInformation itemDescription) {
        long time = new Date().getTime();
        this.itemDescription = itemDescription;
        this.bids = new ArrayList<Bid>();
        this.priority= (-1)*(time); //because of how firebase ordering query works, we need to do this
        this.timeCreated = time;
    }

    public ItemInformation getItemDescription() { return itemDescription; }

    public void setItemDescription(ItemInformation itemDescription) { this.itemDescription = itemDescription; }

    public ArrayList<Bid> getBids(){
        return this.bids;
    }

    public long getPriority() { return priority; }

    public void setPriority(long priority) { this.priority = priority; }

    public long getTimeCreated() { return timeCreated; }

    public void setTimeCreated(long timeCreated) { this.timeCreated = timeCreated; }

    public void addToBidArrayList(Bid bid){ bids.add(bid);}
}
