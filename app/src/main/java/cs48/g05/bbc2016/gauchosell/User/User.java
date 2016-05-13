package cs48.g05.bbc2016.gauchosell.user;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.client.Firebase;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
        Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL);
        HashMap<String, Object> userItemMapping = new HashMap<String, Object>();
        Item newItem = new Item(itemDescription);
        HashMap<String, Object> newItemMap = (HashMap<String, Object>) new ObjectMapper().convertValue(newItem, Map.class);
        userItemMapping.put("/"+ Constants.FIREBASE_LOCATION_ITEMS + "/" + itemDescription.getTitle(), newItemMap);
        firebaseRef.updateChildren(userItemMapping);
        return true;
    }
    public boolean bidItem(Timestamp time, String username, UUID itemID, double amount){
        return true;
    }
    public boolean createAccount(String firstName, String lastName, String username, EmbeddedImage profilePicture, int birthMonth,
                                 int birthYear, char[]password, String email){
        return true;
    }
    public boolean deleteItem(Item item){
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
