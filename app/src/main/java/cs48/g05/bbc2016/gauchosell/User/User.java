package cs48.g05.bbc2016.gauchosell.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.client.Firebase;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import cs48.g05.bbc2016.gauchosell.GauchoSell;
import cs48.g05.bbc2016.gauchosell.feeds.FollowingFeed;
import cs48.g05.bbc2016.gauchosell.feeds.MyBidsFeed;
import cs48.g05.bbc2016.gauchosell.feeds.MyItemsFeed;
import cs48.g05.bbc2016.gauchosell.item.Bid;
import cs48.g05.bbc2016.gauchosell.item.Item;
import cs48.g05.bbc2016.gauchosell.item.ItemInformation;
import cs48.g05.bbc2016.gauchosell.item.Message;
import cs48.g05.bbc2016.gauchosell.util.Constants;

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
    public boolean bidItem(Timestamp time, String username, Item item, double amount, String emails){
        followItem(item, username);
        Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL + "/" + Constants.FIREBASE_LOCATION_ITEMS + "/" + item.getTimeCreated() + "/bids");
        Bid newBid = new Bid(time, username, amount, item.getItemID(), emails);
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
    public boolean createAccount(String firstName, String lastName, String username,int birthMonth,
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
    public boolean likeItem(Item item, String username){
        Firebase firebaseRef=new Firebase(Constants.FIREBASE_URL+"/"+Constants.FIREBASE_LOCATION_ITEMS+"/"+item.getTimeCreated()+"/likers");
        HashMap<String, String> likeMapping = new HashMap<String, String>();
        HashMap<String, Object> newlikeMap = new HashMap<String, Object>();
        likeMapping.put("username", username);
        newlikeMap.put(username, likeMapping);
        firebaseRef.updateChildren(newlikeMap);
        return true;
    }
    public void followItem(Item item, String username){
        Firebase firebaseRef=new Firebase(Constants.FIREBASE_URL+"/"+Constants.FIREBASE_LOCATION_ITEMS+"/"+item.getTimeCreated()+"/followers");
        HashMap<String, String> followMapping = new HashMap<String, String>();
        HashMap<String, Object> newfollowMap = new HashMap<String, Object>();
        followMapping.put("username", username);
        newfollowMap.put(username, followMapping);
        firebaseRef.updateChildren(newfollowMap);
    }
    public void chooseWinningBid(String email, String username, String itemName, double price, String messageDetails){
        String email2 = email.replace(".",",");
        Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL + "/users/" + email2 +"/messages");
        HashMap<String, Object> userMessageMapping = new HashMap<String, Object>();
        Message message=new Message(username, itemName, price, messageDetails);
        HashMap<String, Object> newMessageMap = (HashMap<String, Object>) new ObjectMapper().convertValue(message, Map.class);

        //Time Created identifies the item. It is used so we can differentiate between item (like if they have the same name)
        userMessageMapping.put("" + message.getPriority(), newMessageMap);
        firebaseRef.updateChildren(userMessageMapping);

        //firebaseRef.push().setValue(message);
    }
    public void deleteMessage(Message message){
        String email = GauchoSell.user.getAccount().getEmail().replace(".",",");
        Firebase firebaseRef=new Firebase(Constants.FIREBASE_URL+"/users/"+email+"/messages/"+message.getPriority());
        firebaseRef.removeValue();
    }
    public boolean unfollowItem(Item item){
        Firebase firebaseRef=new Firebase(Constants.FIREBASE_URL+"/"+Constants.FIREBASE_LOCATION_ITEMS+"/"+item.getTimeCreated()+"/likers/" +
                GauchoSell.user.getAccount().getUsername());
        firebaseRef.removeValue();
        return true;
    }
    public boolean cancelBid(Bid bid){
        return true;
    }
    public boolean deleteAccount(){
        return true;
    }
    public boolean updateAccountInfo(String firstName, String lastName, String username, int birthMonth,
                                     int birthYear, char[]password, String email){return true;}
    public boolean updateItemInfo(ItemInformation itemDescription, String saleStatus, UUID itemID, Bid[]bids){return true;}

}
