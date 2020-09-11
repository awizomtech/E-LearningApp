package com.awizomtech.elearning.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.awizomtech.elearning.Adapter.LevelTopicDetailAdapter;
import com.awizomtech.elearning.Helper.UserHelper;
import com.awizomtech.elearning.Model.CourseLevelModel;
import com.awizomtech.elearning.Model.LevelDetailModel;
import com.awizomtech.elearning.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ShortCourseDetailActivity extends AppCompatActivity {
    String  price, cid, duration, Payable, Discount, USDValue, CourseName;
    TextView CoursePrice, EnrollPrice, Duration, DiscountText, PayableText, Tv_course_name;
    ImageView CourseImage;
    CardView Enroll;
    RecyclerView Topics;
    String result;
    LevelTopicDetailAdapter adapter;
    ProgressDialog progressDialog;
    private List<LevelDetailModel> levelDetailTopicModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_course);
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
        CourseName = getIntent().getExtras().getString("CourseName");
        cid = getIntent().getExtras().getString("Cid");
        USDValue = CourseListActivity.getActivityInstance().getUSD();
        CoursePrice = findViewById(R.id.pricecourse);
        EnrollPrice = findViewById(R.id.price);
        EnrollPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        Duration = findViewById(R.id.duration);
        DiscountText = findViewById(R.id.discount);
        Tv_course_name = findViewById(R.id.tv_course_name);
        PayableText = findViewById(R.id.payable);
        Enroll = findViewById(R.id.enroll);
        CourseImage = findViewById(R.id.image);
        Topics = findViewById(R.id.recyclerView);
        Topics.setHasFixedSize(true);
        Topics.setLayoutManager(new LinearLayoutManager(this));
        Tv_course_name.setText(CourseName);

        blink();
        GetCourseDetail();
        GetTopic();
    }
    private void GetCourseDetail() {
        try {
            result = new UserHelper.GetCourseShort().execute(cid.toString()).get();
            if (result.isEmpty()) {
                progressDialog.dismiss();
            } else {
                Type listType = new TypeToken<CourseLevelModel>() {
                }.getType();
                CourseLevelModel  courseLevelModel = new Gson().fromJson(result, listType);

                duration =courseLevelModel.getDuration();
                price = String.valueOf(courseLevelModel.getPrice());
                Payable = String.valueOf(courseLevelModel.getPayableAmount());
                Discount = String.valueOf(courseLevelModel.getPercentage());
                Duration.setText("Duration : " + duration.toString());
                float usd = Float.parseFloat(USDValue);
                float inr = Float.parseFloat(price);
                float payable = Float.parseFloat(Payable);
                float sum = inr / usd;
                float sum1 = payable / usd;
                String usdprice = String.valueOf(sum);
                String usdprice1 = String.valueOf(sum1);
                EnrollPrice.setText(" ₹" + price + "/" + usdprice + "$");
                DiscountText.setText(Discount + "% OFF");
                PayableText.setText(" ₹" + Payable + "/" + usdprice1 + "$");
                CoursePrice.setText("Price " + " ₹" + price + "/" + usdprice + "$");
                Enroll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(ShortCourseDetailActivity.this, SubscriptionActivity.class);
                        intent.putExtra("levelID", "0");
                        intent.putExtra("level", "Short");
                        intent.putExtra("Cid", cid);
                        intent.putExtra("Duration", duration);
                        intent.putExtra("Price", price);
                        intent.putExtra("USDValue", USDValue);
                        intent.putExtra("CourseName", CourseName);
                        intent.putExtra("payable", Payable);
                        intent.putExtra("discount", Discount);

                        startActivity(intent);
                        finish();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    private void GetTopic() {
        try {
            String levelid ="0";
            result = new UserHelper.GetLeveleDetalList().execute(cid.toString(),levelid.toString()).get();
            if (result.isEmpty()) {
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<LevelDetailModel>>() {
                }.getType();
                levelDetailTopicModels = new Gson().fromJson(result, listType);
                Log.d("Error", levelDetailTopicModels.toString());
                adapter = new LevelTopicDetailAdapter(ShortCourseDetailActivity.this, levelDetailTopicModels);
                Topics.setAdapter(adapter);

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void blink() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 1000;    //in milissegunds
                try {
                    Thread.sleep(timeToBlink);
                } catch (Exception e) {
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        if (DiscountText.getVisibility() == View.VISIBLE) {
                            DiscountText.setVisibility(View.INVISIBLE);
                        } else {
                            DiscountText.setVisibility(View.VISIBLE);
                        }
                        blink();
                    }
                });
            }
        }).start();
    }
}