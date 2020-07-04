package com.example.e_learning.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.e_learning.Activity.CourseListActivity;
import com.example.e_learning.Activity.MyCourseActivity;
import com.example.e_learning.Activity.MyPaymentActivity;
import com.example.e_learning.Activity.ProfileActivity;
import com.example.e_learning.Adapter.CourseAdapter;
import com.example.e_learning.Adapter.HomeCourseAdapter;
import com.example.e_learning.Adapter.HomeQuizAdapter;
import com.example.e_learning.Helper.UserHelper;
import com.example.e_learning.Model.CourseListModel;
import com.example.e_learning.Model.QuizModel;
import com.example.e_learning.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class HomeFragment extends Fragment {
    HomeCourseAdapter adapter;
    HomeQuizAdapter quizadapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<CourseListModel> courseListModels;
    private List<QuizModel> quizModels;
    private String result = "";

    androidx.recyclerview.widget.RecyclerView recyclerView;
    androidx.recyclerview.widget.RecyclerView recyclerViewQuiz;
    View root;
    CardView Course,Mycourse,Mypayment,Fitness;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
     root = inflater.inflate(R.layout.fragment_home, container, false);
      /*  final TextView textView = root.findViewById(R.id.text_home);*//*
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        Initview();
        return root;
    }

    private void Initview() {
        Mycourse=root.findViewById(R.id.mycourse);
        Mypayment=root.findViewById(R.id.mypayment);
        Fitness=root.findViewById(R.id.fitness);
        Course=root.findViewById(R.id.course);

        Course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), CourseListActivity.class);
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
        Fitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

      /*  Mycourse=root.findViewById(R.id.mycourse);
        Mypayment=root.findViewById(R.id.mypayment);
        Myprofile=root.findViewById(R.id.profile);
        Course=root.findViewById(R.id.course);
        Course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), CourseListActivity.class);
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
        Myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });*/



      /*  recyclerView=root.findViewById(R.id.recyclerView);
        recyclerViewQuiz=root.findViewById(R.id.recyclerViewQuiz);
        recyclerView.setHasFixedSize(true);
      *//*  recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));*//*
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewQuiz.setHasFixedSize(true);
        recyclerViewQuiz.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
*/
       /* mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetCourselist();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });*/

        GetCourselist();
        GetQuizlist();
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

    private void GetQuizlist() {
        try {
            result = new UserHelper.GetQuizlist().execute().get();
            if (result.isEmpty()) {
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<QuizModel>>() {
                }.getType();
                quizModels= new Gson().fromJson(result, listType);
                Log.d("Error", quizModels.toString());
                quizadapter = new HomeQuizAdapter(getContext(), quizModels);
                recyclerViewQuiz.setAdapter(quizadapter);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
