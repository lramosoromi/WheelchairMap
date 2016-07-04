package com.rolithunderbird.wheelchairmap.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.rolithunderbird.wheelchairmap.R;
import com.rolithunderbird.wheelchairmap.javaClases.TouchImageView;

public class BlueprintActivity extends AppCompatActivity {

    TouchImageView image;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blueprint);
    }

    public void btnChangeUnderground(View view) {
        image.setImageResource(R.drawable.building9_underground);
    }

    public void btnChangeMainFloor(View view) {
        image.setImageResource(R.drawable.building9_0floor);
    }

    public void btnChangeFirstFloor(View view) {
        image.setImageResource(R.drawable.building9_1floor);
    }

    public void btnChangeSecondFloor(View view) {
        image.setImageResource(R.drawable.building9_2floor);
    }

    public void btnChangeThirdFloor(View view) {
        image.setImageResource(R.drawable.building9_3floor);
    }
}