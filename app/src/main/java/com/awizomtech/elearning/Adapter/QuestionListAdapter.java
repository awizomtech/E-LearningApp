package com.awizomtech.elearning.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.awizomtech.elearning.Activity.QuizActivity;
import com.awizomtech.elearning.Activity.QuizResultActivity;
import com.awizomtech.elearning.Helper.UserHelper;
import com.awizomtech.elearning.Model.AnswerModel;
import com.awizomtech.elearning.Model.QuestionModel;
import com.awizomtech.elearning.R;
import com.awizomtech.elearning.SharePrefrence.SharedPrefManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;

import androidx.recyclerview.widget.RecyclerView;
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.MyViewHolder> {

    private List<QuestionModel> questionModelList;
    private Context mCtx;
    private List<AnswerModel> answerModels;
    CircularProgressButton submit;
    private ArrayList<String> Chooseid = new ArrayList<String>();
    private ArrayList<String> Quizid = new ArrayList<String>();
String CourseID ="";
String Data;
    public QuestionListAdapter(Context baseContext, List<QuestionModel> questionModelList, CircularProgressButton submit) {
        this.questionModelList = questionModelList;
        this.mCtx = baseContext;
        this.submit = submit;

    }

    /* for solve issue of item change on scroll this method is set*/
    @Override
    public long getItemId(int position) {
        return position;
    }

    /* for solve issue of item change on scroll this method is set*/
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final QuestionModel n = questionModelList.get(position);
        holder.Coursename.setText(n.getQuestion().toString());
        Quizid.add(String.valueOf(n.getQuizID()));
        CourseID = String.valueOf(n.getCourseID());
        Data= QuizActivity.getActivityInstance().getData();

        try {
            String quizid = String.valueOf(n.QuizID);
            String result = new UserHelper.GetAnswerList().execute(quizid.toString()).get();
            if (result.isEmpty()) {
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<AnswerModel>>() {
                }.getType();
                answerModels = new Gson().fromJson(result, listType);
                Log.d("Error", answerModels.toString());

                for (int i = 0; i < answerModels.size(); i++) {
                    RadioButton rbn = new RadioButton(mCtx);
                    rbn.setId(answerModels.get(i).getAnswerID());
                    rbn.setText(answerModels.get(i).getAnswerText());
                    holder.Check.addView(rbn);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.Check.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup rg, int checkedId) {
                for (int i = 0; i < rg.getChildCount(); i++) {
                 /*   RadioButton btn = (RadioButton) rg.getChildAt(i);*/
                    RadioButton btn = (RadioButton) rg.getChildAt(i);
                    String text = String.valueOf(btn.getId());
                    if (btn.getId() == checkedId) {
                        String vals = String.valueOf(checkedId);
                        Chooseid.add(vals);

                        Toast.makeText(mCtx, vals, Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        String vals = String.valueOf(checkedId);
                        Chooseid.remove(vals);
                        Chooseid.add(vals);
                        Toast.makeText(mCtx, vals, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String userid = SharedPrefManager.getInstance(mCtx).getUser().getUserID();
                    String Length = String.valueOf(Chooseid.size());
                    String result = new UserHelper.PostResult().execute(Chooseid.toString(), Length.toString(),Quizid.toString(),CourseID.toString(), userid.toString()).get();
                    if (result.isEmpty()) {

                    } else {
                        String planer=Data.split("T")[0];
                        String CourseName=Data.split("T")[1];
                        String CourseId=Data.split("C")[0];
                        String planerId=planer.split("C")[1];
                        Intent intent = new Intent(mCtx, QuizResultActivity.class);
                        intent.putExtra("CourseID",CourseId);
                        intent.putExtra("planerId",planerId);
                        intent.putExtra("CourseName",CourseName);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mCtx.startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return questionModelList.size();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_list_adapter, parent, false);
        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Coursename, choooseid;

        RadioGroup Check;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);
            choooseid = view.findViewById(R.id.courseid);
            Coursename = view.findViewById(R.id.coursename);
            Check = view.findViewById(R.id.radiogroup);
        }

    }
}