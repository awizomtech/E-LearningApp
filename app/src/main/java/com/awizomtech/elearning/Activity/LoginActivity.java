package com.awizomtech.elearning.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.awizomtech.elearning.Helper.AccountHelper;
import com.awizomtech.elearning.Model.LoginModel;
import com.awizomtech.elearning.Model.ProfileModel;
import com.awizomtech.elearning.R;
import com.awizomtech.elearning.SharePrefrence.SharedPrefManager;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 1;
    String UserName, Email, Image;
    EditText username, password;
    LinearLayout ll_submit, Googlesigin;
    TextView Register;
    String result;
    ProgressDialog progressDialog;
    private Context mCtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_login);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        InitView();
    }

    private void InitView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        username = findViewById(R.id.et_usename);
        password = findViewById(R.id.et_password);
        Register = findViewById(R.id.tv_register);
        ll_submit = findViewById(R.id.ll_submit);
        Googlesigin = findViewById(R.id.googlesigin);
        Googlesigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, RC_SIGN_IN);
            }
        });
        ll_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (username.getText().toString().isEmpty() || username.getText().toString().length() != 10) {
                    username.setError("Required !");
                /*}else if(password.getText().toString().isEmpty()||password.getText().toString().length()!=6){
                    password.setError("Required !");
                }*/
                } else if (password.getText().toString().isEmpty()) {
                    password.setError("Required !");
                } else {
                    /*     progressDialog.show();*/
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(view.getRootView().getContext());
                    LayoutInflater inflater = getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.progress_dialog, null);
                    alertbox.setView(dialogView);
                    alertbox.setCancelable(false);
                    final AlertDialog b = alertbox.create();
                    b.show();
                    new Timer().schedule(new TimerTask() {
                        public void run() {
                            String Username = username.getText().toString();
                            String Password = password.getText().toString();
                            try {
                                result = new AccountHelper.LogIn().execute(Username.toString(), Password.toString()).get();
                                String first = result.split(":")[1];
                                String second = first.split(",")[0];
                                if (second.contains("null")) {
                                    b.dismiss();
                                    /*     Toast.makeText(LoginActivity.this, "Invalid request", Toast.LENGTH_SHORT).show();*/
                                } else {
                                    b.dismiss();
                                    Type listType = new TypeToken<LoginModel>() {
                                    }.getType();
                                    LoginModel loginModel = new Gson().fromJson(result, listType);
                                    String userid = String.valueOf(loginModel.getUserID());
                                    String usernamebyres = String.valueOf(loginModel.getUserName());
                                    String studid = String.valueOf(loginModel.getID());
                                    String mobileno = String.valueOf(loginModel.getMobileNo());
                                    if (!userid.equals("null")) {
                                        LoginModel loginmodel1 = new LoginModel();
                                        loginmodel1.UserID = String.valueOf(userid.toString());
                                        loginmodel1.UserName = usernamebyres;
                                        loginmodel1.ID = Integer.valueOf(studid);
                                        loginmodel1.MobileNo = mobileno;
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
                            }

                        }
                    }, 100);

                }
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            UserName = account.getDisplayName();
            Email = account.getEmail();
            Image = String.valueOf(account.getPhotoUrl());
            gotoProfile();
        } else {
            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    private void gotoProfile() {

        try {
            result = new AccountHelper.GetGoogleLogin().execute(Email.toString()).get();
          /*  String first = result.split(":")[1];
            String second = first.split(",")[0];*/

           /* if (second.contains("null")) {
                Googlesignup();
            }*/
            if (result.equals("null")) {
                Googlesignup();
            }else {
                Type listType = new TypeToken<ProfileModel>() {
                }.getType();
                ProfileModel profileModel = new Gson().fromJson(result, listType);
                String userid = profileModel.getUserID();
                String usernamebyres = profileModel.getFirstName();
                String studid = String.valueOf(profileModel.getID());
                String mobileno = profileModel.getMobileNumber();
                if (!userid.equals("null")) {
                    LoginModel loginmodel1 = new LoginModel();
                    loginmodel1.UserID = String.valueOf(userid.toString());
                    loginmodel1.UserName = usernamebyres;
                    loginmodel1.ID = Integer.valueOf(studid);
                    loginmodel1.MobileNo = mobileno;
                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(loginmodel1);
                    Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                    startActivity(intent);
                }else{
                    Googlesignup();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void Googlesignup() {

        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.googlesign_dialog, null);
        alertbox.setView(dialogView);
        alertbox.setCancelable(false);
        final AlertDialog b = alertbox.create();
        b.show();
        TextView Username = dialogView.findViewById(R.id.uname);
        final EditText Pass = dialogView.findViewById(R.id.pass);
        final EditText Mobile = dialogView.findViewById(R.id.mobile);
        Button Continue = dialogView.findViewById(R.id.continuebtn);
        ImageView viewImage=dialogView.findViewById(R.id.image);
        Username.setText(UserName.toString());
        try{
            Glide.with(this).load(Image).into(viewImage);
        }catch (NullPointerException e){
            Toast.makeText(getApplicationContext(),"image not found",Toast.LENGTH_LONG).show();
        }
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Pass.getText().toString().isEmpty()) {
                    Pass.setError("Required !");
                } else if (Mobile.getText().toString().isEmpty()) {
                    Mobile.setError("Required !");
                } else {
                    String fname = UserName.toString();
                    String pass = Pass.getText().toString();
                    String lname ="";
                    String mob = Mobile.getText().toString();
                    String email = Email.toString();
                    String gender = " ";
                    progressDialog.show();
                    try {
                        result = new AccountHelper.PostRegister().execute(fname.toString(), lname.toString(), mob.toString(), email.toString(), pass.toString(), gender.toString()).get();
                        if (result.isEmpty()) {
                        } else {
                            progressDialog.dismiss();
                            b.dismiss();
                            Type listType = new TypeToken<ProfileModel>() {
                            }.getType();
                            ProfileModel profileModel = new Gson().fromJson(result, listType);
                            String userid = profileModel.getUserID();
                            String usernamebyres = profileModel.getFirstName();
                            String studid = String.valueOf(profileModel.getID());
                            String mobileno = profileModel.getMobileNumber();
                            if (!userid.equals("null")) {
                                LoginModel loginmodel1 = new LoginModel();
                                loginmodel1.UserID = String.valueOf(userid.toString());
                                loginmodel1.UserName = usernamebyres;
                                loginmodel1.ID = Integer.valueOf(studid);
                                loginmodel1.MobileNo = mobileno;
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(loginmodel1);
                                Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
    }
}