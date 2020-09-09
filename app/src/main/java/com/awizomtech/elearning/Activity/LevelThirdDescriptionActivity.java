package com.awizomtech.elearning.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.awizomtech.elearning.Adapter.LevelTopicDetailAdapter;
import com.awizomtech.elearning.AppConfig.AppConfig;
import com.awizomtech.elearning.Helper.UserHelper;
import com.awizomtech.elearning.Model.LevelDetailTopicModel;
import com.awizomtech.elearning.R;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class LevelThirdDescriptionActivity extends AppCompatActivity {
    String cname, level, price, cid, duration, levelId, ImageCourse,Payable,Discount,USDValue;
    TextView CoursePrice, EnrollPrice, Duration;
    ImageView CourseImage;
    CardView Enroll;
    RecyclerView Topics;
    String result;
    LevelTopicDetailAdapter adapter;
    ProgressDialog progressDialog;
    private List<LevelDetailTopicModel> levelDetailTopicModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_level_third_description);
       InitView();
    }

    private void InitView() {
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
        USDValue = getIntent().getExtras().getString("USDValue");
        Payable = getIntent().getExtras().getString("payable");
        Discount = getIntent().getExtras().getString("discount");

        cname = CourseLevelActivity.getActivityInstance().getData();
        ImageCourse = CourseLevelActivity.getActivityInstance().getImage();
        CoursePrice = findViewById(R.id.pricecourse);
        CoursePrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        EnrollPrice =findViewById(R.id.price);
        Duration =findViewById(R.id.duration);
        Enroll =findViewById(R.id.enroll);
        CourseImage =findViewById(R.id.image);
        Duration.setText("Duration : " +duration.toString());
        CoursePrice.setText("Price : " +price.toString());
        EnrollPrice.setText(price.toString());
        Enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LevelThirdDescriptionActivity.this, SubscriptionActivity.class);
                intent.putExtra("levelID", levelId);
                intent.putExtra("level", level);
                intent.putExtra("Cid", cid);
                intent.putExtra("Duration", duration);
                intent.putExtra("Price", price);
                intent.putExtra("USDValue", USDValue);
                intent.putExtra("Price", Payable);
                intent.putExtra("Price", Discount);

                startActivity(intent);
                finish();
            }
        });
        GetDetailDetail();
    }
    private void GetDetailDetail() {
        try {
            String cid = getIntent().getExtras().getString("Cid");
            String levelid = getIntent().getExtras().getString("levelID");
            result = new UserHelper.GetLeveleDetalList().execute(cid.toString(),levelid.toString()).get();
            if (result.isEmpty()) {
                progressDialog.dismiss();
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<LevelDetailTopicModel>>() {
                }.getType();
                progressDialog.dismiss();
                levelDetailTopicModels = new Gson().fromJson(result, listType);
                Log.d("Error", levelDetailTopicModels.toString());
                adapter = new LevelTopicDetailAdapter(LevelThirdDescriptionActivity.this, levelDetailTopicModels);
                Topics.setAdapter(adapter);

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}