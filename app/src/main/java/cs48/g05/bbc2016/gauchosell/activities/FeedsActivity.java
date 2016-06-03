package cs48.g05.bbc2016.gauchosell.activities;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

import java.sql.Timestamp;
import java.util.Date;

import cs48.g05.bbc2016.gauchosell.R;
import cs48.g05.bbc2016.gauchosell.item.Item;
import cs48.g05.bbc2016.gauchosell.util.GauchoSell;

/**
 * Created by icema_000 on 5/13/2016.
 */
public class FeedsActivity extends BaseActivity {

    static class ItemViewHolder {
        TextView title;
        TextView category;
        TextView description;
        TextView price;

        TextView seller;

        TextView asking_price;

        TextView highestBidText;
        TextView highestBid;

        TextView saleStatus;

        ImageView itemImage;

        EditText postBidItem;

        ImageButton likeButton;
    }
    protected Firebase firebaseRef;
    protected String category="No Category";
    protected ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Initialize the menu for categories
        initializeCategoryDropDown();

        //intent to home
        ImageButton homeButton = (ImageButton) findViewById(R.id.home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onHomeClicked(view);
            }
        });

        //intent to post item
        ImageButton addPostButton = (ImageButton) findViewById(R.id.add_post);
        addPostButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onAddPostClicked(view);
            }
        });

        //intent to settings
        ImageButton settingsButton = (ImageButton) findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onSettingsClicked(view);
            }
        });

        //intent to myItems
        ImageButton myItemsButton = (ImageButton) findViewById(R.id.myitems_folder);
        myItemsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onMyItemsClicked(view);
            }
        });
        //intent to myBids
        ImageButton myBidsButton = (ImageButton) findViewById(R.id.mybids_button);
        myBidsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onMyBidsClicked(view);
            }
        });
        //intent to myBids
        ImageButton followingButton = (ImageButton) findViewById(R.id.like_button);
        followingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onFollowingClicked(view);
            }
        });

        ImageButton messagesButton = (ImageButton) findViewById(R.id.notificationsButton);
        messagesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onMessagesClicked(view);
            }
        });
    }
    public void onHomeClicked(View view) {
        Intent intent = new Intent(getBaseContext(), HomeActivity.class);
        startActivity(intent);
    }

    public void onAddPostClicked (View view) {
        Intent intent = new Intent(getBaseContext(), PostItemActivity.class);
        startActivity(intent);
    }

    public void onMessagesClicked (View view) {
        Intent intent = new Intent(getBaseContext(), MessagesActivity.class);
        startActivity(intent);
    }

    public void onSettingsClicked(View view) {
        Intent intent = new Intent(getBaseContext(), SettingsActivity.class);
        startActivity(intent);
    }

    public void onMyItemsClicked(View view) {
        Intent intent = new Intent(getBaseContext(), MyItemsActivity.class);
        startActivity(intent);
    }

    public void onMyBidsClicked(View view) {
        Intent intent = new Intent(getBaseContext(), MyBidsActivity.class);
        startActivity(intent);
    }

    public void onFollowingClicked(View view) {
        Intent intent = new Intent(getBaseContext(), FollowingActivity.class);
        startActivity(intent);
    }

    public void onSetBidClicked(View view, Item item, EditText postBid){
        String postBidItemString = postBid.getText().toString();
        String email= GauchoSell.user.getAccount().getEmail();
        Date date = new java.util.Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        double bidAmountDouble = Double.parseDouble(postBidItemString);
        GauchoSell.user.bidItem(timestamp, GauchoSell.user.getAccount().getUsername(), item, bidAmountDouble, email);
    }

    //This populates the list view with items. Creates an adapter that contacts Firebase, populates the listView,
    //and returns the adapter
    public FirebaseListAdapter<Item> initializeFeed(Query queryRef){
        FirebaseListAdapter<Item>adapter = new FirebaseListAdapter<Item>(this, Item.class, R.layout.post_layout, queryRef){
            @Override
            protected void populateView(View v, final Item item, int i){
                ItemViewHolder viewHolder = new ItemViewHolder();
                viewHolder.title=(TextView)v.findViewById(R.id.title);
                viewHolder.title.setText(item.getItemDescription().getTitle());

                viewHolder.category=(TextView)v.findViewById(R.id.category);
                viewHolder.category.setText(item.getItemDescription().getCategory());

                viewHolder.description=(TextView)v.findViewById(R.id.description);
                viewHolder.description.setText(item.getItemDescription().getDescription());

                viewHolder.price=(TextView)v.findViewById(R.id.price);
                viewHolder.price.setText(item.getItemDescription().priceToString());

                viewHolder.seller=(TextView)v.findViewById(R.id.seller_name);
                viewHolder.seller.setText(item.getItemDescription().getSeller());

                viewHolder.asking_price=(TextView)v.findViewById(R.id.asking_price);
                viewHolder.asking_price.setText("Asking Price:");

                viewHolder.highestBidText=(TextView)v.findViewById(R.id.highestBidText);
                viewHolder.highestBidText.setText("Highest Bid:");

                //If there are no bids, display the highest bid as: $0.00
                if(item.getBids().size()==0){
                    viewHolder.highestBid = (TextView)v.findViewById(R.id.highestBid);
                    viewHolder.highestBid.setText("$0.00");
                }
                //Find the highestBid in the item's list of bids and set the TextView to the amount
                else {
                    for(int j=0; j<item.getBids().size(); j++){
                        if(item.getBids().get(j).getHighestBid()==true){
                            viewHolder.highestBid = (TextView)v.findViewById(R.id.highestBid);
                            viewHolder.highestBid.setText(item.getBids().get(j).amountToString());
                        }
                    }

                }

                viewHolder.itemImage=(ImageView)v.findViewById(R.id.image);
                byte[] imageAsBytes=Base64.decode(item.getItemDescription().getImage().getBytes(), Base64.DEFAULT);
                viewHolder.itemImage.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));

                //Initialize + button to add bid
                viewHolder.postBidItem = (EditText) v.findViewById(R.id.amount);
                final EditText postBidItem1=viewHolder.postBidItem;
                Button addBidButton = (Button) v.findViewById(R.id.addBid);
                addBidButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        onSetBidClicked (view, item, postBidItem1);
                    }
                });

                //Initialize like button to like items
                likeBehavior(item, v, viewHolder);
                v.setTag(viewHolder);
            }
        };
        return adapter;
    }
    //Initialize drop-down category menu
    public void initializeCategoryDropDown(){
        Spinner spinner = (Spinner) findViewById(R.id.spinner_home);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_home, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //set category to the one the user selected
                category = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    public void likeBehavior(Item item, View v, ItemViewHolder viewHolder){
        final Item item2=item;
        final String username=GauchoSell.user.getAccount().getUsername();
        viewHolder.likeButton = (ImageButton) v.findViewById(R.id.like_button);
        final ImageButton likeButton1=viewHolder.likeButton;
        viewHolder.likeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                likeButton1.setBackgroundResource(R.drawable.favorite);
                GauchoSell.user.likeItem(item2, username);
            }
        });
    }
}



