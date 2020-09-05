package com.awizomtech.elearning.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.awizomtech.elearning.Activity.CourseLevelActivity;
import com.awizomtech.elearning.Activity.CourseListActivity;
import com.awizomtech.elearning.Activity.LearningActivity;
import com.awizomtech.elearning.Activity.LevelOneDescriptionActivity;
import com.awizomtech.elearning.Activity.LevelSecDescriptionActivity;
import com.awizomtech.elearning.Activity.LevelThirdDescriptionActivity;
import com.awizomtech.elearning.Activity.MyCourseLevelActivity;
import com.awizomtech.elearning.Activity.SubscriptionActivity;
import com.awizomtech.elearning.Helper.AccountHelper;
import com.awizomtech.elearning.Model.CourseLevelModel;
import com.awizomtech.elearning.Model.MyCourseLevelModel;
import com.awizomtech.elearning.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CourseLevelAdapter extends RecyclerView.Adapter<CourseLevelAdapter.MyViewHolder> {

    private List<CourseLevelModel> courseLevelModelList;
    private List<MyCourseLevelModel> myCourseLevelModels;
    private Context mCtx;
    String result;
float USDValue;
    public CourseLevelAdapter(Context baseContext, List<CourseLevelModel> courseLevelModelList) {
        this.courseLevelModelList = courseLevelModelList;
        this.mCtx = baseContext;
        ViewAll();
    }

    private void ViewAll() {
        try {
            result = new AccountHelper.RetrieveUSDValueTask().execute().get();
            if (result.isEmpty()) {
            } else {
                JSONObject obj = new JSONObject(result);
                String raa = String.valueOf(obj.get("rates"));
                JSONObject obj1 = new JSONObject(raa);
                String usd = String.valueOf(obj1.get("INR"));
              /*  String ss=usd.split(".")[0];
                String s=usd.split(".")[1];*/
                USDValue = Float.parseFloat(usd);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        final CourseLevelModel n = courseLevelModelList.get(position);
        holder.Coursename.setText(n.Level.toString());
        myCourseLevelModels = CourseLevelActivity.getActivityInstance().getDatas();

        ArrayList<String> Choose = new ArrayList<String>();
        for (int j = 0; j < myCourseLevelModels.size(); j++) {
            Choose.add(String.valueOf(myCourseLevelModels.get(j).getLevelID()));
        }
        String levelID = String.valueOf(n.getLevelID());
        if (Choose.contains(levelID)) {
            holder.Sub.setVisibility(TextView.VISIBLE);
            holder.Sub.setText("Enrolled");
            holder.Duration.setText("Duration " + n.getDuration().toString());
            holder.Price.setText("Price " +"₹" +String.valueOf(n.getPrice()));
          /*  holder.Price.setText("Price " +"₹" +String.valueOf(n.getPrice())+"/"+String.valueOf(n.getPrice()/USDValue)+"$");*/
            holder.payment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String cid = String.valueOf(n.getCourseID());
                    String lid = String.valueOf(n.getLevelID());
                    Intent intent = new Intent(mCtx, LearningActivity.class);
                    intent.putExtra("levelID", lid);
                    intent.putExtra("Cid", cid);
                    mCtx.startActivity(intent);
                }
            });
        } else {
            holder.payment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String cid = String.valueOf(n.getCourseID());
                    String lid = String.valueOf(n.getLevelID());
                    String level = String.valueOf(n.getLevel());
                    String duration = String.valueOf(n.getDuration());
                    String price = String.valueOf(n.getPrice());
                    /*    String cname =getIntent().getExtras().getString("CourseName");*/

                    if (n.getLevel().contains("Level 1")) {
                        Intent intent = new Intent(mCtx, LevelSecDescriptionActivity.class);
                        intent.putExtra("levelID", lid);
                        intent.putExtra("level", level);
                        intent.putExtra("Cid", cid);
                        intent.putExtra("Duration", duration);
                        intent.putExtra("Price", price);
                        mCtx.startActivity(intent);
                    } else if (n.getLevel().contains("Level 2")) {
                        Intent intent = new Intent(mCtx, LevelOneDescriptionActivity.class);
                        intent.putExtra("levelID", lid);
                        intent.putExtra("level", level);
                        intent.putExtra("Cid", cid);
                        intent.putExtra("Duration", duration);
                        intent.putExtra("Price", price);
                        mCtx.startActivity(intent);
                    } else {
                        Intent intent = new Intent(mCtx, LevelThirdDescriptionActivity.class);
                        intent.putExtra("levelID", lid);
                        intent.putExtra("level", level);
                        intent.putExtra("Cid", cid);
                        intent.putExtra("Duration", duration);
                        intent.putExtra("Price", price);
                        mCtx.startActivity(intent);
                    }


                }
            });
            holder.Duration.setText("Duration " + n.getDuration().toString());
            holder.Price.setText("Price " +"₹" +String.valueOf(n.getPrice()));
        /*    holder.Price.setText("Price " +"₹" +String.valueOf(n.getPrice())+"/"+String.valueOf(n.getPrice()/USDValue)+"$");*/
        }

    }

    @Override
    public int getItemCount() {

        return courseLevelModelList.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_level_list_adapter, parent, false);

        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Coursename, Duration, Price, Sub;
        CardView payment;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);
            Coursename = view.findViewById(R.id.coursename);
            payment = view.findViewById(R.id.cardview);
            Duration = view.findViewById(R.id.courseDuration);
            Price = view.findViewById(R.id.coursePrice);
            Sub = view.findViewById(R.id.subscribed);
        }


    }

}