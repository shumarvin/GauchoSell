package cs48.g05.bbc2016.gauchosell;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

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
            protected void populateView(View v, Item item, int i){
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

                final Item item2=item;

                ImageButton deleteItem=(ImageButton)v.findViewById(R.id.delete_post);
                deleteItem.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        GauchoSell.user.deleteItem(item2);
                    }
                });
            }
        };
        return adapter;
    }

}
