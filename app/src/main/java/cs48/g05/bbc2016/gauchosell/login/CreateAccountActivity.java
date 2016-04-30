package cs48.g05.bbc2016.gauchosell.login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
//import com.firebase.client.ServerValue; //was using for timestamp, may change

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cs48.g05.bbc2016.gauchosell.BaseActivity;
import cs48.g05.bbc2016.gauchosell.Constants;
import cs48.g05.bbc2016.gauchosell.R;
import cs48.g05.bbc2016.gauchosell.User;

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
        hookInView();

        Button signUpButton = (Button) findViewById(R.id.SignUpButton);
        signUpButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                onSignUpClicked(view);
            }
        });

        //set logo font
        Typeface logoFont = Typeface.createFromAsset(getAssets(), "FUTRFW.TTF");
        TextView logoTextView = (TextView)findViewById(R.id.login_logo);
        logoTextView.setTypeface(logoFont);

        //set G logo
        Typeface logoGFont = Typeface.createFromAsset(getAssets(), "The Heart Maze Demo.ttf");
        TextView logoGTextView = (TextView)findViewById(R.id.g_logo);
        logoGTextView.setTypeface(logoGFont);
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
        //createUserDialog.setMessage(getResources().getString(R.string.check_inbox_text));
        createUserDialog.setCancelable(false);
    }

    /**
     * Creates and validates a user when sign up clicked
     */
    public void onSignUpClicked(View view) {
        String userName= usernameItem.getText().toString();
        String firstName= fNameItem.getText().toString();
        String lastName= lNameItem.getText().toString();
        final String email= emailItem.getText().toString();
        final String password = passwordItem.getText().toString(); //be careful using strings with passwords
        //note: firebase hashes passwords for you

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
                addUserToDataBase((String) stringObjectMap.get("uid"));
                Log.d(LOG_TAG, "Successfully created user account with uid: " + stringObjectMap.get("uid"));
                createUserDialog.dismiss();
                clearForm();
                Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                // log error and dismiss dialog if creation fails
                String errorMessage = "An error occurred! " + firebaseError.getMessage();
                Log.d(LOG_TAG, errorMessage);
                createUserDialog.dismiss();
                AlertDialog.Builder alert = new AlertDialog.Builder(CreateAccountActivity.this);
                alert.setTitle("Error");
                alert.setMessage(errorMessage);
                alert.setPositiveButton("OK",null);
                alert.show();
            }
        });

    }
    private void addUserToDataBase(final String authUserID) {
        //encode user email as firebase key which does not allow "." in the key
        final String encodedEmail = emailItem.getText().toString().replace(".",",");

        //user : uid mapping
        HashMap<String, Object> userUidMapping = new HashMap<String, Object>();

        //create hashmap representation of user
        User newUser = new User(Integer.parseInt(birthYearItem.getText().toString()),
                Integer.parseInt(birthMonthItem.getText().toString()),
                fNameItem.getText().toString(), lNameItem.getText().toString(),
                usernameItem.getText().toString(), emailItem.getText().toString());
        HashMap<String, Object> newUserMap = (HashMap<String, Object>) new ObjectMapper().convertValue(newUser, Map.class);
        //raw timestamp of when user joins
        newUserMap.put("timeJoined",new Date().getTime());

        //Add user and UID to map to push to database
        userUidMapping.put("/"+Constants.FIREBASE_LOCATION_USERS + "/" + encodedEmail, newUserMap);
        userUidMapping.put("/"+Constants.FIREBASE_LOCATION_UID_MAPPINGS + "/" + authUserID, encodedEmail);

        //Try to update database (will fail if user exists)
        firebaseRef.updateChildren(userUidMapping, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if(firebaseError != null) {
                    //just make uid mapping
                    firebaseRef.child(Constants.FIREBASE_LOCATION_UID_MAPPINGS)
                            .child(authUserID).setValue(encodedEmail);

                    //log them out if fails
                    firebaseRef.unauth();
                }
            }
        });
    }
    //TODO: send verification email to make sure email is valid

    private boolean isValidEmail(String email){
        boolean isValidEmail = (email != null &&
            android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isValidEmail) {
            emailItem.setError(String.format(getString(R.string.invalid_email_error),email));
        }
        return isValidEmail;
    }

    private boolean isValidUsername(String userName){
        if (userName.equals("")) {
            usernameItem.setError(getResources().getString(R.string.username_error_empty));
            return false;
        } else if(userName.contains(" ")) {
            usernameItem.setError("No spaces allowed in username.");
            return false;
        }
        return true;

    }

    private void clearForm() {
        fNameItem.setText("");
        lNameItem.setText("");
        usernameItem.setText("");
        emailItem.setText("");
        passwordItem.setText("");
        birthMonthItem.setText("");
        birthYearItem.setText("");
    }

}

