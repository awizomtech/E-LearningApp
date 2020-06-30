package com.example.e_learning.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.e_learning.Model.CourseDetailModel;
import com.example.e_learning.R;

import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class CourseDetailAdapter extends RecyclerView.Adapter<CourseDetailAdapter.MyViewHolder> {

    private List<CourseDetailModel> courseDetailModelList;
    private Context mCtx;

    public CourseDetailAdapter(Context baseContext, List<CourseDetailModel> courseDetailModelList) {
        this.courseDetailModelList = courseDetailModelList;
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
        final CourseDetailModel n = courseDetailModelList.get(position);
        holder.Coursename.setText(n.PlannerList.toString());
    }

    @Override
    public int getItemCount() {

        return courseDetailModelList.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_detail_list_adapter, parent, false);

        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Coursename;
        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);
            Coursename = view.findViewById(R.id.coursename);
        }


    }

}