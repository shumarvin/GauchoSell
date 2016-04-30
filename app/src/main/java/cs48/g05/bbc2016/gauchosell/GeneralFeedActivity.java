package cs48.g05.bbc2016.gauchosell;

import android.os.Bundle;

import com.firebase.client.Firebase;

//import cs48.g05.bbc2016.gauchosell.BaseActivity;

/**
 * Created by laurendumapias on 4/30/16.
 */
public class GeneralFeedActivity extends BaseActivity {
    private Firebase firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseRef = new Firebase(Constants.FIREBASE_URL);
    }
}
