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
        import com.example.e_learning.Activity.CourseDetailActivity;
        import com.example.e_learning.Activity.QuizActivity;
        import com.example.e_learning.Activity.QuizCourseActivity;
        import com.example.e_learning.Model.QuizModel;
        import com.example.e_learning.R;

        import java.util.List;

        import androidx.annotation.RequiresApi;
        import androidx.cardview.widget.CardView;
        import androidx.recyclerview.widget.RecyclerView;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.MyViewHolder> {

    private List<QuizModel> quizModelList;
    private Context mCtx;
    String result = "";
    private AlertDialog progressDialog;


    public QuizAdapter(Context baseContext, List<QuizModel> quizModelList) {
        this.quizModelList = quizModelList;
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
        final QuizModel n = quizModelList.get(position);
        holder.Coursename.setText(n.CourseName.toString());



        holder.Cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cid = String.valueOf(n.getCourseID());
                String coursename=n.getCourseName().toString();
                Intent intent = new Intent(mCtx, QuizActivity.class);
                intent.putExtra("CourseID",cid);
                intent.putExtra("Coursename",coursename);
                mCtx.startActivity(intent);
            }
        });

        holder.Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cid = String.valueOf(n.getCourseID());
                String coursename=n.getCourseName().toString();
                Intent intent = new Intent(mCtx, QuizActivity.class);
                intent.putExtra("CourseID",cid);
                intent.putExtra("Coursename",coursename);
                mCtx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return quizModelList.size();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quiz_list_adapter, parent, false);

        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Coursename;
        ImageView imageView;
        CardView Cardview,Details;
        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);
            Coursename = view.findViewById(R.id.coursename);
            imageView=view.findViewById(R.id.courseImage);
            Cardview=view.findViewById(R.id.cardview);
            Details=view.findViewById(R.id.detail);
        }


    }

}
