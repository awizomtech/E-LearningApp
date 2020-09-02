package com.awizomtech.elearning.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.awizomtech.elearning.Helper.AccountHelper;
import com.awizomtech.elearning.R;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class RegisterActivity extends AppCompatActivity {
    EditText firtname,lastname,mobile,emailaddress,conpass,password;
    LinearLayout ll_submit;
    TextView login;
    String result;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_register);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        InitView();
    }
    private void InitView() {
        firtname=findViewById(R.id.et_fname);
        password = findViewById(R.id.et_password);
        login=findViewById(R.id.tv_login);
        lastname=findViewById(R.id.et_lname);
        emailaddress=findViewById(R.id.et_email);
        conpass=findViewById(R.id.et_cpassword);
        mobile=findViewById(R.id.et_mob);
        ll_submit=findViewById(R.id.ll_register);

        ll_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cpass=conpass.getText().toString();
                String pass=password.getText().toString();
                if(firtname.getText().toString().isEmpty()){
                    firtname.setError("Required !");
                }else if(lastname.getText().toString().isEmpty()){
                    lastname.setError("Required !");
                }else if(emailaddress.getText().toString().isEmpty()){
                    emailaddress.setError("Required !");
                }
                else if(mobile.getText().toString().isEmpty()||mobile.getText().toString().length()!=10){
                    mobile.setError("Required !");
                }
                else if(password.getText().toString().isEmpty()||password.getText().toString().length()!=6){
                    password.setError("Required !");
                }else if(conpass.getText().toString().isEmpty()){
                    conpass.setError("Required !");
                }else if(!pass.contains(cpass)){
                    password.setError("Password are not match !");
                }
                else {
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(view.getRootView().getContext());
                    LayoutInflater inflater = getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.progress_dialog, null);
                    alertbox.setView(dialogView);
                    alertbox.setCancelable(false);
                    final AlertDialog b = alertbox.create();
                    b.show();
                    new Timer().schedule(new TimerTask() {
                        public void run() {
                    String fname = firtname.getText().toString();
                    String pass = password.getText().toString();
                    String lname = lastname.getText().toString();
                    String mob = mobile.getText().toString();
                    String email = emailaddress.getText().toString();
                    String gender =" ";
                    try {
                        result = new AccountHelper.PostRegister().execute(fname.toString(), lname.toString(),mob.toString(), email.toString(),pass.toString(), gender.toString()).get();
                        if (result.isEmpty()) {
                            b.dismiss();
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Invalid request", Toast.LENGTH_SHORT).show();
                            result = new AccountHelper.PostRegister().execute(fname.toString(), lname.toString(),mob.toString(), email.toString(),pass.toString(), gender.toString()).get();
                        } else {
                            b.dismiss();
                            progressDialog.dismiss();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
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
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}