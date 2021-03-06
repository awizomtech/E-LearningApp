package com.awizomtech.elearning.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.awizomtech.elearning.Model.LectureModel;
import com.awizomtech.elearning.R;
import com.awizomtech.elearning.Activity.ReadActivity;

import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import static com.awizomtech.elearning.AppConfig.AppConfig.BASE_URL;

public class LectureAdapter extends RecyclerView.Adapter<LectureAdapter.MyViewHolder> {

    private List<LectureModel> lectureModelList;
    private Context mCtx;
    String result = "";


    public LectureAdapter(Context baseContext, List<LectureModel> lectureModelList) {
        this.lectureModelList = lectureModelList;
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
        final LectureModel n = lectureModelList.get(position);
        String type=n.Type.toString();
        try {
           if(type.equals("Video"))
           {
               holder.Titele.setText(n.Title.toString());
               holder.Filesection.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       String type=n.Type.toString();
                       String URL=n.Video.toString();
                       String CourseName=n.getTitle();
                       String CourseID = String.valueOf(n.getCourseID());
                       String planerId = String.valueOf(n.getPlanID());
                       Intent intent = new Intent(mCtx, ReadActivity.class);
                       intent.putExtra("Type",type.toString());
                       intent.putExtra("url",URL);
                       intent.putExtra("CourseID",CourseID);
                       intent.putExtra("planerId",planerId);
                       intent.putExtra("CourseName",CourseName);
                       mCtx.startActivity(intent);

                   }
               });
           }else if(type.equals("PDF")){
               holder.icVideo.setVisibility(ImageView.GONE);
               holder.icPdf.setVisibility(ImageView.VISIBLE);
               holder.Titele.setText(n.Title.toString());
               holder.Filesection.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       String type=n.Type.toString();
                       String URL=n.Video.toString();

                       Intent testIntent = new Intent(Intent.ACTION_VIEW);
                       testIntent.setType("application/pdf");
                       Intent intent = new Intent();
                       intent.setAction(Intent.ACTION_VIEW);
                       Uri uri = Uri.parse(BASE_URL+URL);
                       intent.setDataAndType(uri, "application/pdf");
                       mCtx.startActivity(intent);
                      /* Intent intent = new Intent(mCtx, ReadActivity.class);
                       intent.putExtra("Type",type.toString());
                       intent.putExtra("url",URL);
                       mCtx.startActivity(intent);*/
                   }
               });
           }else {
               holder.Texttitel.setText(n.Title.toString());
               holder.Filesection.setVisibility(LinearLayout.GONE);
               holder.TextSection.setVisibility(LinearLayout.VISIBLE);
               holder.TextSection.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       String URLtext=n.TextContant.toString();
                       String type=n.Type.toString();
                       String CourseName=n.getTitle();
                       String CourseID = String.valueOf(n.getCourseID());
                       String planerId = String.valueOf(n.getPlanID());
                       Intent intent = new Intent(mCtx, ReadActivity.class);
                       intent.putExtra("Type",type.toString());
                       intent.putExtra("text",URLtext);
                       intent.putExtra("planerId",planerId);
                       intent.putExtra("CourseID",CourseID);
                       intent.putExtra("CourseName",CourseName);
                       mCtx.startActivity(intent);
                   }
               });
           }

        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    @Override
    public int getItemCount() {

        return lectureModelList.size();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leacture_list_adapter, parent, false);

        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Titele, Vidio, Texttitel, Textcon;
        ImageView icVideo, icPdf;
        LinearLayout Filesection, TextSection;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);
            Titele = view.findViewById(R.id.titele);
            Vidio = view.findViewById(R.id.video);
            Texttitel = view.findViewById(R.id.titeletext);
            Textcon = view.findViewById(R.id.contanttext);
            Filesection = view.findViewById(R.id.file);
            icVideo = view.findViewById(R.id.iconvidio);
            icPdf = view.findViewById(R.id.iconpdf);
            TextSection = view.findViewById(R.id.textcontant);
        }


    }

}