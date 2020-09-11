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
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.awizomtech.elearning.Adapter.LevelTopicDetailAdapter;
import com.awizomtech.elearning.Helper.UserHelper;
import com.awizomtech.elearning.Model.LevelDetailModel;
import com.awizomtech.elearning.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class LevelOneDescriptionActivity extends AppCompatActivity {

    /*private List<LevelDetailModel> levelDetailTopicModels;*/
    String cname,level,price,cid,duration,levelId,ImageCourse,Payable,Discount,USDValue;
    TextView CoursePrice, EnrollPrice, Duration,DiscountText,PayableText;
    ImageView CourseImage;
    CardView Enroll;
    RecyclerView Topics;
    String result;
    LevelTopicDetailAdapter adapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_level_one_description);
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
        /* CoursePrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);*/
        EnrollPrice =findViewById(R.id.price);
        EnrollPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        Duration =findViewById(R.id.duration);
        DiscountText =findViewById(R.id.discount);
        PayableText =findViewById(R.id.payable);
        Enroll =findViewById(R.id.enroll);
        CourseImage =findViewById(R.id.image);
        Topics =findViewById(R.id.recyclerView);
        Topics.setHasFixedSize(true);
        Topics.setLayoutManager(new LinearLayoutManager(this));
        Duration.setText("Duration : " +duration.toString());
        float usd = Float.parseFloat(USDValue);
        float inr = Float.parseFloat(price);
        float payable=Float.parseFloat(Payable);
        float sum = inr / usd;
        float sum1 = payable / usd;
        String usdprice = String.valueOf(sum);
        String usdprice1 = String.valueOf(sum1);
        EnrollPrice.setText(" ₹" + price + "/" + usdprice + "$");
        DiscountText.setText(Discount+"% OFF");
        PayableText.setText(" ₹" + Payable + "/" + usdprice1 + "$");
        CoursePrice.setText("Price "+" ₹" + price + "/" + usdprice + "$");
        Enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LevelOneDescriptionActivity.this, SubscriptionActivity.class);
                intent.putExtra("levelID", levelId);
                intent.putExtra("level", level);
                intent.putExtra("Cid", cid);
                intent.putExtra("Duration", duration);
                intent.putExtra("Price", price);
                intent.putExtra("USDValue", USDValue);
                intent.putExtra("payable", Payable);
                intent.putExtra("discount", Discount);

                startActivity(intent);
                finish();
            }
        });

        GetDetailDetail();
        blink();
    }
    private void GetDetailDetail() {
        try {
            String cid = getIntent().getExtras().getString("Cid");
            String levelid = getIntent().getExtras().getString("levelID");
           String  results = new UserHelper.GetLeveleDetalList().execute(cid.toString(),levelid.toString()).get();
            if (results.isEmpty()) {

            } else {

                Gson gson = new Gson();
                Type listType = new TypeToken<List<LevelDetailModel>>() {
                }.getType();
                List<LevelDetailModel>  levelDetailTopicModels = new Gson().fromJson(results, listType);
                Log.d("Error", levelDetailTopicModels.toString());
                adapter = new LevelTopicDetailAdapter(LevelOneDescriptionActivity.this, levelDetailTopicModels);
                Topics.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    private void blink(){
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 1000;    //in milissegunds
                try{Thread.sleep(timeToBlink);}catch (Exception e) {}
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        if(DiscountText.getVisibility() == View.VISIBLE){
                            DiscountText.setVisibility(View.INVISIBLE);
                        }else{
                            DiscountText.setVisibility(View.VISIBLE);
                        }
                        blink();
                    }
                });
            }
        }).start();
    }
}