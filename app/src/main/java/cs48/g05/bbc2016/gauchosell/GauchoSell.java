package cs48.g05.bbc2016.gauchosell;

import com.firebase.client.Firebase;

import cs48.g05.bbc2016.gauchosell.user.User;

/**
 * Created by dav on 4/20/16.
 * APP STARTS HERE
 * This is what initializes Firebase before you run the app
 */
public class GauchoSell extends android.app.Application {
    public static final User user = new User();

    @Override
    public void onCreate() {
        super.onCreate();
        /* Initialize Firebase */
        Firebase.setAndroidContext(this);
        /* Enable disk persistence  */
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
    }
}
