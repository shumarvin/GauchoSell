package cs48.g05.bbc2016.gauchosell;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;

import cs48.g05.bbc2016.gauchosell.item.Item;
import cs48.g05.bbc2016.gauchosell.util.Constants;


/**
 * Created by laurendumapias on 4/30/16.
 */
public class HomeActivity extends FeedsActivity {
    private Firebase firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView list = (ListView) findViewById(R.id.listView);

        //FirebaseListAdapter used to create a feed
        firebaseRef = new Firebase(Constants.FIREBASE_URL + "/" + Constants.FIREBASE_LOCATION_ITEMS);
        Query queryRef = firebaseRef.orderByPriority();
        FirebaseListAdapter<Item>adapter = new FirebaseListAdapter<Item>(this, Item.class, R.layout.post_layout, queryRef){
            @Override
            protected void populateView(View v, Item item, int i){

                TextView title=(TextView)v.findViewById(R.id.title);
                title.setText(item.getItemDescription().getTitle());

                TextView category=(TextView)v.findViewById(R.id.category);
                category.setText(item.getItemDescription().getCategory());

                TextView description=(TextView)v.findViewById(R.id.description);
                description.setText(item.getItemDescription().getDescription());

                TextView price=(TextView)v.findViewById(R.id.price);
                price.setText(item.getItemDescription().priceToString());

                TextView asking_price=(TextView)v.findViewById(R.id.asking_price);
                asking_price.setText("Asking Price:");

                TextView highestBidText=(TextView)v.findViewById(R.id.highestBidText);
                highestBidText.setText("Highest Bid:");
                //IF there are no bids, display the highest bid as: $0.00
                if(item.getItemDescription().getHighestBid()==null){
                    TextView highestBid = (TextView)v.findViewById(R.id.highestBid);
                    highestBid.setText("$0.00");
                }
                else {
                    TextView highestBid = (TextView)v.findViewById(R.id.highestBid);
                    highestBid.setText(item.getItemDescription().getHighestBid().toString());
                }

                TextView saleStatus=(TextView)v.findViewById(R.id.saleStatus);
                saleStatus.setText(item.getSaleStatus());

                //ImageView image=(ImageView)v.findViewById(R.id.image);
                //image.setImageResource(item.getItemDescription().getImage());

            }
        };

        list.setAdapter(adapter);
    }

}
/*firebaseRef.addValueEventListener(new ValueEventListener() {
@Override
public void onDataChange(DataSnapshot dataSnapshot) { //Gets called when new posts are made
        System.out.println("There are " + dataSnapshot.getChildrenCount() + " items");
        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
        Item item = postSnapshot.getValue(Item.class);
        System.out.println(item.getItemDescription());
        }
        }

@Override
public void onCancelled(FirebaseError firebaseError) {
        System.out.println("The read failed: " + firebaseError.getMessage());
        }
        });
*/