package com.awizomtech.elearning.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.awizomtech.elearning.Adapter.LearningAdapter;
import com.awizomtech.elearning.AppConfig.AppConfig;
import com.awizomtech.elearning.Helper.UserHelper;
import com.awizomtech.elearning.Model.ExamProgressModel;
import com.awizomtech.elearning.Model.LevelTopicModel;
import com.awizomtech.elearning.R;
import com.awizomtech.elearning.SharePrefrence.SharedPrefManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class FreeLearningActivity extends AppCompatActivity {
    LearningAdapter adapter;
    private List<LevelTopicModel> levelTopicModels;
    private List<ExamProgressModel> examProgressModels;
    RecyclerView recyclerview;
    SwipeRefreshLayout mSwipeRefreshLayout;
    String result;
    Button Certificate;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_learning);
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
        recyclerview = findViewById(R.id.recyclerView);
        Certificate = findViewById(R.id.certificate);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetExamProgress();
                GetCourseDetail();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        GetExamProgress();
        GetCourseDetail();

        Certificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cid = getIntent().getExtras().getString("Cid");
                String url= AppConfig.BASE_URL+"Student/Student/PrintCertificate?CourseID="+cid+"&LevelID="+0;
                final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    private void GetCourseDetail() {
        try {
            String cid = getIntent().getExtras().getString("Cid");
            result = new UserHelper.GetFreeTopicList().execute(cid.toString()).get();
            if (result.isEmpty()) {
                progressDialog.dismiss();
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<LevelTopicModel>>() {
                }.getType();
                progressDialog.dismiss();
                levelTopicModels = new Gson().fromJson(result, listType);
                Log.d("Error", levelTopicModels.toString());

                adapter = new LearningAdapter(FreeLearningActivity.this, levelTopicModels);
                int courseCount=levelTopicModels.size();
                int resultcount =examProgressModels.size();
                if(courseCount==resultcount){
                    Certificate.setVisibility(Button.VISIBLE);
                }
                recyclerview.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    private void GetExamProgress() {
        try {
            String cid = getIntent().getExtras().getString("Cid");
            String studentId = SharedPrefManager.getInstance(this).getUser().getMobileNo();
            String levelId="0";
            String plannerDetailID="0";

            result = new UserHelper.PostResultProgress().execute(studentId.toString(),levelId.toString(),cid.toString(),plannerDetailID).get();
            if (result.isEmpty()) {
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<ExamProgressModel>>() {
                }.getType();
                examProgressModels = new Gson().fromJson(result, listType);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}