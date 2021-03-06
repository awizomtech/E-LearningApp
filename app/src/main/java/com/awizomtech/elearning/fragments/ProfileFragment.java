package com.awizomtech.elearning.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.awizomtech.elearning.Activity.EditProfileActivity;
import com.awizomtech.elearning.Activity.ProfileActivity;
import com.awizomtech.elearning.AppConfig.AppConfig;
import com.awizomtech.elearning.Helper.AccountHelper;
import com.awizomtech.elearning.Model.ProfileModel;
import com.awizomtech.elearning.R;
import com.awizomtech.elearning.SharePrefrence.SharedPrefManager;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

public class ProfileFragment extends Fragment {

    Context context;
    View rootView;
    String result;
    TextView Username, Name, Mob, Address, Email;
    LinearLayout ll_login_signup;
    ProgressDialog progressDialog;
    de.hdodenhof.circleimageview.CircleImageView imageview;
    LinearLayout Edit;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.newfragment_profile, container, false);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Inintview();

        return rootView;
    }

    private void Inintview() {

        Username = rootView.findViewById(R.id.username);
        Name = rootView.findViewById(R.id.name);
        Address = rootView.findViewById(R.id.address);
        Mob = rootView.findViewById(R.id.mob);
        Email = rootView.findViewById(R.id.email);
        imageview = rootView.findViewById(R.id.iconimage);
        Edit = rootView.findViewById(R.id.ll_submit);
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        try {
            String userid = SharedPrefManager.getInstance(context).getUser().getUserID();
            result = new AccountHelper.Profile().execute(userid.toString()).get();
            if (result.isEmpty()) {
                progressDialog.dismiss();
            } else {

                Type listType = new TypeToken<ProfileModel>() {
                }.getType();
                progressDialog.dismiss();
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
                    Glide.with(getContext()).load(AppConfig.BASE_URL + profileModel.getProfilePhoto()).into(imageview);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}


