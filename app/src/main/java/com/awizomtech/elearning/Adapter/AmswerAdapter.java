package com.awizomtech.elearning.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.awizomtech.elearning.Model.AnswerModel;
import com.awizomtech.elearning.R;

import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class AmswerAdapter extends RecyclerView.Adapter<AmswerAdapter.MyViewHolder> {

    private Context mCtx;
    String result = "";

    private List<AnswerModel> answerModelList;

    public AmswerAdapter(Context baseContext, List<AnswerModel> answerModelList) {
        this.answerModelList = answerModelList;
        this.mCtx = baseContext;

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
        final AnswerModel n = answerModelList.get(position);
        holder.Coursename.setText(n.AnswerText.toString());

        int buttons = 3;
        /*RadioButton rbn = new RadioButton(mCtx);
        rbn.setId(View.generateViewId());
        rbn.setText(String.valueOf(n.getAnswerText()));
        holder.Check.addView(rbn);*/
        for (int i = 1; i <= buttons ; i++) {
            RadioButton rbn = new RadioButton(mCtx);
            rbn.setId(View.generateViewId());
            rbn.setText("RadioButton" + i);
            holder.Check.addView(rbn);
        }
        holder.Check.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup rg, int checkedId) {
               /* for(int i=0; i<rg.getChildCount(); i++) {
                    RadioButton btn = (RadioButton) rg.getChildAt(i);
                    if(btn.getId() == checkedId) {
                        String text = (String) btn.getText();
                        // do something with text
                        Toast.makeText(mCtx,text,Toast.LENGTH_SHORT).show();
                        return;
                    }
                }*/
                for(int i=0;i<rg.getChildCount();i++)  //From the parent layout (Linear Layout) get the child
                {
                    View child = rg.getChildAt(i);

                    if(child instanceof RadioGroup)     //Check weather its RadioGroup using its INSTANCE
                    {
                        RadioGroup gg = (RadioGroup) child; //create a RadioButton for each group
                        int selectedId = gg.getCheckedRadioButtonId(); // get the selected button



                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return answerModelList.size();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.answer_list_adapter, parent, false);

        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Coursename, Courseid;
RadioGroup Check;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);
            Courseid = view.findViewById(R.id.courseid);
            Coursename = view.findViewById(R.id.optionA);
            Check=view.findViewById(R.id.radiogroup);

        }

    }
}