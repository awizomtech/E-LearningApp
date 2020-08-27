package com.awizomtech.elearning.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.awizomtech.elearning.Adapter.CourseAdapter;
import com.awizomtech.elearning.Adapter.MockAdapter;
import com.awizomtech.elearning.Model.CourseListModel;
import com.awizomtech.elearning.Model.MockTestModel;
import com.awizomtech.elearning.R;

import java.util.List;

public class MockTestActivity extends AppCompatActivity {
    MockAdapter adapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<MockTestModel> courseListModels;
    private String result = "";
    ProgressDialog progressDialog;
    androidx.recyclerview.widget.RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_test);
    }
}