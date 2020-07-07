package com.awizomtech.elearning.Adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.awizomtech.elearning.Helper.UserHelper;
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
    private List<QuizResultModel> quizResultModels;
    CircularProgressButton submit;
    private ArrayList<String> Chooseid = new ArrayList<String>();

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


      /*  answerModels = new Gson().fromJson(result, listType);
        Log.d("Error", answerModels.toString());


        for (int i = 0; i < answerModels.size()&&i<selectedid.size(); i++) {
            int  na = Integer.parseInt(selectedid.get(i));
            int ii=  answerModels.get(i).getAnswerID();
            if(ii==na){
                RadioButton rbn = new RadioButton(mCtx);
                rbn.setBackgroundColor(Color.parseColor("#2F7732"));
                rbn.setId(answerModels.get(i).getAnswerID());
                rbn.setText(answerModels.get(i).getAnswerText());
                holder.Check.addView(rbn);
            }else {
                RadioButton rb = new RadioButton(mCtx);
                rb.setBackgroundColor(Color.parseColor("#E13C19"));
                rb.setId(answerModels.get(i).getAnswerID());
                rb.setText(answerModels.get(i).getAnswerText());
                holder.Check.addView(rb);
            }
        }*/
        try {
            String quizid = String.valueOf(n.QuizID);
            String userid=SharedPrefManager.getInstance(mCtx).getUser().getUserID();
            String result = new UserHelper.GetResult().execute(quizid.toString(),userid.toString()).get();
            if (result.isEmpty()) {
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<QuizResultModel>>() {
                }.getType();
                quizResultModels = new Gson().fromJson(result, listType);
                Log.d("Error", quizResultModels.toString());

                for (int i = 0; i < quizResultModels.size(); i++) {
                    RadioButton rbn = new RadioButton(mCtx);
                    rbn.setId(quizResultModels.get(i).getAnswerID());
                    rbn.setText(quizResultModels.get(i).getAnswerText());
                    holder.Check.addView(rbn);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
      /*  holder.Check.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup rg, int checkedId) {
                for (int i = 0; i < rg.getChildCount(); i++) {
                    RadioButton btn = (RadioButton) rg.getChildAt(i);
                    String text = String.valueOf(btn.getId());
                    if (btn.getId() == checkedId) {
                        Chooseid.add(text);
                        Toast.makeText(mCtx, text, Toast.LENGTH_SHORT).show();
                        return;
                    } else {

                        Chooseid.remove(text);
                        Chooseid.add(text);
                        Toast.makeText(mCtx, text, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });*/

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*try {
                    String userid= SharedPrefManager.getInstance(mCtx).getUser().getUserID();
                    String Length = String.valueOf(Chooseid.size());
                    String  result = new UserHelper.PostResult().execute(Chooseid.toString(),Length.toString(), userid.toString()).get();
                    if (result.isEmpty()) {

                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }*/
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