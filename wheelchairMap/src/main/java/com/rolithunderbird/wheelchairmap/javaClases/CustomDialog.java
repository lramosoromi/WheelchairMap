package com.rolithunderbird.wheelchairmap.javaClases;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rolithunderbird.wheelchairmap.R;
import com.rolithunderbird.wheelchairmap.activities.BlueprintActivity;
import com.rolithunderbird.wheelchairmap.utils.Constants;

import java.util.ArrayList;

/**
 * Created by rolithunderbird on 11.06.16.
 */
public class CustomDialog extends Dialog
        implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Activity activity;
    private ArrayAdapter adapter;
    private Constants.DIALOG_TYPE dialogType;
    private String blueprint;
    private String buildingTitle;
    private String buildingInfo;
    ArrayList<ImageView> iconsList;
    private boolean iconPlane;
    private boolean iconInclined;
    private boolean iconElevator;
    private boolean iconAutomaticDoor;
    private boolean iconWC;
    private boolean iconAssistance;
    private boolean iconExit;


    public CustomDialog(Activity a, Constants.DIALOG_TYPE dialog_type) {
        super(a);
        this.activity = a;
        this.dialogType = dialog_type;
        this.iconsList = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (dialogType == Constants.DIALOG_TYPE.BUILDING_SELECTION) {
            setContentView(R.layout.dialog_selection_layout);
            Button button = (Button) findViewById(R.id.dialog_building_selection_button);
            button.setOnClickListener(this);

            Spinner spinner = (Spinner) findViewById(R.id.dialog_building_selection_spinner_buildings);
            // Create an ArrayAdapter using the string array and a default spinner layout
            adapter = ArrayAdapter.createFromResource(activity.getBaseContext(),
                    R.array.dialog_buildings_array, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);
        }
        else if (dialogType == Constants.DIALOG_TYPE.BUILDING_INFO){
            setContentView(R.layout.dialog_info_layout);
            iconsList.add((ImageView) findViewById(R.id.dialog_building_info_icon_plane));
            iconsList.add((ImageView) findViewById(R.id.dialog_building_info_icon_inclined));
            iconsList.add((ImageView) findViewById(R.id.dialog_building_info_icon_elevator));
            iconsList.add((ImageView) findViewById(R.id.dialog_building_info_icon_automatic_door));
            iconsList.add((ImageView) findViewById(R.id.dialog_building_info_icon_needs_assistance));
            iconsList.add((ImageView) findViewById(R.id.dialog_building_info_icon_wc));
            iconsList.add((ImageView) findViewById(R.id.dialog_building_info_icon_exit));

            TextView textView = (TextView) findViewById(R.id.dialog_building_info_title);
            textView.setText(buildingTitle);

            TextView textView1 = (TextView) findViewById(R.id.dialog_building_info_message);
            textView1.setText(buildingInfo);

            if (!iconPlane) {
                ImageView icon = (ImageView) findViewById(R.id.dialog_building_info_icon_plane);
                icon.setVisibility(View.GONE);
                iconsList.remove(icon);
            }
            if (!iconInclined) {
                ImageView icon = (ImageView) findViewById(R.id.dialog_building_info_icon_inclined);
                icon.setVisibility(View.GONE);
                iconsList.remove(icon);
            }
            if (!iconElevator) {
                ImageView icon = (ImageView) findViewById(R.id.dialog_building_info_icon_elevator);
                icon.setVisibility(View.GONE);
                iconsList.remove(icon);
            }
            if (!iconAutomaticDoor) {
                ImageView icon = (ImageView) findViewById(R.id.dialog_building_info_icon_automatic_door);
                icon.setVisibility(View.GONE);
                iconsList.remove(icon);
            }
            if (!iconAssistance) {
                ImageView icon = (ImageView) findViewById(R.id.dialog_building_info_icon_needs_assistance);
                icon.setVisibility(View.GONE);
                iconsList.remove(icon);
            }
            if (!iconWC) {
                ImageView icon = (ImageView) findViewById(R.id.dialog_building_info_icon_wc);
                icon.setVisibility(View.GONE);
                iconsList.remove(icon);
            }
            if (!iconExit) {
                ImageView icon = (ImageView) findViewById(R.id.dialog_building_info_icon_exit);
                icon.setVisibility(View.GONE);
                iconsList.remove(icon);
            }
            reorderIcons(iconsList);
        }
        else {
            setContentView(R.layout.dialog_contact_layout);
        }
    }

    @Override
    public void onClick(View v) {
        if (blueprint == Constants.BUILDING_BLUEPRINT[0]) {
            //Call Activity of the building blueprint
            activity.startActivity(new Intent(activity.getBaseContext(), BlueprintActivity.class));
            //Close the dialog
            dismiss();
        }
        else {
            Toast.makeText(activity.getBaseContext(), "Please select a building",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        String selected = parent.getItemAtPosition(pos).toString();
        if (selected.equals(Constants.BUILDING_BLUEPRINT[0])) {
            blueprint = Constants.BUILDING_BLUEPRINT[0];
        }
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
        iconPlane = false;
        iconInclined = false;
        iconElevator = false;
        iconAutomaticDoor = false;
        iconAssistance = false;
        iconWC = false;
        iconExit = false;

        switch (building) {
            case 1 :
                iterateIconArray(Constants.BUILDING_ONE_ICONS);
                break;
            case 2 :
                iterateIconArray(Constants.BUILDING_TWO_ICONS);
                break;
            case 3 :
                iterateIconArray(Constants.BUILDING_THREE_ICONS);
                break;
            case 4 :
                iterateIconArray(Constants.BUILDING_FOUR_ICONS);
                break;
            case 5 :
                iterateIconArray(Constants.BUILDING_FIVE_ICONS);
                break;
            case 6 :
                iterateIconArray(Constants.BUILDING_SIX_ICONS);
                break;
            case 7 :
                iterateIconArray(Constants.BUILDING_SEVEN_ICONS);
                break;
            case 8 :
                iterateIconArray(Constants.BUILDING_EIGHT_ICONS);
                break;
            case 9 :
                iterateIconArray(Constants.BUILDING_NINE_ICONS);
                break;
            case 10 :
                iterateIconArray(Constants.BUILDING_TEN_ICONS);
                break;
            case 11 :
                iterateIconArray(Constants.BUILDING_ELEVEN_ICONS);
                break;
            case 12 :
                iterateIconArray(Constants.BUILDING_TWELVE_ICONS);
                break;
            case 13 :
                iterateIconArray(Constants.BUILDING_THIRTEEN_ICONS);
                break;
            case 14 :
                iterateIconArray(Constants.BUILDING_FOURTEEN_ICONS);
                break;
            case 15 :
                iterateIconArray(Constants.BUILDING_FIFTEEN_ICONS);
                break;
            case 16 :
                iterateIconArray(Constants.BUILDING_SIXTEEN_ICONS);
                break;
            case 17 :
                iterateIconArray(Constants.BUILDING_SEVENTEEN_ICONS);
                break;
            case 20 :
                iterateIconArray(Constants.BUILDING_TWENTY_ICONS);
                break;
            default:
                break;
        }
    }

    private void reorderIcons(ArrayList<ImageView> iconsList) {
        int column = 0;
        int row = 0;
        for (ImageView icon : iconsList) {
            if (icon != null) {
                GridLayout grid = (GridLayout) findViewById(R.id.dialog_building_info_grid_layout);
                GridLayout.LayoutParams layout = new GridLayout.LayoutParams();
                if (column >= 3) {
                    column = 0;
                    row++;
                    layout.columnSpec = grid.spec(column);
                    layout.rowSpec = grid.spec(row);
                } else {
                    layout.columnSpec = grid.spec(column);
                    layout.rowSpec = grid.spec(row);
                }
                column++;
                layout.width = 50;
                layout.height = 50;
                icon.setLayoutParams(layout);
            }
            else
                break;
        }
    }

    private void iterateIconArray(Constants.DEFAULT_ICONS[] iconArray) {
        for (Constants.DEFAULT_ICONS icon : iconArray) {
            if (icon == Constants.DEFAULT_ICONS.PLANE)
                iconPlane = true;
            if (icon == Constants.DEFAULT_ICONS.INCLINED)
                iconInclined = true;
            if (icon == Constants.DEFAULT_ICONS.ELEVATOR)
                iconElevator = true;
            if (icon == Constants.DEFAULT_ICONS.AUTOMATIC_DOOR)
                iconAutomaticDoor = true;
            if (icon == Constants.DEFAULT_ICONS.ASSISTANCE)
                iconAssistance = true;
            if (icon == Constants.DEFAULT_ICONS.WC)
                iconWC = true;
            if (icon == Constants.DEFAULT_ICONS.EXIT)
                iconExit = true;
        }
    }
}