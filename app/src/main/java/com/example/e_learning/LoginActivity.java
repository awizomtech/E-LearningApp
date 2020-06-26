package com.example.e_learning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_learning.Helper.AccountHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
EditText username,password;
    LinearLayout ll_submit;
TextView Register;
String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitView();
    }
    private void InitView() {
        username=findViewById(R.id.et_usename);
        password = findViewById(R.id.et_password);
        Register=findViewById(R.id.tv_register);
        ll_submit=findViewById(R.id.ll_submit);
        ll_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().isEmpty()){
                    username.setError("Required !");
                }else if(password.getText().toString().isEmpty()){
                    password.setError("Required !");
                }
                else {
                    String Username = username.getText().toString();
                    String Password = password.getText().toString();
                    try {
                        result = new AccountHelper.LogIn().execute(Username.toString(), Password.toString()).get();
                        if (result.isEmpty()) {
                            Toast.makeText(LoginActivity.this, "Invalid request", Toast.LENGTH_SHORT).show();
                            result = new AccountHelper.LogIn().execute(Username.toString(), Password.toString()).get();
                        } else {

                            Type listType = new TypeToken<LoginModel>() {
                            }.getType();
                            LoginModel loginModel = new Gson().fromJson(result, listType);
                            String userid = String.valueOf(loginModel.getUserID());
                            String usernamebyres = String.valueOf(loginModel.getUserName());
                            String studid=String.valueOf(loginModel.getID());
                            if (!userid.equals("null")) {
                                LoginModel loginmodel1 = new LoginModel();
                                loginmodel1.UserID = String.valueOf(userid.toString());
                                loginmodel1.UserName = usernamebyres;
                                loginmodel1.ID=Integer.valueOf(studid);
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(loginmodel1);
                                Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "User ID or Password are not Correct", Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    };
                }
            }
        });
    }
}