package com.codebrain.minato.tragua;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by David on 25/11/2017.
 */

public class LanguageChange extends AppCompatActivity{
    private String [] lengua = new String[]{
            "English",
            "Spanish",
            "Frances",
            "Portuguese"
    };
    private int item_lengua;
    private TextView tw;
    private NumberPicker numberPicker;
    private Locale local;
    private Configuration configuration = new Configuration();
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laguage);
        tw = findViewById(R.id.textview1);
        button = findViewById(R.id.button1);
        numberPicker = findViewById(R.id.numberpicker);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(lengua.length-1);
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setDisplayedValues(lengua);
        SelectLenguageDefault();
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Toast.makeText(getApplicationContext(),lengua[newVal] + " Numero " + String.valueOf(newVal),Toast.LENGTH_SHORT).show();
                    item_lengua = newVal;
                    onConfigurationChanged(configuration);
                tw.setText(R.string.language);
                button.setText(R.string.ok);
                }
        });
    }

    private void SelectLenguageDefault() {
        String l = Locale.getDefault().getLanguage();
        if (l.equals("en")){
            numberPicker.setValue(0);
            item_lengua = 0;
        }else if (l.equals("es")){
            numberPicker.setValue(1);
            item_lengua = 1;
        }else if (l.equals("fr")){
            numberPicker.setValue(2);
            item_lengua = 2;
        }else if (l.equals("pt")){
            numberPicker.setValue(3);
            item_lengua = 3;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (lengua[item_lengua].equals("English")){
            local = new Locale("en");
            configuration.locale = local;
            getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
        }else if (lengua[item_lengua].equals("Spanish")){
            local = new Locale("es");
            configuration.locale = local;
            getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
        }else if (lengua[item_lengua].equals("Frances")){
            local = new Locale("fr");
            configuration.locale = local;
            getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
        }else if (lengua[item_lengua].equals("Portuguese")){
            local = new Locale("pt");
            configuration.locale = local;
            getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
        }
    }

    public void ChangeLenguage(View v){
        Toast.makeText(getApplicationContext(),getText(R.string.ok),Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,MapsActivity.class);
        startActivity(intent);
    }
}
