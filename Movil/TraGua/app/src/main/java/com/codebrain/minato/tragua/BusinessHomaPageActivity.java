package com.codebrain.minato.tragua;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.codebrain.minato.tragua.CustomDialogs.DialogListener;
import com.codebrain.minato.tragua.CustomDialogs.RateDialog;

/**
 * Created by username on 11/26/2017.
 */

public class BusinessHomaPageActivity extends AppCompatActivity implements DialogListener{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_home_page_layou);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_business);
        LinearLayout valorar = (LinearLayout)findViewById(R.id.business_valorar_bt);
        valorar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                RateDialog dialog = new RateDialog();
                dialog.show(fragmentManager, "rating");
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setActionBar(toolbar);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public void onCompleteDialog(Bundle bundle)
    {
        switch (bundle.getInt("dialog"))
        {
            case 3:
                Log.d("Manejado", "Respuesta de RaeDialog");
                //rate dialog
                Toast.makeText(getApplicationContext(), "rating" + bundle.getFloat("rating"), Toast.LENGTH_LONG).show();
                break;
        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        switch (item.getItemId())
//        {
//            case R.id.homeAsUp:
//                finish();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
