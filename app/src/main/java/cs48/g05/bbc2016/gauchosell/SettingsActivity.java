package cs48.g05.bbc2016.gauchosell;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;

import cs48.g05.bbc2016.gauchosell.login.LoginActivity;
import cs48.g05.bbc2016.gauchosell.util.Constants;

//import com.firebase.client.core.view.View;

public class SettingsActivity extends AppCompatActivity {
    private Firebase firebaseRef;
    private EditText firstName;
    private EditText lastName;
    private EditText userName;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //set G logo
        Typeface logoGFont = Typeface.createFromAsset(getAssets(), "The Heart Maze Demo.ttf");
        TextView logoGTextView = (TextView)findViewById(R.id.g_logo);
        logoGTextView.setTypeface(logoGFont);

        firebaseRef = new Firebase(Constants.FIREBASE_URL);
        //display info from database

        firstName = (EditText) findViewById(R.id.first_name_edit_field);
        firstName.setText(GauchoSell.user.getAccount().getFirstName());

        lastName = (EditText) findViewById(R.id.last_name_edit_field);
        lastName.setText(GauchoSell.user.getAccount().getLastName());

        userName = (EditText) findViewById(R.id.username_edit_field);
        userName.setText(GauchoSell.user.getAccount().getUsername());

        email = (EditText) findViewById(R.id.email_edit_field);
        email.setText(GauchoSell.user.getAccount().getEmail());

        Button saveChangesButton = (Button) findViewById(R.id.save_button);
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveChangesClick(v);
            }
        });

       Button logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onLogoutClick(v);
            }
        });


    }

    public void onLogoutClick(View view){
        Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void onSaveChangesClick(View view){
        if(!isValidForm())
            return;
        GauchoSell.user.getAccount().setFirstName(firstName.getText().toString());
        GauchoSell.user.getAccount().setLastName((lastName.getText().toString()));
        //GauchoSell.user.getAccount().setUsername((userName.getText().toString()));
        //dialogue box for succesfulyl changed Or something like that TODO
        Firebase userRef = firebaseRef.child(Constants.FIREBASE_LOCATION_USERS)
                .child(GauchoSell.user.getAccount().getEmail().replace(".",","));
        userRef.setValue(GauchoSell.user.getAccount());
    }

    private boolean isValidForm() {
        boolean result = true;
        if (firstName.getText().toString().equals("")) {
            firstName.setError(getString(R.string.empty_field_error));
            result = false;
        }

        if (lastName.getText().toString().equals("")) {
            lastName.setError(getString(R.string.empty_field_error));
            result = false;
        }

       /* if (userName.getText().toString().equals("")) {
            userName.setError(getString(R.string.empty_field_error));
            result = false;
        }*/
        return result;
    }
}
