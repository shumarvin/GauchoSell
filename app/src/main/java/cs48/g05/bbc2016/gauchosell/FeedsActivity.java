package cs48.g05.bbc2016.gauchosell;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.client.Firebase;

import cs48.g05.bbc2016.gauchosell.feeds.MyBidsFeed;
import cs48.g05.bbc2016.gauchosell.util.Constants;

/**
 * Created by icema_000 on 5/13/2016.
 */
public class FeedsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //set G logo
        Typeface logoGFont = Typeface.createFromAsset(getAssets(), "The Heart Maze Demo.ttf");
        TextView logoGTextView = (TextView) findViewById(R.id.g_logo);

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
    }
    public void onHomeClicked(View view) {
        Intent intent = new Intent(getBaseContext(), HomeActivity.class);
        startActivity(intent);
    }

    public void onAddPostClicked (View view) {
        Intent intent = new Intent(getBaseContext(), PostItemActivity.class);
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


}



