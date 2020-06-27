package com.example.e_learning.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.e_learning.R;

import androidx.appcompat.app.AppCompatActivity;

public class VerifyPhoneActivity extends AppCompatActivity {

    Button okay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        InitView();
    }

    private void InitView() {
        okay =findViewById(R.id.btnVerifyCode);
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(VerifyPhoneActivity.this,HomePageActivity.class);
                startActivity(intent);
            }
        });
    }
}
