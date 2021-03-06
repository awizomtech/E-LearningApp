package com.awizomtech.elearning.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.awizomtech.elearning.Model.PaymentModel;
import com.awizomtech.elearning.R;

import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MyPaymentAdapter extends RecyclerView.Adapter<MyPaymentAdapter.MyViewHolder> {

    private List<PaymentModel> paymentModelList;
    private Context mCtx;
    String result = "";
    private AlertDialog progressDialog;

    public MyPaymentAdapter(Context baseContext, List<PaymentModel> paymentModelList) {
        this.paymentModelList = paymentModelList;
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
        final PaymentModel n = paymentModelList.get(position);
        holder.Coursename.setText(n.CourseName.toString());
        holder.Level.setText(String.valueOf(n.getLevel()));
        holder.Courseid.setText(String.valueOf(n.CourseID));
        String date = n.getPaymentDate().split("T")[0];
        holder.Date.setText("Date " + date);
        holder.Price.setText(String.valueOf("Pay Amount " + n.Amount + "₹"));
        if(n.getCourseName().contains("Strength and Conditioning - Pre Level")){
            holder.Cardview.setVisibility(View.GONE);
        }
        if (!n.PaymentStatus == true) {
            holder.Verified.setVisibility(CardView.GONE);
            holder.Verify.setVisibility(CardView.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {

        return paymentModelList.size();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_payment_list_adapter, parent, false);

        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Coursename, Courseid, Price, Date, Level;
        ImageView imageView;
        CardView Cardview, Verify, Verified;
        LinearLayout Emisection;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);
            Courseid = view.findViewById(R.id.courseid);
            Coursename = view.findViewById(R.id.coursename);
            Date = view.findViewById(R.id.date);
            Price = view.findViewById(R.id.price);
            imageView = view.findViewById(R.id.image);
            Cardview = view.findViewById(R.id.cardview);
            Verify = view.findViewById(R.id.virify);
            Level = view.findViewById(R.id.courselevel);
           /* Emitype = view.findViewById(R.id.emitype);
            Interest = view.findViewById(R.id.interest);
            Duration = view.findViewById(R.id.duration);
            PayableAmt = view.findViewById(R.id.payableAmt);
            Mothlypay = view.findViewById(R.id.mothlypay);
            Emisection = view.findViewById(R.id.emisection);*/
            Verify = view.findViewById(R.id.virify);
            Verified = view.findViewById(R.id.virified);
        }


    }

}