package cs48.g05.bbc2016.gauchosell.user;

import java.sql.Timestamp;
import java.util.UUID;

import cs48.g05.bbc2016.gauchosell.feeds.FollowingFeed;
import cs48.g05.bbc2016.gauchosell.feeds.MyBidsFeed;
import cs48.g05.bbc2016.gauchosell.feeds.MyItemsFeed;
import cs48.g05.bbc2016.gauchosell.item.Bid;
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
    public boolean postItem(ItemInformation itemDescription, String saleStatus, UUID itemID, Bid[]bids){
        return true; //TODO: Firebase reference
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
    public void chooseWinningbid(Bid bid){

    }
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
                                     int birthYear, char[]password, String email){
        return true;
    }
    public boolean updateItemInfo(ItemInformation itemDescription, String saleStatus, UUID itemID, Bid[]bids){
        return true;
    }

}
