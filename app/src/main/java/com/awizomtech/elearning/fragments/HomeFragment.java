package com.awizomtech.elearning.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.awizomtech.elearning.Activity.CourseListActivity;
import com.awizomtech.elearning.Activity.InstructorListActivity;
import com.awizomtech.elearning.Activity.MyCourseActivity;
import com.awizomtech.elearning.Activity.MyPaymentActivity;
import com.awizomtech.elearning.Adapter.HomeCourseAdapter;
import com.awizomtech.elearning.Adapter.HomeInstructorAdapter;
import com.awizomtech.elearning.Helper.UserHelper;
import com.awizomtech.elearning.Model.CourseListModel;
import com.awizomtech.elearning.Model.InstructorModel;
import com.awizomtech.elearning.R;
import com.awizomtech.elearning.SharePrefrence.SharedPrefManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {
    HomeCourseAdapter adapter;
    HomeInstructorAdapter quizadapter;
    SwipeRefreshLayout mSwipeRefreshLayout,mSwipeRefreshLayout1;
    private List<CourseListModel> courseListModels;
    private List<InstructorModel> instructorModels;
    private String result = "";

    androidx.recyclerview.widget.RecyclerView recyclerView;
    androidx.recyclerview.widget.RecyclerView recyclerView1;
    View root;
    CardView Subscription,Mycourse,Mypayment,AllCourse,AllInstructor;
    TextView Wish, Name;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
     root = inflater.inflate(R.layout.fragment_home, container, false);
        Initview();
        blink();
        return root;
    }

    private void Initview() {
        Mycourse=root.findViewById(R.id.mycourse);
        Mypayment=root.findViewById(R.id.payment);
        Subscription=root.findViewById(R.id.subscription);
        AllCourse=root.findViewById(R.id.allcourse);
        Wish=root.findViewById(R.id.wish);
        Name=root.findViewById(R.id.userName);
        AllInstructor=root.findViewById(R.id.viewAllInstructor);
        recyclerView=root.findViewById(R.id.recyclerView);
        recyclerView1=root.findViewById(R.id.recyclerView1);
        mSwipeRefreshLayout =root.findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout1 =root.findViewById(R.id.swipeToRefresh1);
        recyclerView.setHasFixedSize(true);
        recyclerView1.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        String greeting = null;
        if(hour>= 12 && hour < 17){
            greeting = "Good Afternoon";
        } else if(hour >= 17 && hour < 21){
            greeting = "Good Evening";
        } else if(hour >= 21 && hour < 24){
            greeting = "Good Night";
        } else {
            greeting = "Good Morning";
        }

        Wish.setText(greeting.toString());
        String uname = SharedPrefManager.getInstance(getContext()).getUser().getName();
        StringBuilder sb = new StringBuilder(uname);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        Name.setText(sb.toString());

        AllCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), CourseListActivity.class);
                startActivity(intent);
            }
        });
        Subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), MyCourseActivity.class);
                startActivity(intent);
            }
        });
        Mypayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), MyPaymentActivity.class);
                startActivity(intent);
            }
        });
        Mycourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), MyCourseActivity.class);
                startActivity(intent);
            }
        });
        AllInstructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), InstructorListActivity.class);
                startActivity(intent);
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetCourselist();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mSwipeRefreshLayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetInstructorlist();
                mSwipeRefreshLayout1.setRefreshing(false);
            }
        });
        GetCourselist();
        GetInstructorlist();
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
                adapter = new HomeCourseAdapter(getContext(), courseListModels);
                recyclerView.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
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
                quizadapter = new HomeInstructorAdapter(getContext(), instructorModels);
                recyclerView1.setAdapter(quizadapter);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    private void blink(){
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 1000;    //in milissegunds
                try{Thread.sleep(timeToBlink);}catch (Exception e) {}
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        if(Wish.getVisibility() == View.VISIBLE){
                            Wish.setVisibility(View.INVISIBLE);
                        }else{
                            Wish.setVisibility(View.VISIBLE);
                        }
                        blink();
                    }
                });
            }
        }).start();
    }
}
