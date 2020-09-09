package com.awizomtech.elearning.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.awizomtech.elearning.Model.LevelDetailTopicModel;
import com.awizomtech.elearning.R;
import java.util.List;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class LevelTopicDetailAdapter extends RecyclerView.Adapter<LevelTopicDetailAdapter.MyViewHolder> {

    private List<LevelDetailTopicModel> levelDetailTopicModelList;
    private Context mCtx;

    public LevelTopicDetailAdapter(Context baseContext, List<LevelDetailTopicModel> levelDetailTopicModelList) {
        this.levelDetailTopicModelList = levelDetailTopicModelList;
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
        final LevelDetailTopicModel n = levelDetailTopicModelList.get(position);
        holder.Coursename.setText(n.getTopicName().toString());
    }

    @Override
    public int getItemCount() {

        return levelDetailTopicModelList.size();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.level_detail_list_adapter, parent, false);
        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Coursename;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);
            Coursename = view.findViewById(R.id.levelname);
        }

    }
}