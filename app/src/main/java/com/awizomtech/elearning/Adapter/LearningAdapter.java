package com.awizomtech.elearning.Adapter;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.awizomtech.elearning.Helper.UserHelper;
import com.awizomtech.elearning.Model.CourseLevelModel;
import com.awizomtech.elearning.Model.LectureModel;
import com.awizomtech.elearning.Model.LevelTopicModel;
import com.awizomtech.elearning.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LearningAdapter extends RecyclerView.Adapter<LearningAdapter.MyViewHolder> {
    LectureAdapter adapter;
    private List<LevelTopicModel> levelTopicModelList;
    private List<LectureModel> lectureModels;
    private Context mCtx;

    public LearningAdapter(Context baseContext, List<LevelTopicModel> levelTopicModelList) {
        this.levelTopicModelList = levelTopicModelList;
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
        final LevelTopicModel n = levelTopicModelList.get(position);
        holder.Coursename.setText(n.getPlannerList().toString());
        holder.Down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.Down.setVisibility(ImageView.GONE);
                holder.Lecture.setVisibility(LinearLayout.VISIBLE);
                holder.Up.setVisibility(ImageView.VISIBLE);

            }
        });

        holder.Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.Up.setVisibility(ImageView.GONE);
                holder.Lecture.setVisibility(LinearLayout.GONE);
                holder.Down.setVisibility(ImageView.VISIBLE);

            }
        });
        try {
            String cid = String.valueOf(n.getPlannerDetailID());
            String   result = new UserHelper.GetLectureList().execute(cid.toString()).get();
            if (result.isEmpty()) {
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<LectureModel>>() {
                }.getType();
                lectureModels = new Gson().fromJson(result, listType);
                Log.d("Error", lectureModels.toString());
                adapter = new LectureAdapter(mCtx, lectureModels);
                holder.LectureView.setAdapter(adapter);
                holder.LectureView.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @Override
    public int getItemCount() {

        return levelTopicModelList.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.learning_list_adapter, parent, false);

        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Coursename;
        RecyclerView LectureView;
        ImageView Up,Down;
        LinearLayout Lecture;
        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);
            Coursename = view.findViewById(R.id.coursename);
            Up = view.findViewById(R.id.up);
            Down = view.findViewById(R.id.down);
            Lecture = view.findViewById(R.id.leature);
            LectureView = view.findViewById(R.id.lecturevirew);
            LectureView.setHasFixedSize(true);
            LectureView.setLayoutManager(new LinearLayoutManager(mCtx));
        }


    }

}