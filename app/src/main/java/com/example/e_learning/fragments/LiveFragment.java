package com.example.e_learning.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.e_learning.Helper.UserHelper;
import com.example.e_learning.Model.CourseListModel;
import com.example.e_learning.Model.PaymentModel;
import com.example.e_learning.MyCourseAdapter;
import com.example.e_learning.MyPaymentAdapter;
import com.example.e_learning.R;
import com.example.e_learning.SharedPrefManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class LiveFragment extends Fragment {
    MyPaymentAdapter adapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<PaymentModel> paymentModels;
    private String result = "";
    View rootView;
    androidx.recyclerview.widget.RecyclerView recyclerView;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.fragment_live, container, false);
         InitView();
        return rootView;
    }

    private void InitView() {

        recyclerView=rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeToRefresh);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetCourselist();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        GetCourselist();
    }

    private void GetCourselist() {
        try {
            String userid= SharedPrefManager.getInstance(context).getUser().getUserID();
            result = new UserHelper.GetMyPaymentList().execute(userid.toString()).get();
            if (result.isEmpty()) {
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<PaymentModel>>() {
                }.getType();
                paymentModels= new Gson().fromJson(result, listType);
                Log.d("Error", paymentModels.toString());
                adapter = new MyPaymentAdapter(context, paymentModels);
                recyclerView.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}



