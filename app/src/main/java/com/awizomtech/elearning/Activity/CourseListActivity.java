package com.awizomtech.elearning.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.awizomtech.elearning.Adapter.CourseAdapter;
import com.awizomtech.elearning.Adapter.MyCourseAdapter;
import com.awizomtech.elearning.Helper.AccountHelper;
import com.awizomtech.elearning.Helper.UserHelper;
import com.awizomtech.elearning.Model.CourseListModel;
import com.awizomtech.elearning.Model.QuizResultModel;
import com.awizomtech.elearning.R;
import com.awizomtech.elearning.SharePrefrence.SharedPrefManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class CourseListActivity extends AppCompatActivity {
    static CourseListActivity INSTANCE;
    CourseAdapter adapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<CourseListModel> courseListModels;
    private List<CourseListModel> courseListModelSecond;
    private String result = "";
    ProgressDialog progressDialog;
    androidx.recyclerview.widget.RecyclerView recyclerView;
    String USDValue=" ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        INSTANCE=this;
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_course_list);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ViewAll();
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

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetMyCourselist();
               GetCourselist();

                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        GetMyCourselist();
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

    private void GetMyCourselist() {
        try {
            String userid= SharedPrefManager.getInstance(this).getUser().getUserID();
            result = new UserHelper.GetMyCourseList().execute(userid.toString()).get();
            if (result.isEmpty()) {
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<CourseListModel>>() {
                }.getType();
                courseListModelSecond= new Gson().fromJson(result, listType);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    private void ViewAll() {
        try {
            result = new AccountHelper.RetrieveUSDValueTask().execute().get();
            if (result.isEmpty()) {
            } else {
                JSONObject obj = new JSONObject(result);
                String raa = String.valueOf(obj.get("rates"));
                JSONObject obj1 = new JSONObject(raa);
                String usd = String.valueOf(obj1.get("INR"));
                USDValue = usd;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static CourseListActivity getActivityInstance()
    {
        return INSTANCE;
    }

    public List<CourseListModel> getData()
    {
        return this.courseListModelSecond;
    }
    public  String getUSD(){
        return this.USDValue;
    }
}
