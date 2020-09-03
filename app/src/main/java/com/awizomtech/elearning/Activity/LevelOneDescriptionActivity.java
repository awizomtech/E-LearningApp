package com.awizomtech.elearning.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.awizomtech.elearning.AppConfig.AppConfig;
import com.awizomtech.elearning.R;
import com.bumptech.glide.Glide;

public class LevelOneDescriptionActivity extends AppCompatActivity {
    String cname, level, price, cid, duration, levelId, ImageCourse;
    TextView CoursePrice, EnrollPrice, Duration;
    ImageView CourseImage;
    CardView Enroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_one_description);
        ImageView backpress = findViewById(R.id.back);
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        levelId = getIntent().getExtras().getString("levelID");
        price = getIntent().getExtras().getString("Price");
        cid = getIntent().getExtras().getString("Cid");
        duration = getIntent().getExtras().getString("Duration");
        level = getIntent().getExtras().getString("level");
        cname = CourseLevelActivity.getActivityInstance().getData();
        ImageCourse = CourseLevelActivity.getActivityInstance().getImage();
        CoursePrice = findViewById(R.id.pricecourse);
        EnrollPrice =findViewById(R.id.price);
        Duration =findViewById(R.id.duration);
        Enroll =findViewById(R.id.enroll);
        CourseImage =findViewById(R.id.image);
        Duration.setText("Duration : " +duration.toString());
        CoursePrice.setText("Price : " +price.toString());
        EnrollPrice.setText(price.toString());
       /* try {
            Glide.with(this).load(AppConfig.BASE_URL + ImageCourse.toString()).into(CourseImage);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        Enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LevelOneDescriptionActivity.this, SubscriptionActivity.class);
                intent.putExtra("levelID", levelId);
                intent.putExtra("level", level);
                intent.putExtra("Cid", cid);
                intent.putExtra("Duration", duration);
                intent.putExtra("Price", price);
                startActivity(intent);

            }
        });
    }
}