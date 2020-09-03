package com.awizomtech.elearning.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.awizomtech.elearning.R;
import com.awizomtech.elearning.SharePrefrence.SharedPrefManager;

public class StartFitnessActivity extends AppCompatActivity {
    EditText Distance,RunningTime,RestTime,Round;
    Button Start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_start_fitness);
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
        ImageView Logout=findViewById(R.id.logout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager sp = new SharedPrefManager(StartFitnessActivity.this);
                sp.logout();
                startActivity(new Intent(StartFitnessActivity.this, LoginActivity.class));
                finish();
            }
        });
        Distance=findViewById(R.id.edi_distance);
        RunningTime=findViewById(R.id.edi_runningtime);
        RestTime=findViewById(R.id.ed_resttime);
        Round=findViewById(R.id.ed_round);
        Start=findViewById(R.id.btnstart);


        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Distance.getText().toString().isEmpty()){
                    Distance.setError("Required !");
                }else if(RunningTime.getText().toString().isEmpty()){
                    RunningTime.setError("Required !");
                }else if(RestTime.getText().toString().isEmpty()){
                    RestTime.setError("Required !");
                }else if(Round.getText().toString().isEmpty()){
                    Round.setError("Required !");
                }else {
                    String distance = Distance.getText().toString();
                    String ruunningtime = RunningTime.getText().toString();
                    String resttime = RestTime.getText().toString();
                    String round = Round.getText().toString();
                    Intent intent = new Intent(StartFitnessActivity.this, FitnessTestActivity.class);
                    intent.putExtra("Distance", distance);
                    intent.putExtra("RunningTime", ruunningtime);
                    intent.putExtra("RestTime", resttime);
                    intent.putExtra("Round", round);
                    startActivity(intent);
                }
            }
        });
    }
}