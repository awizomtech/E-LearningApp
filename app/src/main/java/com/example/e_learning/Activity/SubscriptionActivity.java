package com.example.e_learning.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.e_learning.Model.CourseListModel;
import com.example.e_learning.R;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner SelectPay, SelectEmi;
    String  result;
    private List<CourseListModel> courseListModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        InitView();
    }

    private void InitView() {
        SelectPay  = (Spinner) findViewById(R.id.selectpaymenttype);
        SelectEmi  = (Spinner) findViewById(R.id.selectemi);
        SelectPay.setOnItemSelectedListener(SubscriptionActivity.this);

        try {
          /*  result = new UserHelper.GetCourseList().execute().get();
            if (result.isEmpty()) {
            } else {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<CourseListModel>>() {
                }.getType();
                courseListModels= new Gson().fromJson(result, listType);
               *//* Log.d("Error", courseListModels.toString());
                adapter = new CourseAdapter(CourseListActivity.this, courseListModels);
                recyclerView.setAdapter(adapter);*//*
            }*/
        } catch (Exception e) {
            e.printStackTrace();

        }
        List<String> categories = new ArrayList<String>();
      /*  for(int i=0;i>=courseListModels.size();i++){
            CourseListModel madel = new CourseListModel();
            categories.add(String.valueOf(madel.getCourseName()));
        }*/
        // Spinner Drop down elements

        categories.add("Cash");
        categories.add("EMI");
        categories.add("Item 3");
        categories.add("Item 4");
        categories.add("Item 5");
        categories.add("Item 6");
        // Creating adapter for spinner
        ArrayAdapter<String> PayTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        PayTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        SelectPay.setAdapter(PayTypeAdapter);

        SelectEmi.setOnItemSelectedListener(SubscriptionActivity.this);

        // Spinner Drop down elements
        List<String> emi = new ArrayList<String>();
        emi.add("Emi ");
        emi.add("Item 2");
        emi.add("Item 3");
        emi.add("Item 4");
        emi.add("Item 5");
        emi.add("Item 6");

        // Creating adapter for spinner
        ArrayAdapter<String> emiAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, emi);

        // Drop down layout style - list view with radio button
        emiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        SelectEmi.setAdapter(emiAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}