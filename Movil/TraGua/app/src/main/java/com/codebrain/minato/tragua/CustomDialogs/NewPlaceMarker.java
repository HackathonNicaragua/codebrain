package com.codebrain.minato.tragua.CustomDialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codebrain.minato.tragua.R;

/**
 * Created by username on 11/25/2017.
 */

public class NewPlaceMarker extends DialogFragment {
    DialogListener dialogListener;
    private String TitleBusiness;
    private String description;
    private String Cod_Ruc;
    private EditText textViewTitle, textViewDes, textViewRuc;
    private Button Send;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final AlertDialog.Builder  builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.new_place_marker_dialog, null);


        //do stuff here

        //Reference Dialog
        textViewTitle = v.findViewById(R.id.TitleBissnes);
        textViewDes = v.findViewById(R.id.description);
        textViewRuc = v.findViewById(R.id.ruc);
        Send = v.findViewById(R.id.send_button);

        //Listener
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TitleBusiness = textViewTitle.getText().toString();
                description = textViewDes.getText().toString();
                Cod_Ruc = textViewRuc.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putInt("dialog", 2);
                bundle.putString("business", TitleBusiness);
                bundle.putString("description",description);
                bundle.putString("Cod_Ruc",Cod_Ruc);
                //Toast.makeText(getContext(),"Trabajo: " + TitleBusiness + " Descripcion: " + description + " Ruc: " + Cod_Ruc,Toast.LENGTH_LONG).show();
                dialogListener.onCompleteDialog(bundle);

                //Close Dialog
                dismiss();
            }
        });
        builder.setView(v);

        //Return Dialog
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
