package cs48.g05.bbc2016.gauchosell;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cs48.g05.bbc2016.gauchosell.item.Bid;
import cs48.g05.bbc2016.gauchosell.item.ItemInformation;
import cs48.g05.bbc2016.gauchosell.util.Constants;
import cs48.g05.bbc2016.gauchosell.util.EmbeddedImage;
import cs48.g05.bbc2016.gauchosell.util.ImageUploadFireBaseAdapter;

public class PostItemActivity extends FragmentActivity implements
                                                UploadImageDialogFragment.UploadImageListener {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;

    private Firebase firebaseRef;
    private EditText itemNameText;
    private EditText itemDescriptionText;
    private String category;
    private EditText priceText;
    private Uri fileUri;
    private ImageView itemImage;
    private String imgDecodableString;
    private String imageFile;
    private ImageUploadFireBaseAdapter uploadImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_item);
        firebaseRef = new Firebase(Constants.FIREBASE_URL);
        uploadImageAdapter = new ImageUploadFireBaseAdapter(this);

        itemNameText = (EditText) findViewById(R.id.item_name_field);
        itemDescriptionText = (EditText) findViewById(R.id.item_description_field);
        priceText = (EditText) findViewById(R.id.item_price_field);
        itemImage = (ImageView) findViewById(R.id.itemPhoto);

        //Initialize drop-down category menu
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //set category to the one the user selected
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
        showUploadImageDialog();
    }
    public void onPostItemClick(View v) {
        String itemName = itemNameText.getText().toString();
        String itemDescription = itemDescriptionText.getText().toString();
        String itemSeller = GauchoSell.user.getAccount().getUsername();
        double price = Double.parseDouble(priceText.getText().toString());
        ItemInformation itemInfo=new ItemInformation(price, itemName, category, itemDescription, itemSeller);
        GauchoSell.user.postItem(itemInfo);

        //Go back to the home page
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

    }
    //show the upload image dialog popup box
    public void showUploadImageDialog(){
        DialogFragment dialog = new UploadImageDialogFragment();
        dialog.show(getFragmentManager(), "UploadImageDialog");
    }
    //starts the camera app
    @Override
    public void onDialogPositiveClick(DialogFragment dialog){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }
    //User clicked upload image, which will take them to the Gallery app
    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, MEDIA_TYPE_IMAGE);
    }
    //Get the image captured from the camera
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            //image taken from camera
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                // Image captured and saved to fileUri specified in the Intent
                fileUri = data.getData();
                itemImage.setImageURI(fileUri);
            }
            //image taken from gallery app
            //code taken from http://programmerguru.com/android-tutorial/how-to-pick-image-from-gallery/
            else if (requestCode == MEDIA_TYPE_IMAGE) {
                fileUri = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(fileUri,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                // Set the Image in ImageView after decoding the String
                itemImage.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));
            }
            imageFile = uploadImageAdapter.convertImage(R.id.itemPhoto);
        } else if (resultCode == Activity.RESULT_CANCELED) {
            // User cancelled the image capture
        } else {
            // Image capture failed, advise user
            System.out.println("Error: Capture Failed");
        }

    }
}

