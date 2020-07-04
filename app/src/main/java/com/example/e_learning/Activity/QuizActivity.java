package com.example.e_learning.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.e_learning.Adapter.QuestionListAdapter;
import com.example.e_learning.Helper.UserHelper;
import com.example.e_learning.Model.QuestionModel;
import com.example.e_learning.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    String CourseID = "0", Coursename = "";
    TextView quiztext, titletext;
    List<QuestionModel> questionModels;
    QuestionListAdapter adapter;
    br.com.simplepass.loading_button_lib.customViews.CircularProgressButton submit;
    CardView quzdetl;
    FrameLayout frame;
    ImageView comng_soon;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
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

        submit = findViewById(R.id.cirSubmitButton);

        quzdetl = findViewById(R.id.quzdetl);
        comng_soon = findViewById(R.id.comng_soon);
          CourseID = getIntent().getStringExtra("CourseID");
          Coursename = getIntent().getStringExtra("Coursename");
         CourseID="3";
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        frame = findViewById(R.id.frame);
        quiztext = findViewById(R.id.quiz);
        titletext = findViewById(R.id.title);
        quiztext.setText(Coursename.toString());
        GetQuestions();
    }

    private void GetQuestions() {

        try {

            result = new UserHelper.GetQuestionList().execute(CourseID.toString()).get();
            if (result.isEmpty()) {
                result = new UserHelper.GetQuestionList().execute(CourseID.toString()).get();
            } else {
                Type listType = new TypeToken<List<QuestionModel>>() {
                }.getType();

                questionModels = new Gson().fromJson(result, listType);
                Log.d("Error", questionModels.toString());
               adapter = new QuestionListAdapter(QuizActivity.this, questionModels, submit);
            /*    recyclerView.setAdapter(adapter);*/
                adapter.setHasStableIds(true);
                recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 5);
                recyclerView.setItemViewCacheSize(100);
                adapter.notifyItemRangeChanged(0, adapter.getItemCount());
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}