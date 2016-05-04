package cs48.g05.bbc2016.gauchosell.util;

import cs48.g05.bbc2016.gauchosell.BuildConfig;

/**
 * Created by dav on 4/16/16.
 */
public class Constants {

    /**
     * Constants for Firebase URL
     */
    public static final String FIREBASE_URL = BuildConfig.UNIQUE_FIREBASE_ROOT_URL;

    //Constants for users in database
    public static final String FIREBASE_LOCATION_USERS = "users";
    public static final String FIREBASE_LOCATION_UID_MAPPINGS = "uidMappings";
    public static final String FIREBASE_LOCATION_ITEMS = "items";
}
