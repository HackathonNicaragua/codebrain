package com.codebrain.minato.tragua.CustomDialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.codebrain.minato.tragua.ConstantValues;
import com.codebrain.minato.tragua.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import static android.app.Activity.RESULT_OK;

/**
 * Created by username on 11/25/2017.
 */

public class WhereDoYouGo extends DialogFragment {

    DialogListener dialogListener;
    private AppCompatImageView btPlacePicker_1, btPlacePicker_2;

    static final int PLACE_PICKER_REQUEST_ORIGIN = 1;
    static final int PLACE_PICKER_REQUEST_DESTINY = 2;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder  builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.where_do_you_go_dialog, null);

        btPlacePicker_1 = (AppCompatImageView)v.findViewById(R.id.place_picker_1);
        btPlacePicker_2 = (AppCompatImageView)v.findViewById(R.id.place_picker_2);

        btPlacePicker_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                    startActivityForResult(intentBuilder.build(getActivity()), PLACE_PICKER_REQUEST_ORIGIN);
                }
                catch (GooglePlayServicesNotAvailableException e)
                {
                    Log.d("Exception", e.getMessage());
                }
                catch (GooglePlayServicesRepairableException e)
                {
                    Log.d("Exception", e.getMessage());
                }
            }
        });

        btPlacePicker_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                    startActivityForResult(intentBuilder.build(getActivity()), PLACE_PICKER_REQUEST_DESTINY);
                }
                catch (GooglePlayServicesNotAvailableException e)
                {
                    Log.d("Exception", e.getMessage());
                }
                catch (GooglePlayServicesRepairableException e)
                {
                    Log.d("Exception", e.getMessage());
                }
            }
        });

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case PLACE_PICKER_REQUEST_ORIGIN:
                if (resultCode == RESULT_OK)
                {
                    Place place = PlacePicker.getPlace(getContext(), data);
                    String toastMsg = String.format("Place: %s", place.getName());
                    Toast.makeText(getContext(), toastMsg, Toast.LENGTH_LONG).show();

                }
                break;
            case PLACE_PICKER_REQUEST_DESTINY:
                if (resultCode == RESULT_OK)
                {
                    Place place = PlacePicker.getPlace(getContext(), data);
                    String toastMsg = String.format("Place: %s", place.getName());
                    Toast.makeText(getContext(), toastMsg, Toast.LENGTH_LONG).show();

                }
                break;
        }
    }
}
