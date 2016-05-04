package cs48.g05.bbc2016.gauchosell;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

/**
 * Created by shuma_000 on 5/3/2016.
 */
public class UploadImageDialogFragment extends DialogFragment {

    //interface for activity to receive event callbacks
    public interface UploadImageListener{
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return null;
    }
}
