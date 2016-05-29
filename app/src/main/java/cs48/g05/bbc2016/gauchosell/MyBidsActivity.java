package cs48.g05.bbc2016.gauchosell;

import android.os.Bundle;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;

import cs48.g05.bbc2016.gauchosell.util.Constants;

/**
 * Created by icema_000 on 5/13/2016.
 */
public class MyBidsActivity extends FeedsActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView list = (ListView) findViewById(R.id.listView);


        firebaseRef = new Firebase(Constants.FIREBASE_URL + "/" + Constants.FIREBASE_LOCATION_ITEMS);
        Query queryRef = firebaseRef.orderByChild("bids/username").equalTo(GauchoSell.user.getAccount().getUsername());
        list.setAdapter(initializeFeed(queryRef));;
    }
}
