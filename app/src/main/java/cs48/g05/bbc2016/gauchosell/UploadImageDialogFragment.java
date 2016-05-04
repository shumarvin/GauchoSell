package cs48.g05.bbc2016.gauchosell;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shuma_000 on 5/3/2016.
 */
public class UploadImageDialogFragment extends DialogFragment {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;

    public UploadImageListener mListener;
    private Uri fileUri;
    private Bitmap bitmap;
    private ImageView itemImage;


    //interface for activity to receive event callbacks
    public interface UploadImageListener{
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.UploadImageDialogTitle);
        builder.setPositiveButton(R.string.takePhoto, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                //user clicked take photo
                mListener.onDialogPositiveClick(UploadImageDialogFragment.this);
                /**
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri = getOutputMediaFileURI(MEDIA_TYPE_IMAGE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                 **/
            }
        });

        builder.setNegativeButton(R.string.uploadImage, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                //user clicked upload image
                mListener.onDialogNegativeClick(UploadImageDialogFragment.this);
            }
        });
        return builder.create();
    }
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            mListener = (UploadImageListener) activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement UploadImageListener");
        }
    }
    /**
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                Toast.makeText(getActivity(), "Image saved to:\n" +
                        data.getData(), Toast.LENGTH_LONG).show();
                try{
                    fileUri = data.getData();
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), fileUri);
                itemImage = (ImageView) getActivity().findViewById(R.id.itemPhoto);
                    itemImage.setImageBitmap(bitmap);
            } catch(IOException e){

                }}
            else if (resultCode == Activity.RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
        }
    }
    //Create file Uri for saving image
    private static Uri getOutputMediaFileURI(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }
    //create File to save image
    private static File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");

        //create media storage directory if it doesn't exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        //create media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile= new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        return mediaFile;
    }
    **/
    public Bitmap getBitmap(){
        return bitmap;
    }
}
