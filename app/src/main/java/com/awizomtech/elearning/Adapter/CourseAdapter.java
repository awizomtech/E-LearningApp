package com.awizomtech.elearning.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.awizomtech.elearning.Activity.FreeSubcriptionActivity;
import com.awizomtech.elearning.Activity.HomePageActivity;
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
      /*  String date=n.getStartsFrom().split("T")[0];
        String Pdate=n.getCreatedOn().split("T")[0];*/
       /* holder.bodyNoti.setText("Start From : " +date);
        holder.pdate.setText("Date" +Pdate);*/

        /*CourseId = HomePageActivity.getActivityInstance().getData();
        for (int i = 0; i <= CourseId.size(); i++) {
            int Cid = Integer.valueOf(CourseId.get(i).toString());
            if (Cid == n.getCourseID()) {
                holder.Virify.setVisibility(TextView.VISIBLE);
            }
        }*/
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
                    mCtx.startActivity(intent);
                } else {
                    String cid = String.valueOf(n.getCourseID());
                    String coursename = String.valueOf(n.getCourseName());
                    Intent intent = new Intent(mCtx, CourseLevelActivity.class);
                    intent.putExtra("CourseName", coursename);
                    intent.putExtra("Cid", cid);
                    mCtx.startActivity(intent);
                }
            }
        });

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

        TextView bodyNoti, Coursename, Courseid, pdate, Virify;
        ImageView imageView;
        CardView Cardview;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);
            Courseid = view.findViewById(R.id.courseid);
            bodyNoti = view.findViewById(R.id.bodyNotice);
            pdate = view.findViewById(R.id.date);
            Coursename = view.findViewById(R.id.coursename);
            imageView = view.findViewById(R.id.image);
            Cardview = view.findViewById(R.id.cardview);
            Virify = view.findViewById(R.id.coursevirify);
        }


    }

}