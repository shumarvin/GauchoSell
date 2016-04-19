package cs48.g05.bbc2016.gauchosell;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.firebase.client.Firebase;


    /**
     * Includes one-time initialization of Firebase related code
     */
    //TODO: mention how we got setup from firebase and udacity
    public class MainActivity extends AppCompatActivity {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        /* Initialize Firebase */
            Firebase.setAndroidContext(this);
        /* Enable disk persistence  */
            Firebase.getDefaultConfig().setPersistenceEnabled(true);

            setContentView(R.layout.activity_login);
        }


}