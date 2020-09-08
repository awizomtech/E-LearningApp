package com.awizomtech.elearning.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.awizomtech.elearning.Activity.InstructorDetailActivity;
import com.awizomtech.elearning.AppConfig.AppConfig;
import com.awizomtech.elearning.Model.InstructorModel;
import com.awizomtech.elearning.R;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class InstructorAdapter extends RecyclerView.Adapter<InstructorAdapter.MyViewHolder> {

    private List<InstructorModel> instructorModelList;
    private Context mCtx;
    String result = "";
    private AlertDialog progressDialog;


    public InstructorAdapter(Context baseContext, List<InstructorModel> instructorModelList) {
        this.instructorModelList = instructorModelList;
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
        final InstructorModel n = instructorModelList.get(position);
        holder.Name.setText("Name : "+n.getInsName().toString());
        holder.Mobile.setVisibility(View.GONE);
        holder.Email.setVisibility(View.GONE);
        holder.Mobile.setText("Phone : "+n.getInsPhone().toString());
        holder.Email.setText("Email : "+n.getInsEmail().toString());
        String date = n.getCreatedOn().split("T")[0];
        holder.Date.setText("Joining Date : "+date.toString());
        holder.Degree.setText("Degree : "+n.getInsdegreeOption().toString());
        holder.DegreeText.setText("Degree : "+n.getInsDegreeText().toString());
        if(n.getInsdegreeOption().contains("OTHER")){
            holder.DegreeText.setVisibility(View.VISIBLE);
        }else {
            holder.Degree.setVisibility(View.VISIBLE);
        }

        try {
            Glide.with(mCtx).load(AppConfig.BASE_URL + n.getInsImage().toString()).into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.Cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String InsName=n.getInsName().toString();
                String InsImage = n.getInsImage();
                String InsdegreeOption=n.getInsdegreeOption();
                String InsPhone =n.getInsPhone();
                String InsEmail = n.getInsEmail();
                String InsDegreeText = n.getInsDegreeText();
                String Date = n.getCreatedOn();
                Intent intent = new Intent(mCtx, InstructorDetailActivity.class);
                intent.putExtra("InsName", InsName);
                intent.putExtra("InsImage", InsImage);
                intent.putExtra("InsdegreeOption", InsdegreeOption);
                intent.putExtra("InsPhone", InsPhone);
                intent.putExtra("InsEmail", InsEmail);
                intent.putExtra("InsDegreeText", InsDegreeText);
                intent.putExtra("Date", Date);
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return instructorModelList.size();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.instructor_list_adapter, parent, false);

        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Name,Mobile, Email,Degree,DegreeText,Date;
        ImageView imageView;
        CardView Cardview;
        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);
            Name = view.findViewById(R.id.name);
            imageView=view.findViewById(R.id.image);
            Cardview=view.findViewById(R.id.cardview);
            Degree=view.findViewById(R.id.degree);
            Mobile=view.findViewById(R.id.mobile);
            Email=view.findViewById(R.id.email);
            DegreeText=view.findViewById(R.id.degreetext);
            Date=view.findViewById(R.id.joindate);
        }


    }

}
