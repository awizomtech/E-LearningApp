package com.example.e_learning.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.e_learning.Activity.CourseListActivity;
import com.example.e_learning.Activity.MyCourseActivity;
import com.example.e_learning.Activity.MyPaymentActivity;
import com.example.e_learning.Activity.ProfileActivity;
import com.example.e_learning.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    View root;
    CardView Course,Mycourse,Myprofile,Mypayment;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
     root = inflater.inflate(R.layout.fragment_home, container, false);
      /*  final TextView textView = root.findViewById(R.id.text_home);*//*
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        Initview();
        return root;
    }

    private void Initview() {

        Mycourse=root.findViewById(R.id.mycourse);
        Mypayment=root.findViewById(R.id.mypayment);
        Myprofile=root.findViewById(R.id.profile);
        Course=root.findViewById(R.id.course);
        Course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), CourseListActivity.class);
                startActivity(intent);
            }
        });
        Mypayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), MyPaymentActivity.class);
                startActivity(intent);
            }
        });
        Mycourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), MyCourseActivity.class);
                startActivity(intent);
            }
        });
        Myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
