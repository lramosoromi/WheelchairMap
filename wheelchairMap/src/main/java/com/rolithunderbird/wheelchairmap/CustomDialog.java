package com.rolithunderbird.wheelchairmap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by rolithunderbird on 11.06.16.
 */
public class CustomDialog extends Dialog
        implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Activity activity;
    private static AlertDialog alert;
    private ArrayAdapter adapter;


    public CustomDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_layout);
        Button button = (Button) findViewById(R.id.custom_dialog_button);
        button.setOnClickListener(this);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_buildings);
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(activity.getBaseContext(),
                R.array.buildings_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        //Call Activity of the building blueprint
        dismiss();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        parent.getItemAtPosition(pos);
        Toast.makeText(activity.getBaseContext(),
                " " + parent.getItemAtPosition(pos), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {  }
}