package com.awizomtech.elearning.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
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
import com.awizomtech.elearning.Model.CourseLevelModel;
import com.awizomtech.elearning.Model.MyCourseLevelModel;
import com.awizomtech.elearning.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CourseLevelAdapter extends RecyclerView.Adapter<CourseLevelAdapter.MyViewHolder> {

    private List<CourseLevelModel> courseLevelModelList;
    private List<MyCourseLevelModel> myCourseLevelModels;
    private Context mCtx;
    String result;
    String USDValue;

    public CourseLevelAdapter(Context baseContext, List<CourseLevelModel> courseLevelModelList) {
        this.courseLevelModelList = courseLevelModelList;
        this.mCtx = baseContext;

    }

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
        myCourseLevelModels = CourseLevelActivity.getActivityInstance().getDatas();
        USDValue = CourseListActivity.getActivityInstance().getUSD();

        holder.Coursename.setText(n.Level.toString());
        holder.Discount.setText(n.getPercentage() + "% OFF");
        holder.Duration.setText("Duration " + n.getDuration().toString());
        float usd = Float.parseFloat(USDValue);
        float inr = n.getPrice();
        float sum = inr / usd;
        String price = String.valueOf(n.getPrice());
        String usdprice = String.valueOf(sum);
        holder.Price.setText("Price  " + " ₹" + price + "/" + usdprice + "$");
        ArrayList<String> Choose = new ArrayList<String>();
        for (int j = 0; j < myCourseLevelModels.size(); j++) {
            Choose.add(String.valueOf(myCourseLevelModels.get(j).getLevelID()));
        }
        String levelID = String.valueOf(n.getLevelID());
        if (Choose.contains(levelID)) {
            holder.Sub.setVisibility(TextView.VISIBLE);
            holder.Sub.setText("Enrolled");

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
                    String discount = String.valueOf(n.getPercentage());
                    String payable = String.valueOf(n.getPayableAmount());


                    if (n.getLevel().contains("Level 1")) {
                        Intent intent = new Intent(mCtx, LevelSecDescriptionActivity.class);
                        intent.putExtra("levelID", lid);
                        intent.putExtra("level", level);
                        intent.putExtra("Cid", cid);
                        intent.putExtra("Duration", duration);
                        intent.putExtra("Price", price);
                        intent.putExtra("payable", payable);
                        intent.putExtra("discount", discount);
                        intent.putExtra("USDValue", USDValue);
                        mCtx.startActivity(intent);
                    } else if (n.getLevel().contains("Level 2")) {
                        Intent intent = new Intent(mCtx, LevelOneDescriptionActivity.class);
                        intent.putExtra("levelID", lid);
                        intent.putExtra("level", level);
                        intent.putExtra("Cid", cid);
                        intent.putExtra("Duration", duration);
                        intent.putExtra("Price", price);
                        intent.putExtra("payable", payable);
                        intent.putExtra("discount", discount);
                        intent.putExtra("USDValue", USDValue);
                        mCtx.startActivity(intent);
                    } else {
                        Intent intent = new Intent(mCtx, LevelThirdDescriptionActivity.class);
                        intent.putExtra("levelID", lid);
                        intent.putExtra("level", level);
                        intent.putExtra("Cid", cid);
                        intent.putExtra("Duration", duration);
                        intent.putExtra("Price", price);
                        intent.putExtra("payable", payable);
                        intent.putExtra("discount", discount);
                        intent.putExtra("USDValue", USDValue);
                        mCtx.startActivity(intent);
                    }


                }
            });
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

        TextView Coursename, Duration, Price, Sub, Discount;
        CardView payment;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);
            Coursename = view.findViewById(R.id.coursename);
            payment = view.findViewById(R.id.cardview);
            Duration = view.findViewById(R.id.courseDuration);
            Price = view.findViewById(R.id.coursePrice);
            Sub = view.findViewById(R.id.subscribed);
            Discount = view.findViewById(R.id.courseDiscount);
        }
    }
}