package cs48.g05.bbc2016.gauchosell.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.client.Firebase;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cs48.g05.bbc2016.gauchosell.util.GauchoSell;
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
    public User() {}

    public void loginUser(Account account){
        this.myAccount = account;
    }

    public void setAccount(Account myAccount) { this.myAccount = myAccount; }
    public Account getAccount() { return myAccount; }
    public void postItem(ItemInformation itemDescription){
        //Creates an Item object and  puts it in the database
        Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL + "/" +Constants.FIREBASE_LOCATION_ITEMS);
        HashMap<String, Object> userItemMapping = new HashMap<String, Object>();
        Item newItem = new Item(itemDescription);
        HashMap<String, Object> newItemMap = (HashMap<String, Object>) new ObjectMapper().convertValue(newItem, Map.class);

        //Time Created identifies the item. It is used so we can differentiate between item (like if they have the same name)
        userItemMapping.put("" + newItem.getTimeCreated(), newItemMap);
        firebaseRef.updateChildren(userItemMapping);
    }
    public void bidItem(Timestamp time, String username, Item item, double amount, String emails){
        followItem(item, username);
        Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL + "/" + Constants.FIREBASE_LOCATION_ITEMS + "/" + item.getTimeCreated() + "/bids");
        Bid newBid = new Bid(time, username, amount, emails);
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
    }
    public void deleteItem(Item item){
        Firebase firebaseRef=new Firebase(Constants.FIREBASE_URL+"/"+Constants.FIREBASE_LOCATION_ITEMS+"/"+item.getTimeCreated());
        firebaseRef.removeValue();
    }
    public void likeItem(Item item, String username){
        Firebase firebaseRef=new Firebase(Constants.FIREBASE_URL+"/"+Constants.FIREBASE_LOCATION_ITEMS+"/"+item.getTimeCreated()+"/likers");
        HashMap<String, String> likeMapping = new HashMap<String, String>();
        HashMap<String, Object> newlikeMap = new HashMap<String, Object>();
        likeMapping.put("username", username);
        newlikeMap.put(username, likeMapping);
        firebaseRef.updateChildren(newlikeMap);
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
    }
    public void deleteMessage(Message message){
        String email = GauchoSell.user.getAccount().getEmail().replace(".",",");
        Firebase firebaseRef=new Firebase(Constants.FIREBASE_URL+"/users/"+email+"/messages/"+message.getPriority());
        firebaseRef.removeValue();
    }
    public void unfollowItem(Item item){
        Firebase firebaseRef=new Firebase(Constants.FIREBASE_URL+"/"+Constants.FIREBASE_LOCATION_ITEMS+"/"+item.getTimeCreated()+"/likers/" +
                GauchoSell.user.getAccount().getUsername());
        firebaseRef.removeValue();
    }
}
