package com.awizomtech.elearning.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.awizomtech.elearning.Helper.UserHelper;
import com.awizomtech.elearning.R;
import com.awizomtech.elearning.SharePrefrence.SharedPrefManager;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class SubscriptionActivity extends AppCompatActivity {

    String cname,level, price, cid, duration,levelId;
    TextView Tv_coursename, Tv_price, Tv_duration,PayableAmt,Tv_LevelID,Tv_Level;
EditText Transactionid;
CardView Next;
String result;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_subscription);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        InitView();
    }
    private void InitView() {

        ImageView backpress=findViewById(R.id.back);
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Tv_coursename = findViewById(R.id.tv_course_name);
        Tv_price = findViewById(R.id.tv_price);
        Tv_duration = findViewById(R.id.tv_duration);
        Tv_LevelID = findViewById(R.id.tv_levelid);
        Tv_Level = findViewById(R.id.tv_level_name);
        Next = findViewById(R.id.enroll);
        Transactionid = findViewById(R.id.transactionid);
        PayableAmt = findViewById(R.id.tv_payable);
        levelId = getIntent().getExtras().getString("levelID");
        price = getIntent().getExtras().getString("Price");
        cid = getIntent().getExtras().getString("Cid");
        duration = getIntent().getExtras().getString("Duration");
        level = getIntent().getExtras().getString("level");
        cname=CourseLevelActivity.getActivityInstance().getData();
        Tv_LevelID.setText(levelId);
        Tv_coursename.setText("Course Name : "+cname.toString());
        Tv_Level.setText("Level Name : "+level.toString());
        Tv_price.setText(price.toString()+"₹");
        Tv_duration.setText(duration.toString());
        PayableAmt.setText(price.toString()+"₹");
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Transactionid.getText().toString().isEmpty()){
                    Transactionid.setError("Required !");
                }else{
                    progressDialog.show();
                    new Timer().schedule(new TimerTask() {
                        public void run() {
                    try {
                        String lid=Tv_LevelID.getText().toString();
                        String transactionid=Transactionid.getText().toString();
                        String usetid=SharedPrefManager.getInstance(SubscriptionActivity.this).getUser().getUserID();
                        String type ="Paid";
                        result = new UserHelper.POSTPayment().execute(usetid.toString(), cid.toString(),price.toString(),transactionid.toString(),lid.toString(),type.toString()).get();
                        if (result.isEmpty()) {
                            progressDialog.dismiss();
                        } else {
                            progressDialog.dismiss();
                                Intent intent = new Intent(SubscriptionActivity.this, MyCourseActivity.class);
                                startActivity(intent);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    };
                        }
                    }, 100);
                }
            }
        });

    }
}