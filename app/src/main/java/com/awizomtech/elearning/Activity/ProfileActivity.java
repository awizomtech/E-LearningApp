package com.awizomtech.elearning.Activity;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.awizomtech.elearning.AppConfig.AppConfig;
import com.awizomtech.elearning.Helper.AccountHelper;
import com.awizomtech.elearning.Model.ProfileModel;
import com.awizomtech.elearning.R;
import com.awizomtech.elearning.SharePrefrence.SharedPrefManager;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class ProfileActivity extends AppCompatActivity {
    String result;
    TextView Username, Name,Mob,Address,Email;
    de.hdodenhof.circleimageview.CircleImageView imageView;
    LinearLayout Edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_profile);
        Inintview();
    }
    private void Inintview() {
ImageView backpress=findViewById(R.id.back);
      backpress.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              onBackPressed();
          }
      });
      
        Username =findViewById(R.id.username);
        Name=findViewById(R.id.name);
        Address=findViewById(R.id.address);
        Mob=findViewById(R.id.mob);
        Email=findViewById(R.id.email);
        imageView=findViewById(R.id.Iconimage2);
       Edit = findViewById(R.id.ll_submit);
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });
        try {
            String userid= SharedPrefManager.getInstance(this).getUser().getUserID();
            result = new AccountHelper.Profile().execute(userid.toString()).get();
            if (result.isEmpty()) {
            } else {

                Type listType = new TypeToken<ProfileModel>() {
                }.getType();
                ProfileModel profileModel = new Gson().fromJson(result, listType);
                Username.setText(profileModel.getFirstName().toString());
                String Lname="";
                Lname =String.valueOf(profileModel.getLastName());
                if(Lname.equals("null")){
                    Name.setText(profileModel.getFirstName().toString());
                }else {
                    Name.setText(profileModel.getFirstName().toString() + " " +Lname.toString());
                }

                Mob.setText(profileModel.getMobileNumber().toString());
                Email.setText(profileModel.getEmailAddrss().toString());
                String date = profileModel.getRegistrationDate().toString();
                String ne = date.split("T")[0];
                Address.setText(ne);
                try {
                    Glide.with(this).load(AppConfig.BASE_URL + profileModel.getProfilePhoto()).into(imageView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        };
    }
}