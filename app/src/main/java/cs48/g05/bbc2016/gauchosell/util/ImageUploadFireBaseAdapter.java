package cs48.g05.bbc2016.gauchosell.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
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

    public String convertImage(int imageID){
        Bitmap bmp =  BitmapFactory.decodeResource(context.getResources(), imageID);//your image
        ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, bYtE);
        bmp.recycle();
        byte[] byteArray = bYtE.toByteArray();
        String imageFile = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return imageFile;
    }

}
