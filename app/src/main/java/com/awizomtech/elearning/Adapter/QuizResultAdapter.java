package com.awizomtech.elearning.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.awizomtech.elearning.Model.ProfileModel;
import com.awizomtech.elearning.Model.QuestionModel;
import com.awizomtech.elearning.Model.QuizResultModel;
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


public class QuizResultAdapter extends RecyclerView.Adapter<QuizResultAdapter.MyViewHolder> {

    private List<QuestionModel> questionModelList;
    private Context mCtx;
    /*  QuizResultModel quizResultModels;*/
    private List<QuizResultModel> quizResultModels;
    CircularProgressButton submit;
    private ArrayList<String> Chooseid = new ArrayList<String>();
    private List<AnswerModel> answerModels;
    int Qid = 0;
    private ArrayList<String> Quizid = new ArrayList<String>();
    ArrayList<String> Resultid = new ArrayList<String>();
    ArrayList<String> Resultd = new ArrayList<String>();
    ArrayList<String> Correct = new ArrayList<String>();
   /* ArrayList<String> AnsCorrect = new ArrayList<String>();*/
    String CourseID = "";
    String Data;

    public QuizResultAdapter(Context baseContext, List<QuestionModel> questionModelList, CircularProgressButton submit) {
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
        quizResultModels = QuizResultActivity.getActivityInstance().getData();
        Data = QuizResultActivity.getActivityInstance().getDatas();
        try {
            final String quizid = String.valueOf(n.QuizID);
            String result = new UserHelper.GetAnswerList().execute(quizid.toString()).get();
            if (result.isEmpty()) {
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<AnswerModel>>() {
                }.getType();
                answerModels = new Gson().fromJson(result, listType);
                Log.d("Error", answerModels.toString());

                ArrayList<String> Choose = new ArrayList<String>();
                for (int j = 0; j < quizResultModels.size(); j++) {
                    Choose.add(String.valueOf(quizResultModels.get(j).getAnswerID()));
                    Resultd.add(String.valueOf(quizResultModels.get(j).getResultID()));
                }

                ArrayList<String> Chooselist = new ArrayList<String>();

                for (int i = 0; i < answerModels.size(); i++) {
                    Qid = answerModels.get(i).getAnswerID();
                    String val = String.valueOf(answerModels.get(i).getAnswerID());

                    if (Choose.contains(val)) {
                        String name = String.valueOf(answerModels.get(i).getAnswerID()) + "10" + answerModels.get(i).getAnswerText() + "15" + "true" + "20" + answerModels.get(i).IsCorrect;
                        Chooselist.add(name);
                    } else {
                        String name = String.valueOf(answerModels.get(i).getAnswerID()) + "10" + answerModels.get(i).getAnswerText() + "15" + "false" + "20" + answerModels.get(i).IsCorrect;
                        Chooselist.add(name);
                    }
                }

                for (int k = 0; k < Chooselist.size(); k++) {
                    String name = Chooselist.get(k);

                    String id = name.split("10")[0];
                    String text = name.split("15")[0];
                    String anstext = text.split("10")[1];
                    String texts = name.split("15")[1];
                    String correct = texts.split("20")[1];
                    String select = texts.split("20")[0];

                    RadioButton rbn = new RadioButton(mCtx);
                    if (select.contains("true")) {
                        if (correct.contains(select)) {
                            rbn.setTextColor(Color.parseColor("#1ea383"));
                            rbn.setId(Integer.parseInt(id));
                            rbn.setText(anstext);
                            Correct.add(id);
                        } else if (select.contains("true")) {
                            rbn.setTextColor(Color.parseColor("#FC0707"));
                            rbn.setId(Integer.parseInt(id));
                            rbn.setText(anstext);
                        }
                    } else {
                        rbn.setId(Integer.parseInt(id));
                        rbn.setText(anstext);
                    }

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
        int correct = Correct.size();
        int anscorrect = Quizid.size();
        if (correct == anscorrect) {
            submit.setText("Completed");
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* try {
                        for (int j = 0; j < Quizid.size(); j++) {
                            Resultid.add(String.valueOf(Resultd.get(j)));

                        }
                        String userid = SharedPrefManager.getInstance(mCtx).getUser().getUserID();
                        String Length = String.valueOf(Chooseid.size());
                        String result = new UserHelper.PostResultupdate().execute(Chooseid.toString(), Length.toString(), Quizid.toString(), CourseID.toString(), userid.toString(), Resultid.toString()).get();
                        if (result.isEmpty()) {

                        } else {
                            String planer = Data.split("T")[0];
                            String CourseName = Data.split("T")[1];
                            String CourseId = Data.split("C")[0];
                            String planerId = planer.split("C")[1];
                            Intent intent = new Intent(mCtx, QuizResultActivity.class);
                            intent.putExtra("CourseID", CourseId);
                            intent.putExtra("planerId", planerId);
                            intent.putExtra("CourseName", CourseName);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            mCtx.startActivity(intent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                    }*/
                }
            });
        } else {
            submit.setText("Re-Submit");
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        for (int j = 0; j < Quizid.size(); j++) {
                            Resultid.add(String.valueOf(Resultd.get(j)));

                        }
                        String userid = SharedPrefManager.getInstance(mCtx).getUser().getMobileNo();
                        String Length = String.valueOf(Chooseid.size());
                        String result = new UserHelper.PostResultupdate().execute(Chooseid.toString(), Length.toString(), Quizid.toString(), CourseID.toString(), userid.toString(), Resultid.toString()).get();
                        if (result.isEmpty()) {

                        } else {
                            String planer = Data.split("T")[0];
                            String CourseName = Data.split("T")[1];
                            String CourseId = Data.split("C")[0];
                            String planerId = planer.split("C")[1];
                            Intent intent = new Intent(mCtx, QuizResultActivity.class);
                            intent.putExtra("CourseID", CourseId);
                            intent.putExtra("planerId", planerId);
                            intent.putExtra("CourseName", CourseName);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            mCtx.startActivity(intent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            });
        }

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