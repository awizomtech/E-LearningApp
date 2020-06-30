package com.example.e_learning.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.e_learning.Activity.HomePageActivity;
import com.example.e_learning.Activity.LoginActivity;
import com.example.e_learning.Helper.AccountHelper;
import com.example.e_learning.Model.LoginModel;
import com.example.e_learning.Model.ProfileModel;
import com.example.e_learning.R;
import com.example.e_learning.SharePrefrence.SharedPrefManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;
import java.util.jar.Attributes;

public class ProfileFragment extends Fragment {

    Context context;
    View rootView;
    String result;
    TextView Username, Name, Mob, Address, Email;
    LinearLayout ll_login_signup;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
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
                Name.setText(profileModel.getFirstName().toString() + " " + profileModel.getLastName().toString());
                Mob.setText(profileModel.getMobileNumber().toString());
                Email.setText(profileModel.getEmailAddrss().toString());
                String date = profileModel.getRegistrationDate().toString();
                String ne = date.split("T")[0];
                Address.setText(ne);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        ;
    }
}


