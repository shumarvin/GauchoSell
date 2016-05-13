package cs48.g05.bbc2016.gauchosell.feeds;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import cs48.g05.bbc2016.gauchosell.item.Item;
import cs48.g05.bbc2016.gauchosell.util.Constants;

/**
 * Created by icema_000 on 4/29/2016.
 */
public class GeneralFeed extends Feed {
    public GeneralFeed(){
        super();
    }
    @Override
    public boolean callQuery() {
        return true;
    }
}
