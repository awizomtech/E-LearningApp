package com.awizomtech.elearning.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.awizomtech.elearning.Helper.UserHelper;
import com.awizomtech.elearning.R;
import com.awizomtech.elearning.SharePrefrence.SharedPrefManager;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class FreeSubcriptionActivity extends AppCompatActivity {
String cname,cid,Type;
    ProgressDialog progressDialog;
    TextView Tv_coursename;
    CardView Next;
    String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_subcription);
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
        cname = getIntent().getExtras().getString("CourseName");
        cid = getIntent().getExtras().getString("Cid");
        Type = getIntent().getExtras().getString("Type");
        Tv_coursename = findViewById(R.id.tv_course_name);
        Tv_coursename.setText(cname.toString());
        Next = findViewById(R.id.enroll);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    progressDialog.show();
                    new Timer().schedule(new TimerTask() {
                        public void run() {
                            try {
                                String lid="0";
                                String transactionid="0";
                                String price="0";
                                String usetid= SharedPrefManager.getInstance(FreeSubcriptionActivity.this).getUser().getUserID();
                                String type =Type.toString();
                                result = new UserHelper.POSTPayment().execute(usetid.toString(), cid.toString(),price.toString(),transactionid.toString(),lid.toString(),type.toString()).get();
                                if (result.isEmpty()) {
                                    progressDialog.dismiss();
                                } else {
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(FreeSubcriptionActivity.this, MyCourseActivity.class);
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

        });
    }
}