package cs48.g05.bbc2016.gauchosell.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.core.view.View;

import java.util.Map;

import cs48.g05.bbc2016.gauchosell.BaseActivity;
import cs48.g05.bbc2016.gauchosell.Constants;
import cs48.g05.bbc2016.gauchosell.R;

/**
 * Created by dav on 4/17/16.
 * Base code provided by Udacity tutorial see:
 * https://github.com/udacity/ShoppingListPlusPlus
 */


public class CreateAccountActivity extends BaseActivity {
    private Firebase firebaseRef;
    private EditText fNameItem;
    private EditText lNameItem;
    private EditText emailItem;
    private EditText usernameItem;
    private EditText passwordItem;
    private EditText birthYearItem;
    private EditText birthMonthItem;
    private ProgressDialog createUserDialog;
    private static final String LOG_TAG = CreateAccountActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);

        firebaseRef = new Firebase(Constants.FIREBASE_URL);

        hookInView(); //TODO: Change name?
    }

    /**
    * Link layout elements from XML and set up the progress dialog
     */
    public void hookInView() {
        fNameItem = (EditText) findViewById(R.id.FirstNameSignUp);
        lNameItem = (EditText) findViewById(R.id.LastNameSignUp);
        usernameItem = (EditText) findViewById(R.id.UserNameSignUp);
        emailItem = (EditText) findViewById(R.id.EmailSignUp);
        passwordItem = (EditText) findViewById(R.id.PasswordSignUp);
        birthMonthItem = (EditText) findViewById(R.id.BirthMonthSignUp);
        birthYearItem = (EditText) findViewById(R.id.BirthYearSignUp);

        /* set up the progress dialog that is displayed when authenticating with Firebase*/

        createUserDialog = new ProgressDialog(this);
        createUserDialog.setTitle(getResources().getString(R.string.loading_text));
        createUserDialog.setMessage(getResources().getString(R.string.check_inbox_text));
        createUserDialog.setCancelable(false);
    }

    /**
     * Creates and validates a user when sign up clicked
     */
    public void onCreateSignUpClicked(View view) {
        String userName= usernameItem.getText().toString();
        String firstName= fNameItem.getText().toString();
        String lastName= lNameItem.getText().toString();
        String email= emailItem.getText().toString();
        //TODO: hash password?
        // http://stackoverflow.com/questions/2860943/how-can-i-hash-a-password-in-java
        String password = passwordItem.getText().toString();

        /**
         * Checking email and username is valid
         */
        if(!isValidEmail(email) || !isValidUsername(userName)) return;

        createUserDialog.show();

        /**
         * Create User
         */
        firebaseRef.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>(){
            @Override
            public void onSuccess(Map<String, Object> stringObjectMap) {
                Log.d(LOG_TAG, "Successfully created user account with uid: " + stringObjectMap.get("uid"));
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                // log error and dismiss dialog if creation fails
                Log.d(LOG_TAG, "An error occurred! " + firebaseError);
                createUserDialog.dismiss();

            }
        });

    }
    private void addUserToDataBase(final String authUserIDA) {
        //encode user email as firebase key which does not allow "." in the key
        final String encodedEmail = emailItem.getText().toString().replace(".",",");
    }

    private boolean isValidEmail(String email){
        boolean isValidEmail = (email != null &&
            android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isValidEmail) {
            emailItem.setError(String.format(getString(R.string.invalid_email_error),email));
        }
        return isValidEmail;
    }

    private boolean isValidUsername(String userName){
        if (userName.equals("")){
            usernameItem.setError(getResources().getString(R.string.username_error_empty));
            return false;
        }
        return true;

    }

}

