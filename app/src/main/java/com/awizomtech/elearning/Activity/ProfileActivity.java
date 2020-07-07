package com.awizomtech.elearning.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.awizomtech.elearning.Helper.AccountHelper;
import com.awizomtech.elearning.Model.ProfileModel;
import com.awizomtech.elearning.R;
import com.awizomtech.elearning.SharePrefrence.SharedPrefManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

public class ProfileActivity extends AppCompatActivity {
    String result;
    TextView Username, Name,Mob,Address,Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


        try {
            String userid= SharedPrefManager.getInstance(this).getUser().getUserID();
            result = new AccountHelper.Profile().execute(userid.toString()).get();
            if (result.isEmpty()) {
            } else {

                Type listType = new TypeToken<ProfileModel>() {
                }.getType();
                ProfileModel profileModel = new Gson().fromJson(result, listType);
                Username.setText(profileModel.getFirstName().toString());
                Name.setText(profileModel.getFirstName().toString()+" "+profileModel.getLastName().toString());
                Mob.setText(profileModel.getMobileNumber().toString());
                Email.setText(profileModel.getEmailAddrss().toString());
                String date=profileModel.getRegistrationDate().toString();
                String ne=date.split("T")[0];
                Address.setText(ne);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        };
    }
}