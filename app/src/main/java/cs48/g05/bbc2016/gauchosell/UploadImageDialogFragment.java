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

    public UploadImageListener mListener;



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
}
