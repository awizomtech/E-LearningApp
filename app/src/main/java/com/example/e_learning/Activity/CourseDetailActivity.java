package com.example.e_learning.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.e_learning.Helper.UserHelper;
import com.example.e_learning.Model.CourseDetailModel;
import com.example.e_learning.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class CourseDetailActivity extends AppCompatActivity {

    CourseDetailAdapter adapter;
    private List<CourseDetailModel> courseDetailModels;
    CardView Enroll;
    TextView Price, startDate, CourseName, Description, Duration, Cid;
    RecyclerView recyclerview;
    SwipeRefreshLayout mSwipeRefreshLayout;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        InitView();
    }

    private void InitView() {
        Enroll = findViewById(R.id.enroll);
        Price = findViewById(R.id.price);
        startDate = findViewById(R.id.tv_starts_on);
        CourseName = findViewById(R.id.tv_course_name);
        Description = findViewById(R.id.tv_discription);
        Duration = findViewById(R.id.tv_duration);
        Cid = findViewById(R.id.tv_cid);
        recyclerview = findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);

        String cname = getIntent().getExtras().getString("Cname");
        String descript = getIntent().getExtras().getString("Descript");
        String price = getIntent().getExtras().getString("Price");
        String cid = getIntent().getExtras().getString("Cid");
        String duration = getIntent().getExtras().getString("Duration");
        String startdate = getIntent().getExtras().getString("Startdate");
        String date = startdate.split("T")[0];
        Price.setText(price + "₹");
        startDate.setText(date);
        CourseName.setText(cname);
        Description.setText(descript);
        Duration.setText(duration);
        Cid.setText(cid);

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
            String cid=Cid.getText().toString();
            result = new UserHelper.GetCourseTopicList().execute(cid.toString()).get();
            if (result.isEmpty()) {
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<CourseDetailModel>>() {
                }.getType();
                courseDetailModels = new Gson().fromJson(result, listType);
                Log.d("Error", courseDetailModels.toString());
                adapter = new CourseDetailAdapter(CourseDetailActivity.this, courseDetailModels);
                recyclerview.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
