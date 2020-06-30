package com.example.e_learning.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.example.e_learning.Adapter.LearningAdapter;
import com.example.e_learning.Helper.UserHelper;
import com.example.e_learning.Model.CourseDetailModel;
import com.example.e_learning.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class LearningActivity extends AppCompatActivity {
    LearningAdapter adapter;
    private List<CourseDetailModel> courseDetailModels;
    RecyclerView recyclerview;
    SwipeRefreshLayout mSwipeRefreshLayout;
    String result;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        InitView();
    }

    private void InitView() {

        recyclerview = findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
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
             String cid = getIntent().getExtras().getString("Cid");
            result = new UserHelper.GetCourseTopicList().execute(cid.toString()).get();
            if (result.isEmpty()) {
                progressDialog.dismiss();
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<CourseDetailModel>>() {
                }.getType();
                progressDialog.dismiss();
                courseDetailModels = new Gson().fromJson(result, listType);
                Log.d("Error", courseDetailModels.toString());
                adapter = new LearningAdapter(LearningActivity.this, courseDetailModels);
                recyclerview.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}