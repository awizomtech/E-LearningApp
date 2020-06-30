package com.example.e_learning.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_learning.Helper.AccountHelper;
import com.example.e_learning.Model.LoginModel;
import com.example.e_learning.R;
import com.example.e_learning.SharePrefrence.SharedPrefManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
EditText username,password;
    LinearLayout ll_submit;
TextView Register;
String result;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitView();
    }
    private void InitView() {
         progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        username=findViewById(R.id.et_usename);
        password = findViewById(R.id.et_password);
        Register=findViewById(R.id.tv_register);
        ll_submit=findViewById(R.id.ll_submit);

        ll_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(username.getText().toString().isEmpty()||username.getText().toString().length()!=10){
                    username.setError("Required !");
                }else if(password.getText().toString().isEmpty()||password.getText().toString().length()!=6){
                    password.setError("Required !");
                } else {
                    progressDialog.show();
                    new Timer().schedule(new TimerTask() {
                        public void run() {
                            String Username = username.getText().toString();
                            String Password = password.getText().toString();
                            try {
                                result = new AccountHelper.LogIn().execute(Username.toString(), Password.toString()).get();

                                String first=result.split(":")[1];
                                String second=first.split(",")[0];
                                if (result.isEmpty()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Invalid request", Toast.LENGTH_SHORT).show();
                                }
                                else  {
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
                                        progressDialog.dismiss();
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
                    }, 100);

                }
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


    @SuppressLint("ResourceType")
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            final AlertDialog.Builder alertbox = new AlertDialog.Builder(LoginActivity.this);

            alertbox.setIconAttribute(90);
            alertbox.setIcon(R.drawable.exit);
            alertbox.setTitle("Do You Want To Exit ?");

            alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    // finish used for destroyed activity

                    finishAffinity();
                    System.exit(0);
                }
            });

            alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {

                }
            });
            alertbox.show();
        }
        return super.onKeyDown(keyCode, event);
    }
}