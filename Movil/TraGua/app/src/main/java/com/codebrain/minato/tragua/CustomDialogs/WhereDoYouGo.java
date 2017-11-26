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
import android.widget.EditText;
import android.widget.Toast;

import com.codebrain.minato.tragua.ConstantValues;
import com.codebrain.minato.tragua.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import static android.app.Activity.RESULT_OK;

/**
 * Created by username on 11/25/2017.
 */

public class WhereDoYouGo extends DialogFragment {

    DialogListener dialogListener;
    private EditText origin, destiny;
    private LatLng latLngOrigin, latLngDestiny;

    static final int PLACE_PICKER_REQUEST_ORIGIN = 1;
    static final int PLACE_PICKER_REQUEST_DESTINY = 2;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final AlertDialog.Builder  builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.where_do_you_go_dialog, null);

        AppCompatImageView btPlacePicker_1 = v.findViewById(R.id.place_picker_1);
        AppCompatImageView btPlacePicker_2 = v.findViewById(R.id.place_picker_2);
        AppCompatButton travel_ok = v.findViewById(R.id.travel_bt_ok);
        AppCompatButton travel_cancel = v.findViewById(R.id.travel_bt_cancel);

        origin = v.findViewById(R.id.where_origin);
        destiny = v.findViewById(R.id.where_destiny);

        travel_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("dialog", 1);
                bundle.putDouble("lat1", latLngOrigin.latitude);
                bundle.putDouble("lon1", latLngOrigin.longitude);
                bundle.putDouble("lat2", latLngDestiny.latitude);
                bundle.putDouble("lon2", latLngDestiny.longitude);
                getDialog().dismiss();
                dialogListener.onCompleteDialog(bundle);
            }
        });

        travel_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

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

        builder.setView(v);

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
                    String toastMsg = String.format("%s", place.getName());
                    latLngOrigin = place.getLatLng();

                    origin.setText(toastMsg);

                    //Toast.makeText(getContext(), toastMsg, Toast.LENGTH_LONG).show();

                }
                break;
            case PLACE_PICKER_REQUEST_DESTINY:
                if (resultCode == RESULT_OK)
                {
                    Place place = PlacePicker.getPlace(getContext(), data);
                    String toastMsg = String.format("%s", place.getName());
                    latLngDestiny = place.getLatLng();
                    destiny.setText(toastMsg);
                    //Toast.makeText(getContext(), toastMsg, Toast.LENGTH_LONG).show();

                }
                break;
        }
    }
}
