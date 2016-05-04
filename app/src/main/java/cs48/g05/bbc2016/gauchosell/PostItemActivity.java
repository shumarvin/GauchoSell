package cs48.g05.bbc2016.gauchosell;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.firebase.client.Firebase;

import cs48.g05.bbc2016.gauchosell.util.Constants;

public class PostItemActivity extends Activity implements UploadImageDialogFragment.UploadImageListener {
    private Firebase firebaseRef;
    private EditText itemNameText;
    private EditText itemDescriptionText;
    private String category;
    private EditText priceText;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_item);
        firebaseRef = new Firebase(Constants.FIREBASE_URL);

        itemNameText = (EditText) findViewById(R.id.item_name_field);
        itemDescriptionText = (EditText) findViewById(R.id.item_description_field);
        priceText = (EditText) findViewById(R.id.item_price_field);

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
        //initialize upload picture button
        Button uploadImage = (Button) findViewById(R.id.uploadImage);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUploadImageClick(v);
            }
        });
        //initialize the postItem button
        Button postItem = (Button) findViewById(R.id.postItem);
        postItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPostItemClick(v);
            }
        });
    }

    public void onUploadImageClick(View v) {

    }
    public void onPostItemClick(View v) {
        String itemName = itemNameText.getText().toString();
        String itemDescription = itemDescriptionText.getText().toString();
        String price = priceText.getText().toString();
    }
    @Override
    public void onDialogPositiveClick(DialogFragment dialog){

    }
    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
    }
}

