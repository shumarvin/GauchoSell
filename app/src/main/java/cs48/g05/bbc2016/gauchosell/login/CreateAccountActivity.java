package cs48.g05.bbc2016.gauchosell.login;

import android.os.Bundle;
import android.widget.EditText;

import com.firebase.client.Firebase;

import cs48.g05.bbc2016.gauchosell.BaseActivity;
import cs48.g05.bbc2016.gauchosell.Constants;
import cs48.g05.bbc2016.gauchosell.R;

/**
 * Created by dav on 4/17/16.
 */


//See: https://github.com/udacity/ShoppingListPlusPlus/blob/master/app/src/main/java/com/udacity/firebase/shoppinglistplusplus/ui/login/CreateAccountActivity.java
public class CreateAccountActivity extends BaseActivity {
    private Firebase firebaseRef;
    private EditText fNameItem;
    private EditText lNameItem;
    private EditText usernameItem;
    private EditText birthYearItem;
    private EditText birthMonthItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);

        firebaseRef = new Firebase(Constants.FIREBASE_URL);

        hookInView(); //TODO: Change name?
    }

    public void hookInView() {
        fNameItem = (EditText) findViewById(R.id.FirstNameSignUp);
        lNameItem = (EditText) findViewById(R.id.LastNameSignUp);
        usernameItem = (EditText) findViewById(R.id.UserNameSignUp);
        birthMonthItem = (EditText) findViewById(R.id.BirthMonthSignUp);
        birthYearItem = (EditText) findViewById(R.id.BirthYearSignUp);
    }
}
