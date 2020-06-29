package com.example.e_learning.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.e_learning.Adapter.MyPaymentAdapter;
import com.example.e_learning.Helper.UserHelper;
import com.example.e_learning.Model.PaymentModel;
import com.example.e_learning.R;
import com.example.e_learning.SharePrefrence.SharedPrefManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MyPaymentActivity extends AppCompatActivity {
    MyPaymentAdapter adapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<PaymentModel> paymentModels;
    private String result = "";
    androidx.recyclerview.widget.RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_payment);
        InitView();
    }

    private void InitView() {

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetPaymentlist();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        GetPaymentlist();
    }

    private void GetPaymentlist() {
        try {
            String userid= SharedPrefManager.getInstance(this).getUser().getUserID();
            result = new UserHelper.GetMyPaymentList().execute(userid.toString()).get();
            if (result.isEmpty()) {
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<PaymentModel>>() {
                }.getType();
                paymentModels= new Gson().fromJson(result, listType);
                Log.d("Error", paymentModels.toString());
                adapter = new MyPaymentAdapter(this, paymentModels);
                recyclerView.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}