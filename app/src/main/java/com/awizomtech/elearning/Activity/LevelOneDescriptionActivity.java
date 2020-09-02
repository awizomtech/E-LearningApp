package com.awizomtech.elearning.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.awizomtech.elearning.R;

public class LevelOneDescriptionActivity extends AppCompatActivity {
    String cname,level, price, cid, duration,levelId,ImageCourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_one_description);
        levelId = getIntent().getExtras().getString("levelID");
        price = getIntent().getExtras().getString("Price");
        cid = getIntent().getExtras().getString("Cid");
        duration = getIntent().getExtras().getString("Duration");
        level = getIntent().getExtras().getString("level");
        cname=CourseLevelActivity.getActivityInstance().getData();
        ImageCourse=CourseLevelActivity.getActivityInstance().getImage();
    }
}