package com.awizomtech.elearning.Adapter;

        import android.content.Context;
        import android.content.Intent;
        import android.os.Build;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;
        import com.awizomtech.elearning.Activity.LearningActivity;
        import com.awizomtech.elearning.Model.MyCourseLevelModel;
        import com.awizomtech.elearning.R;

        import java.util.List;

        import androidx.annotation.RequiresApi;
        import androidx.cardview.widget.CardView;
        import androidx.recyclerview.widget.RecyclerView;

public class MyCourseLevelAdapter extends RecyclerView.Adapter<MyCourseLevelAdapter.MyViewHolder> {

    private List<MyCourseLevelModel> myCourseLevelModelList ;
    private Context mCtx;

    public MyCourseLevelAdapter(Context baseContext, List<MyCourseLevelModel> myCourseLevelModelList) {
        this.myCourseLevelModelList = myCourseLevelModelList;
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
        final MyCourseLevelModel n = myCourseLevelModelList.get(position);
        holder.Coursename.setText(n.Level.toString());
        holder.Duration.setText("Duration " +n.getDuration().toString());
        holder.Price.setText("Price "+ String.valueOf(n.getPrice()));
        holder.payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cid = String.valueOf(n.getCourseID());
                String lid = String.valueOf(n.getLevelID());
                Intent intent = new Intent(mCtx, LearningActivity.class);
                intent.putExtra("levelID",lid);
                intent.putExtra("Cid",cid);
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return myCourseLevelModelList.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_course_level_list_adapter, parent, false);

        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Coursename,Duration,Price;
        CardView payment;
        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);
            Coursename = view.findViewById(R.id.coursename);
            payment=view.findViewById(R.id.cardview);
            Duration=view.findViewById(R.id.courseDuration);
            Price=view.findViewById(R.id.coursePrice);
        }


    }

}