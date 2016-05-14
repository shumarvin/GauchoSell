package cs48.g05.bbc2016.gauchosell;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;

import cs48.g05.bbc2016.gauchosell.feeds.Feed;
import cs48.g05.bbc2016.gauchosell.item.Item;
import cs48.g05.bbc2016.gauchosell.util.Constants;

/**
 * Created by icema_000 on 5/13/2016.
 */
public class MyItemsActivity extends FeedsActivity{
    private Firebase firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
