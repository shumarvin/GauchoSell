package cs48.g05.bbc2016.gauchosell.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;

import cs48.g05.bbc2016.gauchosell.R;
import cs48.g05.bbc2016.gauchosell.item.Item;
import cs48.g05.bbc2016.gauchosell.util.Constants;
import cs48.g05.bbc2016.gauchosell.util.GauchoSell;

/**
 * Created by icema_000 on 5/13/2016.
 */
public class FollowingActivity extends FeedsActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView list = (ListView) findViewById(R.id.listView);

        firebaseRef = new Firebase(Constants.FIREBASE_URL + "/" + Constants.FIREBASE_LOCATION_ITEMS);
        Query queryRef = firebaseRef.orderByChild("likers/"+ GauchoSell.user.getAccount().getUsername()+"/username").equalTo(GauchoSell.user.getAccount().getUsername());
        list.setAdapter(initializeFeed(queryRef));
    }

    //Changes the like behavior when initializing the feed. When you press the like button, it removes
    //the item from the likes feed
    @Override
    public void likeBehavior(Item item, View v,  ItemViewHolder viewHolder){
        final Item item2=item;
        final String username=GauchoSell.user.getAccount().getUsername();
        final ImageButton likeButton = (ImageButton) v.findViewById(R.id.like_button);
        likeButton.setBackgroundResource(R.drawable.favorite);
        likeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                GauchoSell.user.unfollowItem(item2);
            }
        });
    }

    //
}
