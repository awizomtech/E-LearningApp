package com.awizomtech.elearning.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.awizomtech.elearning.AppConfig.AppConfig;
import com.awizomtech.elearning.R;
import com.bumptech.glide.Glide;

public class InstructorDetailActivity extends AppCompatActivity {
    TextView Name,Mobile, Email,Degree,DegreeText,Date;
    ImageView imageView;
    CardView Cardview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_detail);
        InitView();
    }

    private void InitView() {
        Name = findViewById(R.id.name);
        imageView=findViewById(R.id.image);
        Cardview=findViewById(R.id.cardview);
        Degree=findViewById(R.id.degree);
        Mobile=findViewById(R.id.mobile);
        Email=findViewById(R.id.email);
        DegreeText=findViewById(R.id.degreetext);
        Date=findViewById(R.id.joindate);

        String InsName=getIntent().getExtras().getString("InsName");
        String InsImage = getIntent().getExtras().getString("InsImage");
        String InsdegreeOption=getIntent().getExtras().getString("InsdegreeOption");
        String InsPhone =getIntent().getExtras().getString("InsPhone");
        String InsEmail = getIntent().getExtras().getString("InsEmail");
        String InsDegreeText = getIntent().getExtras().getString("InsDegreeText");
        String Dates = getIntent().getExtras().getString("Date");


       Name.setText("Name : "+InsName.toString());
       Mobile.setText("Phone : "+InsPhone.toString());
       Email.setText("Email : "+InsEmail.toString());
        String date = Dates.split("T")[0];
        Date.setText("Joining Date : "+date.toString());
       Degree.setText("Degree : "+InsdegreeOption.toString());
      DegreeText.setText("Degree : "+InsDegreeText.toString());
        if(InsdegreeOption.contains("OTHER")){
            DegreeText.setVisibility(View.VISIBLE);
        }else {
            Degree.setVisibility(View.VISIBLE);
        }

        try {
            Glide.with(this).load(AppConfig.BASE_URL + InsImage.toString()).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}