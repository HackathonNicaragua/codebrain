package com.codebrain.minato.tragua.CustomDialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;

import com.codebrain.minato.tragua.R;

/**
 * Created by username on 11/25/2017.
 */

public class BaseDialog extends DialogFragment {

    DialogListener dialogListener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder  builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.where_do_you_go_dialog, null));

        return builder.create();

        //do stuff here
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            dialogListener = (DialogListener)activity;
        }
        catch (ClassCastException e)
        {
            Log.d("Exception", e.getMessage());
        }
    }
}
