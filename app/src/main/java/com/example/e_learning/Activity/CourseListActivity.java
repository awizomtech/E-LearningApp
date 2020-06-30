package com.example.e_learning.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.example.e_learning.Adapter.CourseAdapter;
import com.example.e_learning.Helper.UserHelper;
import com.example.e_learning.Model.CourseListModel;
import com.example.e_learning.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class CourseListActivity extends AppCompatActivity {
    CourseAdapter adapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<CourseListModel> courseListModels;
    private String result = "";
    ProgressDialog progressDialog;
    androidx.recyclerview.widget.RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        InitView();
    }



    private void InitView() {


        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               GetCourselist();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        GetCourselist();
    }

    private void GetCourselist() {
        try {
            result = new UserHelper.GetCourseList().execute().get();
            if (result.isEmpty()) {
                progressDialog.dismiss();
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<CourseListModel>>() {
                }.getType();
                progressDialog.dismiss();
                courseListModels= new Gson().fromJson(result, listType);
                Log.d("Error", courseListModels.toString());
                adapter = new CourseAdapter(CourseListActivity.this, courseListModels);
                recyclerView.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
