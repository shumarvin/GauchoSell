package cs48.g05.bbc2016.gauchosell;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.firebase.client.Firebase;

/*public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}*/

    /**
     * Includes one-time initialization of Firebase related code
     */
    public class MainActivity extends AppCompatActivity {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        /* Initialize Firebase */
            Firebase.setAndroidContext(this);
        /* Enable disk persistence  */
            Firebase.getDefaultConfig().setPersistenceEnabled(true);
        }


}
