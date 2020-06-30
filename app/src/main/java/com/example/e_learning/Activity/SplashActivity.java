package com.example.e_learning.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.e_learning.R;
import com.example.e_learning.SharePrefrence.SharedPrefManager;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        String userid = SharedPrefManager.getInstance(this).getUser().getUserID().toString();
        if (userid != "") {
            new Timer().schedule(new TimerTask() {
                public void run() {
                    startActivity(new Intent(SplashActivity.this, HomePageActivity.class));
                    finish();

                }
            }, 2000);

        }
        else {
            new Timer().schedule(new TimerTask() {
                public void run() {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();

                }
            }, 2000);
        }
    }
}