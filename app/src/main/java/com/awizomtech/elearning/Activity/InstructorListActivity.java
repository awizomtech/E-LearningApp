package com.awizomtech.elearning.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.awizomtech.elearning.Adapter.HomeCourseAdapter;
import com.awizomtech.elearning.Adapter.HomeInstructorAdapter;
import com.awizomtech.elearning.Adapter.InstructorAdapter;
import com.awizomtech.elearning.Helper.UserHelper;
import com.awizomtech.elearning.Model.CourseListModel;
import com.awizomtech.elearning.Model.InstructorModel;
import com.awizomtech.elearning.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class InstructorListActivity extends AppCompatActivity {

    InstructorAdapter instructorAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<InstructorModel> instructorModels;
    private String result = "";
    androidx.recyclerview.widget.RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_list);
        InitView();
    }

    private void InitView() {
        ImageView backpress = findViewById(R.id.back);
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView=findViewById(R.id.recyclerView);
        mSwipeRefreshLayout =findViewById(R.id.swipeToRefresh);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetInstructorlist();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        GetInstructorlist();
    }

    private void GetInstructorlist() {
        try {
            result = new UserHelper.GetInstructorlist().execute().get();
            if (result.isEmpty()) {
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<InstructorModel>>() {
                }.getType();
                instructorModels= new Gson().fromJson(result, listType);
                Log.d("Error", instructorModels.toString());
                instructorAdapter = new InstructorAdapter(this, instructorModels);
                recyclerView.setAdapter(instructorAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}