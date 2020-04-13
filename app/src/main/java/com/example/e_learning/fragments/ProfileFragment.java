package com.example.e_learning.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.e_learning.R;

public class ProfileFragment extends Fragment {

    Context context;

    TextView tv_title, tv_create_profile, tv_desc, tv_follow;
    LinearLayout ll_login_signup;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        context=getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
/*

        tv_title=rootView.findViewById(R.id.tv_title);
        tv_create_profile=rootView.findViewById(R.id.tv_create_profile);
        tv_desc=rootView.findViewById(R.id.tv_desc);
        tv_follow=rootView.findViewById(R.id.tv_follow);
        ll_login_signup=rootView.findViewById(R.id.ll_login_signup);

*/


    /*    ll_login_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, LoginRegisterActivity.class));
            }
        });*/

        return rootView;
    }

}
