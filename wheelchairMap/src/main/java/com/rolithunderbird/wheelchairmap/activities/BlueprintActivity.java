package com.rolithunderbird.wheelchairmap.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.rolithunderbird.wheelchairmap.R;
import com.rolithunderbird.wheelchairmap.javaClasses.CustomDialog;
import com.rolithunderbird.wheelchairmap.javaClasses.TouchImageView;
import com.rolithunderbird.wheelchairmap.utils.Constants;

public class BlueprintActivity extends AppCompatActivity {

    private TouchImageView image;
    private String buildingSelected;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        buildingSelected = bundle.getString("Building");
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(buildingSelected);

        setContentView(R.layout.activity_blueprint);

        image = (TouchImageView) findViewById(R.id.activity_blueprint_image_view);
    }

    /**
     * Method that creates a menu
     *
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_blueprint, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_blueprint_action_info:
                CustomDialog appInfoCustomDialog = new CustomDialog(this, Constants.DIALOG_TYPE.BLUEPRINT_INFO);
                appInfoCustomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                appInfoCustomDialog.setBlueprint(buildingSelected);
                appInfoCustomDialog.show();
                return true;
            case R.id.menu_blueprint_action_show_location:
                Toast.makeText(this, "This feature is not available for the moment",
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_blueprint_action_underground:
                image.setImageResource(R.drawable.building9_underground);
                return true;
            case R.id.menu_blueprint_action_main_floor:
                image.setImageResource(R.drawable.building9_0floor);
                return true;
            case R.id.menu_blueprint_action_first_floor:
                image.setImageResource(R.drawable.building9_1floor);
                return true;
            case R.id.menu_blueprint_action_second_floor:
                image.setImageResource(R.drawable.building9_2floor);
                return true;
            case R.id.menu_blueprint_action_third_floor:
                image.setImageResource(R.drawable.building9_3floor);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}