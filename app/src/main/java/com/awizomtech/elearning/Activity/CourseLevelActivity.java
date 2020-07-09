package com.awizomtech.elearning.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.awizomtech.elearning.Adapter.CourseLevelAdapter;
import com.awizomtech.elearning.Helper.UserHelper;
import com.awizomtech.elearning.Model.CourseLevelModel;
import com.awizomtech.elearning.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class CourseLevelActivity extends AppCompatActivity {
    static CourseLevelActivity INSTANCE;
    String data="FirstActivity";
    CourseLevelAdapter adapter;
    private List<CourseLevelModel> courseLevelModels;
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
        setContentView(R.layout.activity_course_level);
        INSTANCE=this;
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
        data=cname;
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
            String cid=Cid.getText().toString();
            result = new UserHelper.GetCourseLevelList().execute(cid.toString()).get();
            if (result.isEmpty()) {
                progressDialog.dismiss();
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<CourseLevelModel>>() {
                }.getType();
                progressDialog.dismiss();
                courseLevelModels = new Gson().fromJson(result, listType);
                Log.d("Error", courseLevelModels.toString());
                adapter = new CourseLevelAdapter(CourseLevelActivity.this, courseLevelModels);
                recyclerview.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static CourseLevelActivity getActivityInstance()
    {
        return INSTANCE;
    }

    public String getData()
    {
        return this.data;
    }
}
