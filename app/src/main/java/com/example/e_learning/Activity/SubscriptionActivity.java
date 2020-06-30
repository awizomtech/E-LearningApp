package com.example.e_learning.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_learning.Helper.AccountHelper;
import com.example.e_learning.Helper.UserHelper;
import com.example.e_learning.Model.CourseListModel;
import com.example.e_learning.Model.LoginModel;
import com.example.e_learning.R;
import com.example.e_learning.SharePrefrence.SharedPrefManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class SubscriptionActivity extends AppCompatActivity {

    String cname, price, cid, duration;
    TextView Tv_coursename, Tv_price, Tv_duration,PayableAmt;
EditText Transactionid;
CardView Next;
String result;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        InitView();
    }

    private void InitView() {
      /*  SelectPay  = (Spinner) findViewById(R.id.selectpaymenttype);
        SelectEmi  = (Spinner) findViewById(R.id.selectemi);*/
        Tv_coursename = findViewById(R.id.tv_course_name);
        Tv_price = findViewById(R.id.tv_price);
        Tv_duration = findViewById(R.id.tv_duration);
        Next = findViewById(R.id.enroll);
        Transactionid = findViewById(R.id.transactionid);
        PayableAmt = findViewById(R.id.tv_payable);
        cname = getIntent().getExtras().getString("Cname");
        price = getIntent().getExtras().getString("Price");
        cid = getIntent().getExtras().getString("Cid");
        duration = getIntent().getExtras().getString("Duration");

        Tv_coursename.setText(cname.toString());
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
                        String transactionid=Transactionid.getText().toString();
                        String usetid=SharedPrefManager.getInstance(SubscriptionActivity.this).getUser().getUserID();
                        result = new UserHelper.POSTPayment().execute(usetid.toString(), cid.toString(),price.toString(),transactionid.toString()).get();
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