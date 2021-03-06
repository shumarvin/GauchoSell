package cs48.g05.bbc2016.gauchosell.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import cs48.g05.bbc2016.gauchosell.R;

/**
 * Created by shuma_000 on 5/13/2016.
 * Class to upload images to Firebase
 */
public class ImageUploadFireBaseAdapter {
    private Context context;

    public ImageUploadFireBaseAdapter(Context current){
        this.context = current;
    }
    //converts image to base64 string to be stored on Firebase
    //code taken from user skabir from Stack Overflow:
    //http://stackoverflow.com/questions/26292969/can-i-store-image-files-in-firebase-using-java-api
    public String convertImage(ImageView itemImage){
        Bitmap bmp = ((BitmapDrawable)itemImage.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        String imageFile = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return imageFile;
    }

}
