package cs48.g05.bbc2016.gauchosell;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

import java.sql.Timestamp;
import java.util.Date;



import cs48.g05.bbc2016.gauchosell.item.Item;
import cs48.g05.bbc2016.gauchosell.util.Constants;

/**
 * Created by icema_000 on 5/13/2016.
 */
public class FeedsActivity extends BaseActivity {
    protected Firebase firebaseRef;
    protected String category="No Category";
    protected ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Initialize the menu for categories
        initializeCategoryDropDown();

        //set G logo
        //Typeface logoFont = Typeface.createFromAsset(getAssets(), "The Heart Maze Demo.ttf");
        //TextView logoGTextView = (TextView)findViewById(R.id.g_logo);
        //logoGTextView.setTypeface(logoFont);


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
        String email=GauchoSell.user.getAccount().getEmail();
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
                TextView title=(TextView)v.findViewById(R.id.title);
                title.setText(item.getItemDescription().getTitle());

                TextView category=(TextView)v.findViewById(R.id.category);
                category.setText(item.getItemDescription().getCategory());

                TextView description=(TextView)v.findViewById(R.id.description);
                description.setText(item.getItemDescription().getDescription());

                TextView price=(TextView)v.findViewById(R.id.price);
                price.setText(item.getItemDescription().priceToString());

                TextView seller=(TextView)v.findViewById(R.id.seller_name);
                seller.setText(item.getItemDescription().getSeller());

                TextView asking_price=(TextView)v.findViewById(R.id.asking_price);
                asking_price.setText("Asking Price:");

                TextView highestBidText=(TextView)v.findViewById(R.id.highestBidText);
                highestBidText.setText("Highest Bid:");

                //If there are no bids, display the highest bid as: $0.00
                if(item.getBids().size()==0){
                    TextView highestBid = (TextView)v.findViewById(R.id.highestBid);
                    highestBid.setText("$0.00");
                }
                //Find the highestBid in the item's list of bids and set the TextView to the amount
                else {
                    for(int j=0; j<item.getBids().size(); j++){
                        if(item.getBids().get(j).getHighestBid()==true){
                            TextView highestBid = (TextView)v.findViewById(R.id.highestBid);
                            highestBid.setText(item.getBids().get(j).amountToString());
                        }
                    }

                }

                TextView saleStatus=(TextView)v.findViewById(R.id.saleStatus);
                saleStatus.setText(item.getSaleStatus());

                //Initialize + button to add bid
                final EditText postBidItem = (EditText) v.findViewById(R.id.amount);
                Button addBidButton = (Button) v.findViewById(R.id.addBid);
                addBidButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        onSetBidClicked (view, item, postBidItem);
                    }
                });

                //Initialize like button to like items
                likeBehavior(item, v);

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
    public void likeBehavior(Item item, View v){
        final Item item2=item;
        final String username=GauchoSell.user.getAccount().getUsername();
        final ImageButton likeButton = (ImageButton) v.findViewById(R.id.like_button);
        likeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                likeButton.setBackgroundResource(R.drawable.favorite);
                GauchoSell.user.likeItem(item2, username);
            }
        });
    }
}



