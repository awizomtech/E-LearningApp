package com.awizomtech.elearning.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.awizomtech.elearning.Activity.CourseListActivity;
import com.awizomtech.elearning.Activity.FreeLearningActivity;
import com.awizomtech.elearning.Activity.FreeSubcriptionActivity;
import com.awizomtech.elearning.Activity.HomePageActivity;
import com.awizomtech.elearning.Activity.MyCourseActivity;
import com.awizomtech.elearning.Activity.MyCourseLevelActivity;
import com.awizomtech.elearning.Activity.QuizResultActivity;
import com.bumptech.glide.Glide;
import com.awizomtech.elearning.AppConfig.AppConfig;
import com.awizomtech.elearning.Activity.CourseLevelActivity;
import com.awizomtech.elearning.Model.CourseListModel;
import com.awizomtech.elearning.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> {
    public ArrayList<String> CourseId = new ArrayList<>();
    private List<CourseListModel> courseListModelList;
    private List<CourseListModel> courseListModelSecond;
    private Context mCtx;

    public CourseAdapter(Context baseContext, List<CourseListModel> courseListModelList) {
        this.courseListModelList = courseListModelList;
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
        final CourseListModel n = courseListModelList.get(position);
        holder.Coursename.setText(n.CourseName.toString());
        holder.Courseid.setText(String.valueOf(n.CourseID));
        courseListModelSecond = CourseListActivity.getActivityInstance().getData();

        ArrayList<String> Choose = new ArrayList<String>();
        for (int j = 0; j < courseListModelSecond.size(); j++) {
            Choose.add(String.valueOf(courseListModelSecond.get(j).getCourseID()));
        }
        String cid = String.valueOf(n.getCourseID());
        String type = String.valueOf(n.getType());

        if (Choose.contains(cid)) {
            if(type.contains("Free")){
                holder.Subscribed.setVisibility(TextView.VISIBLE);
                holder.Subscribed.setText("Subscribed");
            }

            try {
                Glide.with(mCtx).load(AppConfig.BASE_URL + n.CourseImage.toString()).into(holder.imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String ctype = n.getType().toString();
            if (ctype.contains("Free")) {
                holder.Cardview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String cid = String.valueOf(n.getCourseID());
                        Intent intent = new Intent(mCtx, FreeLearningActivity.class);
                        intent.putExtra("Cid",cid);
                        mCtx.startActivity(intent);
                    }
                });
           /* }else if (!n.PaymentStatus == true) {
                holder.PaymnetStatus.setVisibility(CardView.VISIBLE);
                holder.PaymnetStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mCtx, "When Your Payment is Verified Then Read Course",Toast.LENGTH_SHORT).show();
                    }
                });*/
            }else {
                holder.Cardview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String coursename = String.valueOf(n.getCourseName());
                        String cid = String.valueOf(n.getCourseID());
                        Intent intent = new Intent(mCtx, CourseLevelActivity.class);
                        intent.putExtra("CourseName", coursename);
                        intent.putExtra("Cid", cid);
                        mCtx.startActivity(intent);
                    }
                });
            }
        } else {
            try {
                Glide.with(mCtx).load(AppConfig.BASE_URL + n.CourseImage.toString()).into(holder.imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.Cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String ctype = n.getType().toString();
                    if (ctype.contains("Free")) {
                        String cid = String.valueOf(n.getCourseID());
                        String coursename = String.valueOf(n.getCourseName());
                        Intent intent = new Intent(mCtx, FreeSubcriptionActivity.class);
                        intent.putExtra("CourseName", coursename);
                        intent.putExtra("Cid", cid);
                        intent.putExtra("Type", ctype);
                        mCtx.startActivity(intent);
                    } else {
                        String cid = String.valueOf(n.getCourseID());
                        String coursename = String.valueOf(n.getCourseName());
                        String image = String.valueOf(n.getCourseImage());
                        Intent intent = new Intent(mCtx, CourseLevelActivity.class);
                        intent.putExtra("CourseName", coursename);
                        intent.putExtra("image", image);
                        intent.putExtra("Cid", cid);
                        mCtx.startActivity(intent);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {

        return courseListModelList.size();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_list_adapter, parent, false);

        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView bodyNoti, Coursename, Courseid, pdate, Virify,Subscribed;
        ImageView imageView;
        CardView Cardview;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);
            Courseid = view.findViewById(R.id.courseid);
            bodyNoti = view.findViewById(R.id.bodyNotice);
            Subscribed = view.findViewById(R.id.subscribe);
            pdate = view.findViewById(R.id.date);
            Coursename = view.findViewById(R.id.coursename);
            imageView = view.findViewById(R.id.image);
            Cardview = view.findViewById(R.id.cardview);
            Virify = view.findViewById(R.id.coursevirify);
        }


    }

}