package com.solar.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.solar.Helper;
import com.solar.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashBoardActivity extends AppCompatActivity {

    ImageSlider slider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for making full screen
        setContentView(R.layout.activity_dash_board);
        ButterKnife.bind(this);
        slider = findViewById(R.id.slider);
        displaySlider();

    }

    private void displaySlider() {
        final List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.banner1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner3, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner4, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner5, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner6, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner7, ScaleTypes.FIT));
        slider.setImageList(slideModels, ScaleTypes.FIT);
    }

    @OnClick({R.id.banking_view, R.id.file_view, R.id.point_view, R.id.idea_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.banking_view:
                Helper.transectionActivityToActivity(DashBoardActivity.this, Solar_CalculatorActivity.class);
               // startActivity(new Intent(DashBoardActivity.this));
                break;
            case R.id.file_view:
                Intent intent = new Intent();
                break;
            case R.id.point_view:
                break;
            case R.id.idea_view:
                break;
        }
    }
}