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
import android.widget.ImageView;
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
    private String buildingTitle;
    private String buildingInfo;
    private boolean iconPlane;
    private boolean iconInclined;
    private boolean iconElevator;
    private boolean iconAutomaticDoor;
    private boolean iconWC;
    private boolean iconAssistance;
    private boolean iconExit;


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
        else if (dialogType == Constants.DIALOG_TYPE.BUILDING_INFO){
            setContentView(R.layout.dialog_info_layout);
            TextView textView = (TextView) findViewById(R.id.dialog_building_info_title);
            textView.setText(buildingTitle);

            TextView textView1 = (TextView) findViewById(R.id.dialog_building_info_message);
            textView1.setText(buildingInfo);

            if (!iconPlane)
                findViewById(R.id.icon_plane).setVisibility(View.GONE);
            if (!iconInclined)
                findViewById(R.id.icon_inclined).setVisibility(View.GONE);
            if (!iconAutomaticDoor)
                findViewById(R.id.icon_automatic_door).setVisibility(View.GONE);
            if (!iconElevator)
                findViewById(R.id.icon_elevator).setVisibility(View.GONE);
            if (!iconWC)
                findViewById(R.id.icon_wc).setVisibility(View.GONE);
            if (!iconAssistance)
                findViewById(R.id.icon_needs_assistance).setVisibility(View.GONE);
            if (!iconExit)
                findViewById(R.id.icon_exit).setVisibility(View.GONE);
        }
        else {
            setContentView(R.layout.dialog_contact_layout);
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

    public void setBuildingTitle(String title) {
        String titleAux =
                activity.getResources().getString(R.string.dialog_building_info_title) + " " + title;
        this.buildingTitle = titleAux;
    }

    public void setBuildingInfo(String info) {
        this.buildingInfo = info;
    }

    public void setIconsVisibility(int building) {
        switch (building) {
            case 1 :
                iconPlane = true;
                iconInclined = false;
                iconElevator = true;
                iconAutomaticDoor = false;
                iconAssistance = true;
                iconWC = true;
                iconExit = false;
                break;
            case 2 :
                iconPlane = true;
                iconInclined = false;
                iconElevator = true;
                iconAutomaticDoor = false;
                iconAssistance = false;
                iconWC = false;
                iconExit = false;
                break;
            case 3 :
                iconPlane = true;
                iconInclined = false;
                iconElevator = true;
                iconAutomaticDoor = true;
                iconAssistance = false;
                iconWC = true;
                iconExit = true;
                break;
            case 4 :
                iconPlane = true;
                iconInclined = false;
                iconElevator = true;
                iconAutomaticDoor = true;
                iconAssistance = false;
                iconWC = true;
                iconExit = true;
                break;
            case 5 :
                iconPlane = true;
                iconInclined = false;
                iconElevator = true;
                iconAutomaticDoor = false;
                iconAssistance = true;
                iconWC = true;
                iconExit = false;
                break;
            case 6 :
                iconPlane = false;
                iconInclined = true;
                iconElevator = true;
                iconAutomaticDoor = false;
                iconAssistance = true;
                iconWC = true;
                iconExit = false;
                break;
            case 7 :
                iconPlane = true;
                iconInclined = false;
                iconElevator = true;
                iconAutomaticDoor = false;
                iconAssistance = true;
                iconWC = true;
                iconExit = false;
                break;
            case 8 :
                iconPlane = true;
                iconInclined = false;
                iconElevator = true;
                iconAutomaticDoor = true;
                iconAssistance = false;
                iconWC = true;
                iconExit = true;
                break;
            case 9 :
                iconPlane = false;
                iconInclined = true;
                iconElevator = true;
                iconAutomaticDoor = false;
                iconAssistance = true;
                iconWC = true;
                iconExit = false;
                break;
            case 10 :
                iconPlane = true;
                iconInclined = false;
                iconElevator = true;
                iconAutomaticDoor = false;
                iconAssistance = true;
                iconWC = true;
                iconExit = false;
                break;
            case 11 :
                iconPlane = true;
                iconInclined = false;
                iconElevator = false;
                iconAutomaticDoor = true;
                iconAssistance = false;
                iconWC = true;
                iconExit = true;
                break;
            case 12 :
                iconPlane = false;
                iconInclined = false;
                iconElevator = false;
                iconAutomaticDoor = false;
                iconAssistance = false;
                iconWC = false;
                iconExit = false;
                break;
            case 13 :
                iconPlane = false;
                iconInclined = false;
                iconElevator = false;
                iconAutomaticDoor = false;
                iconAssistance = false;
                iconWC = false;
                iconExit = false;
                break;
            case 14 :
                iconPlane = true;
                iconInclined = false;
                iconElevator = true;
                iconAutomaticDoor = false;
                iconAssistance = true;
                iconWC = true;
                iconExit = true;
                break;
            case 15 :
                iconPlane = true;
                iconInclined = false;
                iconElevator = false;
                iconAutomaticDoor = false;
                iconAssistance = true;
                iconWC = false;
                iconExit = false;
                break;
            case 16 :
                iconPlane = true;
                iconInclined = false;
                iconElevator = true;
                iconAutomaticDoor = true;
                iconAssistance = false;
                iconWC = true;
                iconExit = true;
                break;
            case 17 :
                iconPlane = true;
                iconInclined = false;
                iconElevator = true;
                iconAutomaticDoor = false;
                iconAssistance = false;
                iconWC = true;
                iconExit = false;
                break;
            case 20 :
                iconPlane = true;
                iconInclined = false;
                iconElevator = true;
                iconAutomaticDoor = true;
                iconAssistance = false;
                iconWC = true;
                iconExit = true;
                break;
            default:
                break;
        }
    }
}