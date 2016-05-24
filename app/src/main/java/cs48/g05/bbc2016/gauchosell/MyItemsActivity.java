package cs48.g05.bbc2016.gauchosell;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

import cs48.g05.bbc2016.gauchosell.feeds.Feed;
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

}
