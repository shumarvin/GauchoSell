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
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import cs48.g05.bbc2016.gauchosell.activities.BaseActivity;
import cs48.g05.bbc2016.gauchosell.util.GauchoSell;
import cs48.g05.bbc2016.gauchosell.activities.HomeActivity;
import cs48.g05.bbc2016.gauchosell.R;
import cs48.g05.bbc2016.gauchosell.user.Account;
import cs48.g05.bbc2016.gauchosell.util.Constants;

/**
 * Created by dav on 4/17/16.
 */
public class LoginActivity extends BaseActivity {
    private Firebase firebaseRef;
    private EditText emailInput;
    private EditText passwordInput;
    private ProgressDialog authUserDialog;
    private static final String LOG_TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseRef = new Firebase(Constants.FIREBASE_URL);

        //create listeners for buttons
        TextView signUpLink = (TextView) findViewById(R.id.signupLink);
        signUpLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onSignUpPressed(view);
            }
        });
        Button loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                onLoginPressed(view);
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

        hookInView();
    }

    /**
     * Open CreateAccountActivity when user taps on "Sign up" TextView
     */
    private void onSignUpPressed(View view) {
        Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
        startActivity(intent);
    }

    private void onLoginPressed(View view) {
        authUserDialog.show();
        authUser();
    }

    private void authUser() {
        firebaseRef.authWithPassword(emailInput.getText().toString(),passwordInput.getText().toString(), new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Log.d(LOG_TAG, getString(R.string.successful_authentication) + authData.getUid());
                authUserDialog.dismiss();
                //get info on user, and change to home screen
                buildAccount();
                clearForm();
                //GauchoSell.user.loginUser(userAccount, bids, items, following);
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                String errorMessage = getString(R.string.email_password_invalid) + firebaseError.getMessage();
                Log.d(LOG_TAG, errorMessage);
                authUserDialog.dismiss();
                AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                alert.setTitle("Error");
                alert.setMessage(errorMessage);
                alert.setPositiveButton("OK",null);
                alert.show();

            }
        });
    }

    private void buildAccount() {
        String userUrl = Constants.FIREBASE_URL + "/" + Constants.FIREBASE_LOCATION_USERS;
        String email = emailInput.getText().toString().replace(".",",");
        Firebase userTable = new Firebase(userUrl).child(email);
        userTable.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot);
                GauchoSell.user.setAccount(dataSnapshot.getValue(Account.class));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("LOOK HERE 2");

            }
        });
    }

    private void hookInView(){
        emailInput = (EditText) findViewById(R.id.emailInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);

        authUserDialog = new ProgressDialog(this);
        authUserDialog.setTitle("Authenticating...");
        authUserDialog.setCancelable(false);
    }

    private void clearForm(){
        emailInput.setText("");
        passwordInput.setText("");
    }

}
