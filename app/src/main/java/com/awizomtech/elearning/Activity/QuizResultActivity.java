package com.awizomtech.elearning.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.awizomtech.elearning.Adapter.QuizResultAdapter;
import com.awizomtech.elearning.Helper.UserHelper;
import com.awizomtech.elearning.Model.QuestionModel;
import com.awizomtech.elearning.Model.QuizResultModel;
import com.awizomtech.elearning.R;
import com.awizomtech.elearning.SharePrefrence.SharedPrefManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class QuizResultActivity extends AppCompatActivity {
    static QuizResultActivity INSTANCE;

    RecyclerView recyclerView;
    String CourseID = "", Coursename = "",planerId="";
    TextView quiztext, titletext;
    List<QuestionModel> questionModels;
    QuizResultAdapter adapter;
    br.com.simplepass.loading_button_lib.customViews.CircularProgressButton submit;
    CardView quzdetl;
    FrameLayout frame;
    ImageView comng_soon;
    String result;
    String data;
    private List<QuizResultModel> quizResultModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        INSTANCE=this;
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_quiz_result);
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
        planerId = getIntent().getStringExtra("planerId");
        Coursename = getIntent().getStringExtra("CourseName");
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        frame = findViewById(R.id.frame);
        quiztext = findViewById(R.id.quiz);
        titletext = findViewById(R.id.title);
        quiztext.setText(Coursename.toString());
        data=CourseID+"C"+planerId+"T"+Coursename;
        ViewAns();
        GetQuestions();
    }

    private void GetQuestions() {

        try {

            result = new UserHelper.GetQuestionList().execute(planerId.toString()).get();
            if (result.isEmpty()) {
                result = new UserHelper.GetQuestionList().execute(planerId.toString()).get();
            } else {
                Type listType = new TypeToken<List<QuestionModel>>() {
                }.getType();

                questionModels = new Gson().fromJson(result, listType);
                Log.d("Error", questionModels.toString());
                adapter = new QuizResultAdapter(QuizResultActivity.this, questionModels, submit);
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
    private void ViewAns() {
        try {
             int n=100;
            String quizid = String.valueOf(n);
            String userid= SharedPrefManager.getInstance(this).getUser().getMobileNo();
            String result = new UserHelper.GetResult().execute(quizid.toString(),userid.toString()).get();
            if (result.isEmpty()) {
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<QuizResultModel>>() {
                }.getType();
                quizResultModels = new Gson().fromJson(result, listType);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static QuizResultActivity getActivityInstance()
    {
        return INSTANCE;
    }

    public List<QuizResultModel> getData()
    {
        return this.quizResultModels;
    }
    public String getDatas()
    {
        return this.data;
    }
}