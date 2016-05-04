package cs48.g05.bbc2016.gauchosell;

import android.os.Bundle;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import cs48.g05.bbc2016.gauchosell.item.Item;
import cs48.g05.bbc2016.gauchosell.util.Constants;


/**
 * Created by laurendumapias on 4/30/16.
 */
public class HomeActivity extends BaseActivity {
    private Firebase firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseRef = new Firebase(Constants.FIREBASE_URL + Constants.FIREBASE_LOCATION_ITEMS);

        firebaseRef.addValueEventListener(new ValueEventListener() {
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
        //TODO: Do we want to do onChildAdded??


    }

}
