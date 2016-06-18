package com.rolithunderbird.wheelchairmap.javaClases;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.rolithunderbird.wheelchairmap.R;
import com.rolithunderbird.wheelchairmap.activities.BlueprintActivity;
import com.rolithunderbird.wheelchairmap.utils.Constants;

/**
 * Created by rolithunderbird on 11.06.16.
 */
public class CustomDialog extends Dialog
        implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Activity activity;
    private ArrayAdapter adapter;
    private Constants.DIALOG_TYPE dialogType;


    public CustomDialog(Activity a, Constants.DIALOG_TYPE dialog_type) {
        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
        this.dialogType = dialog_type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogType == Constants.DIALOG_TYPE.BUILDING_SELECTION) {
            setContentView(R.layout.dialog_selection_layout);
            Button button = (Button) findViewById(R.id.dialog_building_selection_button);
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
        else {
            setContentView(R.layout.dialog_info_layout);
            TextView textView = (TextView) findViewById(R.id.dialog_building_info_title);
            String title = activity.getResources().getString(R.string.dialog_building_info_title) + " 9";
            textView.setText(title);

            TextView textView1 = (TextView) findViewById(R.id.dialog_building_info_message);
            String message = "School of informatics";
            textView1.setText(message);
        }
    }

    @Override
    public void onClick(View v) {
        //Call Activity of the building blueprint
        activity.startActivity(new Intent(activity.getBaseContext(), BlueprintActivity.class));

        dismiss();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        //Toast.makeText(activity.getBaseContext(),
        //        " " + parent.getItemAtPosition(pos), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {  }
}