package com.awizomtech.elearning.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.awizomtech.elearning.Adapter.MyCourseLevelAdapter;
import com.awizomtech.elearning.Helper.UserHelper;
import com.awizomtech.elearning.Model.MyCourseLevelModel;
import com.awizomtech.elearning.R;
import com.awizomtech.elearning.SharePrefrence.SharedPrefManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MyCourseLevelActivity extends AppCompatActivity {

    MyCourseLevelAdapter adapter;
    private List<MyCourseLevelModel> myCourseLevelModels;
    CardView Enroll;
    TextView Price, startDate, CourseName, Description, Duration, Cid;
    RecyclerView recyclerview;
    SwipeRefreshLayout mSwipeRefreshLayout;
    String result;
    String cname,descript,price,cid,duration,startdate,date;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course_level);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        InitView();
    }

    private void InitView() {
        ImageView backpress=findViewById(R.id.back);
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        CourseName = findViewById(R.id.tv_course_name);
        Cid = findViewById(R.id.tv_cid);
        recyclerview = findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        cname = getIntent().getExtras().getString("CourseName");
        cid = getIntent().getExtras().getString("Cid");
        Cid.setText(cid);
        CourseName.setText(cname);

        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetCourseDetail();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        GetCourseDetail();
    }

    private void GetCourseDetail() {
        try {
            String userid= SharedPrefManager.getInstance(this).getUser().getUserID();
            String cid=Cid.getText().toString();
            result = new UserHelper.GetMyCourseLevelList().execute(cid.toString(),userid.toString()).get();
            if (result.isEmpty()) {
                progressDialog.dismiss();
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<MyCourseLevelModel>>() {
                }.getType();
                progressDialog.dismiss();
                myCourseLevelModels = new Gson().fromJson(result, listType);
                Log.d("Error", myCourseLevelModels.toString());
                adapter = new MyCourseLevelAdapter(MyCourseLevelActivity.this, myCourseLevelModels);
                recyclerview.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}