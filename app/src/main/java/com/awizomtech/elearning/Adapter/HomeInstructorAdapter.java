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
        import com.awizomtech.elearning.Activity.QuizActivity;
        import com.awizomtech.elearning.AppConfig.AppConfig;
        import com.awizomtech.elearning.Model.InstructorModel;
        import com.awizomtech.elearning.Model.QuizModel;
        import com.awizomtech.elearning.R;
        import com.bumptech.glide.Glide;

        import java.util.List;

                import androidx.annotation.RequiresApi;
                import androidx.cardview.widget.CardView;
                import androidx.recyclerview.widget.RecyclerView;

public class HomeInstructorAdapter extends RecyclerView.Adapter<HomeInstructorAdapter.MyViewHolder> {

    private List<InstructorModel> instructorModelList;
    private Context mCtx;
    String result = "";
    private AlertDialog progressDialog;


    public HomeInstructorAdapter(Context baseContext, List<InstructorModel> instructorModelList) {
        this.instructorModelList = instructorModelList;
        this.mCtx = baseContext;

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final InstructorModel n = instructorModelList.get(position);
        holder.Name.setText(n.getInsName().toString());
        holder.Degree.setText(n.getInsdegreeOption().toString());
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
                .inflate(R.layout.home_instructor_list_adapter, parent, false);

        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView  Name,Degree;
        ImageView imageView;
        CardView Cardview;
        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);
            Name = view.findViewById(R.id.name);
            imageView=view.findViewById(R.id.image);
            Cardview=view.findViewById(R.id.cardview);
            Degree=view.findViewById(R.id.degree);
        }


    }

}