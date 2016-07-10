package com.rolithunderbird.wheelchairmap.activities;

import android.graphics.BitmapFactory;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BlueprintActivity extends AppCompatActivity {

    private TouchImageView image;
    private String buildingSelected;
    private ArrayList<File> blueprintFiles;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.blueprintFiles = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        buildingSelected = bundle.getString("Building");
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(buildingSelected);

        String buildingSelectedFileName = buildingSelected.replace(" ", "").toLowerCase();
        List<File> files = Constants.getImageFiles();
        for (File file : files) {
            if (file.getName().contains(buildingSelectedFileName))
                blueprintFiles.add(file);
        }

        setContentView(R.layout.activity_blueprint);

        image = (TouchImageView) findViewById(R.id.activity_blueprint_image_view);
        image.setImageBitmap(BitmapFactory.decodeFile(blueprintFiles.get(1).getPath()));
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
                image.setImageBitmap(BitmapFactory.decodeFile(blueprintFiles.get(0).getPath()));
                return true;
            case R.id.menu_blueprint_action_main_floor:
                image.setImageBitmap(BitmapFactory.decodeFile(blueprintFiles.get(1).getPath()));
                return true;
            case R.id.menu_blueprint_action_first_floor:
                image.setImageBitmap(BitmapFactory.decodeFile(blueprintFiles.get(2).getPath()));
                return true;
            case R.id.menu_blueprint_action_second_floor:
                image.setImageBitmap(BitmapFactory.decodeFile(blueprintFiles.get(3).getPath()));
                return true;
            case R.id.menu_blueprint_action_third_floor:
                image.setImageBitmap(BitmapFactory.decodeFile(blueprintFiles.get(4).getPath()));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}