package com.rolithunderbird.wheelchairmap.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.rolithunderbird.wheelchairmap.R;
import com.rolithunderbird.wheelchairmap.javaClases.TouchImageView;

public class BlueprintActivity extends AppCompatActivity implements View.OnClickListener {

    TouchImageView image;
    float zoomFactor = 2f;
    boolean zoomedOut = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blueprint);

        image = (TouchImageView) findViewById(R.id.imageView);
        assert image != null;
        image.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(zoomedOut) {
            view.setScaleX(1);
            view.setScaleY(1);
            zoomedOut = false;
        }
        else {
            view.setScaleX(zoomFactor);
            view.setScaleY(zoomFactor);
            zoomedOut = true;
        }
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