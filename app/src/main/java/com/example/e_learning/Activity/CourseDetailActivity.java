package com.example.e_learning.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.e_learning.Adapter.CourseDetailAdapter;
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
String cname,descript,price,cid,duration,startdate,date;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
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

        cname = getIntent().getExtras().getString("Cname");
        descript = getIntent().getExtras().getString("Descript");
        price = getIntent().getExtras().getString("Price");
        cid = getIntent().getExtras().getString("Cid");
        duration = getIntent().getExtras().getString("Duration");
        startdate = getIntent().getExtras().getString("Startdate");
        date = startdate.split("T")[0];
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

        Enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseDetailActivity.this, SubscriptionActivity.class);
                intent.putExtra("Cname",cname);
                intent.putExtra("Price",price);
                intent.putExtra("Cid",cid);
                intent.putExtra("Duration",duration);
              startActivity(intent);
            }
        });

        GetCourseDetail();
    }

    private void GetCourseDetail() {
        try {
            String cid=Cid.getText().toString();
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
                adapter = new CourseDetailAdapter(CourseDetailActivity.this, courseDetailModels);
                recyclerview.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
