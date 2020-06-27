package com.example.e_learning.Activity;

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

    androidx.recyclerview.widget.RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        InitView();
    }



    private void InitView() {


      /*  androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar)
                findViewById(R.id.toolbar);
        toolbar.setTitle("Course List");
        toolbar.setTitleTextColor(Color.parseColor("#ffff"));
        toolbar.setBackgroundColor(Color.parseColor("#409F60"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(AddOrderActivity.this, HomePage.class);
//                startActivity(intent);
                onBackPressed();
            }
        });*/
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
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<CourseListModel>>() {
                }.getType();
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
