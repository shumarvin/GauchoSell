package cs48.g05.bbc2016.gauchosell;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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

        //set G logo
        Typeface logoGFont = Typeface.createFromAsset(getAssets(), "The Heart Maze Demo.ttf");
        TextView logoGTextView = (TextView)findViewById(R.id.g_logo);
        logoGTextView.setTypeface(logoGFont);

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


        //intent from home to post item view
        ImageButton addPostButton = (ImageButton) findViewById(R.id.add_post);
        addPostButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onAddPostClicked (view);
            }
        });

        //intent from home to settings
        ImageButton settingsButton = (ImageButton) findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onSettingsClicked (view);
            }
        });


    }

    public void onAddPostClicked (View view) {
        Intent intent = new Intent(HomeActivity.this, PostItemActivity.class);
        startActivity(intent);
    }

    public void onSettingsClicked(View view) {
        Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

}
