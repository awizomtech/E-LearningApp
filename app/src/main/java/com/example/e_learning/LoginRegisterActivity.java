package com.example.e_learning;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class LoginRegisterActivity extends AppCompatActivity {

    LinearLayout ll_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        InitView();
    }

    private void InitView() {
        ll_submit=findViewById(R.id.ll_submit);
        ll_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginRegisterActivity.this,VerifyPhoneActivity.class);
                startActivity(intent);
            }
        });
    }
}
