package cs48.g05.bbc2016.gauchosell;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;

import cs48.g05.bbc2016.gauchosell.util.Constants;


/**
 * Created by laurendumapias on 4/30/16.
 */
public class HomeActivity extends FeedsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = (ListView) findViewById(R.id.listView);

        //FirebaseListAdapter used to create a feed
        firebaseRef = new Firebase(Constants.FIREBASE_URL + "/" + Constants.FIREBASE_LOCATION_ITEMS);
        final Query queryRef = firebaseRef.orderByChild("priority");
        //Initialize the button to filter the categories
        Button filterButton = (Button) findViewById(R.id.category_button);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(category.equals("No Category"))) {
                    Query queryRef2 = firebaseRef.orderByChild("itemDescription/category").equalTo(category);
                    list.setAdapter(initializeFeed(queryRef2));
                }
                else {
                    list.setAdapter(initializeFeed(queryRef));
                }
            }
        });


        list.setAdapter(initializeFeed(queryRef));

    }
}
