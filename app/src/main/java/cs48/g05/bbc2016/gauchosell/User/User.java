package cs48.g05.bbc2016.gauchosell.user;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.client.Firebase;
import com.firebase.client.ServerValue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import cs48.g05.bbc2016.gauchosell.GauchoSell;
import cs48.g05.bbc2016.gauchosell.feeds.FollowingFeed;
import cs48.g05.bbc2016.gauchosell.feeds.MyBidsFeed;
import cs48.g05.bbc2016.gauchosell.feeds.MyItemsFeed;
import cs48.g05.bbc2016.gauchosell.item.Bid;
import cs48.g05.bbc2016.gauchosell.util.Constants;
import cs48.g05.bbc2016.gauchosell.util.EmbeddedImage;
import cs48.g05.bbc2016.gauchosell.item.Item;
import cs48.g05.bbc2016.gauchosell.item.ItemInformation;

/**
 * Created by dav on 4/17/16.
 */


public class User {
    private Account myAccount;
    private MyBidsFeed myBids;
    private MyItemsFeed myItems;
    private FollowingFeed following;

    public User() {}

    public void loginUser(Account account, MyBidsFeed bids, MyItemsFeed items, FollowingFeed following){ //TODO: may not need this
        this.myAccount = account;
        this.myBids = bids;
        this.myItems = items;
        this.following = following;
    }

    public void setAccount(Account myAccount) { this.myAccount = myAccount; }
    public Account getAccount() { return myAccount; }
    public boolean postItem(ItemInformation itemDescription){
        //Creates an Item object and  puts it in the database
        Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL + "/" +Constants.FIREBASE_LOCATION_ITEMS);
        HashMap<String, Object> userItemMapping = new HashMap<String, Object>();
        Item newItem = new Item(itemDescription);
        HashMap<String, Object> newItemMap = (HashMap<String, Object>) new ObjectMapper().convertValue(newItem, Map.class);

        //Time Created identifies the item. It is used so we can differentiate between item (like if they have the same name)
        userItemMapping.put("" + newItem.getTimeCreated(), newItemMap);
        firebaseRef.updateChildren(userItemMapping);

        return true;
    }
    public boolean bidItem(Timestamp time, String username, Item item, double amount){
        Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL + "/" + Constants.FIREBASE_LOCATION_ITEMS + "/" + item.getTimeCreated() + "/bids");
        Bid newBid = new Bid(time, username, amount, item.getItemID());
        //Get item's arrayList of bids
        ArrayList<Bid>bids=item.getBids();

        //If there are no bids, set this bid to the highest
        if(bids.size()==0){ newBid.setHighestBid(true); }
        //If the amount is greater than the highestBid, change it.
        for(int i=0; i<bids.size(); i++){
            if(bids.get(i).getHighestBid()==true && amount>bids.get(i).getAmount()){
                bids.get(i).setHighestBid(false);
                newBid.setHighestBid(true);
            }
        }

        //Put the bid into the database
        bids.add(newBid);
        firebaseRef.setValue(bids);

        return true;
    }
    public boolean createAccount(String firstName, String lastName, String username, EmbeddedImage profilePicture, int birthMonth,
                                 int birthYear, char[]password, String email){
        return true;
    }
    public boolean deleteItem(Item item){
        Firebase firebaseRef=new Firebase(Constants.FIREBASE_URL+"/"+Constants.FIREBASE_LOCATION_ITEMS+"/"+item.getTimeCreated());
        firebaseRef.removeValue();
        return true;
    }
    public boolean repostItem(Item item){
        return true;
    }
    public boolean followItem(Item item){
        return true;
    }
    public void chooseWinningBid(Bid bid){}
    public boolean unfollowItem(Item item){
        return true;
    }
    public boolean cancelBid(Bid bid){
        return true;
    }
    public boolean deleteAccount(){
        return true;
    }
    public boolean updateAccountInfo(String firstName, String lastName, String username, EmbeddedImage profilePicture, int birthMonth,
                                     int birthYear, char[]password, String email){return true;}
    public boolean updateItemInfo(ItemInformation itemDescription, String saleStatus, UUID itemID, Bid[]bids){return true;}

}
