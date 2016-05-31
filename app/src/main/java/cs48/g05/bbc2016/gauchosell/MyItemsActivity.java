package cs48.g05.bbc2016.gauchosell;

import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

import java.util.ArrayList;
import java.util.UUID;

import cs48.g05.bbc2016.gauchosell.item.Bid;
import cs48.g05.bbc2016.gauchosell.item.Item;
import cs48.g05.bbc2016.gauchosell.util.Constants;

/**
 * Created by icema_000 on 5/13/2016.
 */
public class MyItemsActivity extends FeedsActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView list = (ListView) findViewById(R.id.listView);

        firebaseRef = new Firebase(Constants.FIREBASE_URL + "/" + Constants.FIREBASE_LOCATION_ITEMS);
        Query queryRef = firebaseRef.orderByChild("itemDescription/seller").equalTo(GauchoSell.user.getAccount().getUsername());
        list.setAdapter(initializeFeed(queryRef));

    }
    @Override
    public FirebaseListAdapter<Item> initializeFeed(Query queryRef){
        FirebaseListAdapter<Item>adapter = new FirebaseListAdapter<Item>(this, Item.class, R.layout.myitem_post_layout, queryRef){
            @Override
            protected void populateView(View v, Item item, int i) {

                TextView title = (TextView) v.findViewById(R.id.title);
                title.setText(item.getItemDescription().getTitle());

                TextView category = (TextView) v.findViewById(R.id.category);
                category.setText(item.getItemDescription().getCategory());

                TextView description = (TextView) v.findViewById(R.id.description);
                description.setText(item.getItemDescription().getDescription());

                TextView price = (TextView) v.findViewById(R.id.price);
                price.setText(item.getItemDescription().priceToString());

                TextView seller = (TextView) v.findViewById(R.id.seller_name);
                seller.setText(item.getItemDescription().getSeller());

                TextView asking_price = (TextView) v.findViewById(R.id.asking_price);
                asking_price.setText("Asking Price:");

                TextView highestBidText = (TextView) v.findViewById(R.id.highestBidText);
                highestBidText.setText("Highest Bid:");

                //If there are no bids, display the highest bid as: $0.00
                if (item.getBids().size() == 0) {
                    TextView highestBid = (TextView) v.findViewById(R.id.highestBid);
                    highestBid.setText("$0.00");
                }
                //Find the highestBid in the item's list of bids and set the TextView to the amount
                else {
                    for (int j = 0; j < item.getBids().size(); j++) {
                        if (item.getBids().get(j).getHighestBid() == true) {
                            TextView highestBid = (TextView) v.findViewById(R.id.highestBid);
                            highestBid.setText(item.getBids().get(j).amountToString());
                        }
                    }
                }

                TextView saleStatus = (TextView) v.findViewById(R.id.saleStatus);
                saleStatus.setText(item.getSaleStatus());
                

                ImageView itemImage=(ImageView)v.findViewById(R.id.image);
                byte[] imageAsBytes= Base64.decode(item.getItemDescription().getImage().getBytes(), Base64.DEFAULT);
                itemImage.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));

                final Item item2 = item;
                ImageButton deleteItem = (ImageButton) v.findViewById(R.id.delete_post);
                deleteItem.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        GauchoSell.user.deleteItem(item2);
                    }
                });

                //When you click the View Bids button, it brings you to a dialog box with the list
                //of bidders and the amount bid.
                Button winningBid = (Button) v.findViewById(R.id.view_bid_button);
                winningBid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog dialog = new Dialog(MyItemsActivity.this);
                        dialog.setTitle("Select Winning Bidder");
                        dialog.setContentView(R.layout.pick_winning_bid);
                        dialog.show();
                        final ListView list2=(ListView)dialog.findViewById(R.id.bids_list_view);
                        list2.setAdapter(initializeBidView(item2));

                        contactBuyer(list2, item2.getItemDescription().getTitle(), item2.getItemDescription().getPrice());

                    }
                });
            }
        };
        return adapter;
    }
    public ArrayAdapter<Bid> initializeBidView(Item item) {
        final ArrayList<Bid> bids = item.getBids();
        ArrayAdapter<Bid> BidsAdapter = new ArrayAdapter<Bid>(this, R.layout.list_item_2, bids) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.list_item_2, null);
                }
                TextView winningBidUsername = (TextView) convertView.findViewById(R.id.text1);
                winningBidUsername.setText(bids.get(position).getUsername());

                TextView winningBidAmount = (TextView) convertView.findViewById(R.id.text2);
                winningBidAmount.setText(bids.get(position).amountToString());

                TextView winningBidEmail = (TextView) convertView.findViewById(R.id.bid_email);
                winningBidEmail.setText(bids.get(position).getEmail());

                return convertView;
            }
        };
        return BidsAdapter;
    }
    //This methods brings up another dialog box when the bidder has been selected. This is where
    //you can send a message to the bidder detailing your contact information.
    public void contactBuyer(final ListView list, final String itemTitle, final double itemPrice){
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ViewGroup vg=(ViewGroup)view;
                final TextView username=(TextView)vg.findViewById(R.id.text1);
                final TextView email=(TextView)vg.findViewById(R.id.bid_email);
                Dialog dialog = new Dialog(MyItemsActivity.this);
                dialog.setTitle("Message: " + username.getText().toString());
                dialog.setContentView(R.layout.message_bidder);
                dialog.show();

                final Dialog dialog2=dialog;

                Button messageButton=(Button)dialog.findViewById(R.id.send_message_button);
                messageButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        EditText message=(EditText)dialog2.findViewById(R.id.message_bidder_text);
                        String message_string=message.getText().toString();
                        GauchoSell.user.chooseWinningBid(email.getText().toString(), GauchoSell.user.getAccount().getUsername(), itemTitle, itemPrice, message_string);
                        dialog2.dismiss();
                    }
                });
            }
        });
    }

}
