package com.example.e_learning.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.e_learning.AppConfig.AppConfig;
import com.example.e_learning.Activity.LearningActivity;
import com.example.e_learning.Model.CourseListModel;
import com.example.e_learning.R;

import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MyCourseAdapter extends RecyclerView.Adapter<MyCourseAdapter.MyViewHolder> {

    private List<CourseListModel> courseListModelList;
    private Context mCtx;
    String result = "";
    private AlertDialog progressDialog;

    public MyCourseAdapter(Context baseContext, List<CourseListModel> courseListModelList) {
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
        String date=n.getStartsFrom().split("T")[0];
        String Pdate=n.getCreatedOn().split("T")[0];
        holder.bodyNoti.setText("Price " +n.Price+"₹");
        holder.pdate.setText("Duration " +n.Duration);
        try {
            Glide.with(mCtx).load(AppConfig.BASE_URL + n.CourseImage.toString()).into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.Cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cid = String.valueOf(n.getCourseID());
                Intent intent = new Intent(mCtx, LearningActivity.class);
                intent.putExtra("Cid",cid);
                mCtx.startActivity(intent);
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
                .inflate(R.layout.my_course_list_adapter, parent, false);

        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView bodyNoti, Coursename, Courseid,pdate;
        ImageView imageView;
        CardView Cardview;
        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);
            Courseid = view.findViewById(R.id.courseid);
            bodyNoti = view.findViewById(R.id.bodyNotice);
            pdate = view.findViewById(R.id.date);
            Coursename = view.findViewById(R.id.coursename);
            imageView=view.findViewById(R.id.image);
            Cardview=view.findViewById(R.id.cardview);
        }


    }

}