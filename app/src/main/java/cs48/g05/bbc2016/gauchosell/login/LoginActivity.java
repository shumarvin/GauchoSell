package cs48.g05.bbc2016.gauchosell.login;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Firebase;

import cs48.g05.bbc2016.gauchosell.BaseActivity;
import cs48.g05.bbc2016.gauchosell.Constants;
import cs48.g05.bbc2016.gauchosell.R;

/**
 * Created by dav on 4/17/16.
 */
public class LoginActivity extends BaseActivity {
    private Firebase firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseRef = new Firebase(Constants.FIREBASE_URL);

        TextView signUpLink = (TextView) findViewById(R.id.signupLink);
        signUpLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onSignUpPressed(view);
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
     * Open CreateAccountActivity when user taps on "Sign up" TextView
     */
    public void onSignUpPressed(View view) {
        Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
        startActivity(intent);
    }
}
