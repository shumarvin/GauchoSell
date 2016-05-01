package cs48.g05.bbc2016.gauchosell;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.UUID;

/**
 * Created by icema_000 on 4/29/2016.
 */
public class Bid {
    //private Timestamp date;
    private Timestamp date;
    private String username;
    private double amount;
    private UUID itemId;
  
    public Bid(Timestamp date, String username, double amount, UUID itemId){
        this.date=date;
        this.username=username;
        this.amount=amount;
        this.itemId=itemId;
    }

    public Timestamp getDate() { return date; }

    public void setDate(Timestamp date) { this.date = date; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public double getAmount() { return amount; }

    public void setAmount(double amount) { this.amount = amount; }

    public UUID getItemId() { return itemId; }

    public void setItemId(UUID itemId) { this.itemId = itemId; }

    //toString puts the Bid into money form: $9.53
    public String amountToString(){
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(amount);
    }




}
