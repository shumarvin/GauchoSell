package cs48.g05.bbc2016.gauchosell;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.firebase.client.Firebase;

public class PostItemActivity extends Activity {
    private Firebase firebaseRef;
    private EditText itemNameText;
    private EditText itemDescriptionText;
    private String category;
    private EditText priceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_item);
        firebaseRef = new Firebase(Constants.FIREBASE_URL);

        itemNameText = (EditText) findViewById(R.id.itemName);
        itemDescriptionText = (EditText) findViewById(R.id.itemDescription);
        priceText = (EditText) findViewById(R.id.itemPrice);

        //Initialize drop-down category menu
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button postItem = (Button) findViewById(R.id.button);
        postItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPostItemClick(v);
            }
        });
    }
    public void onPostItemClick(View v) {
        String itemName = itemNameText.getText().toString();
        String itemDescription = itemDescriptionText.getText().toString();
        String price = priceText.getText().toString();
    }
}

