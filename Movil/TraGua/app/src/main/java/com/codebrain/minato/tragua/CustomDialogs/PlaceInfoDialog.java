package com.codebrain.minato.tragua.CustomDialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatRatingBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.codebrain.minato.tragua.BusinessHomaPageActivity;
import com.codebrain.minato.tragua.R;

/**
 * Created by username on 11/26/2017.
 */

public class PlaceInfoDialog extends DialogFragment {
    DialogListener dialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder  builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.custom_info_place_content, null);
        Button btMoreInfo = (Button)v.findViewById(R.id.info_bt_more_info);
        btMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BusinessHomaPageActivity.class);
                startActivity(intent);
            }
        });

        Button btTraceRoute = (Button)v.findViewById(R.id.info_bt_trazar_ruta);
        btTraceRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("dialog", 10);
                dialogListener.onCompleteDialog(bundle);
                getDialog().dismiss();
            }
        });

        Button btExit = (Button)v.findViewById(R.id.info_bt_salir);
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

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
