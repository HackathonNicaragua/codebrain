package com.codebrain.minato.tragua.CustomDialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatRatingBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.codebrain.minato.tragua.R;

/**
 * Created by username on 11/26/2017.
 */

public class RateDialog extends DialogFragment {
    DialogListener dialogListener;
    AppCompatRatingBar ratingBar;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder  builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.valoracion, null);
        ratingBar = (AppCompatRatingBar)v.findViewById(R.id.rating_bar);

        builder.setView(v);
        return builder.create();
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
