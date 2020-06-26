package com.example.e_learning;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.e_learning.AppConfig.AppConfig;
import com.example.e_learning.Model.CourseListModel;
import com.example.e_learning.Model.PaymentModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

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
        holder.Courseid.setText(String.valueOf(n.CourseID));
        String date = n.getPaymentDate().split("T")[0];
        holder.Date.setText("Date " + date);
        holder.Price.setText(String.valueOf("Pay Amount " + n.Amount + "₹"));
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

        TextView Coursename, Courseid, Price, Date, PayAmt;
        ImageView imageView;
        CardView Cardview, Verify, Verified;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);
            Courseid = view.findViewById(R.id.courseid);
            Coursename = view.findViewById(R.id.coursename);
            Date = view.findViewById(R.id.date);
            Price = view.findViewById(R.id.price);
            PayAmt = view.findViewById(R.id.payAmt);
            imageView = view.findViewById(R.id.image);
            Cardview = view.findViewById(R.id.cardview);
            Verify = view.findViewById(R.id.virify);
            Verified = view.findViewById(R.id.virified);
        }


    }

}