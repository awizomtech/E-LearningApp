package com.awizomtech.elearning.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.awizomtech.elearning.R;

import static com.awizomtech.elearning.AppConfig.AppConfig.BASE_URL;

public class ReadActivity extends AppCompatActivity {
String planerId,CourseID,CourseName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        ImageView backpress=findViewById(R.id.back);
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        WebView webView=findViewById(R.id.webview);
        TextView textView=findViewById(R.id.textview);
        Button test= findViewById(R.id.testmake);
        String type = getIntent().getExtras().getString("Type");
        String URl = getIntent().getExtras().getString("url");
        String Text = getIntent().getExtras().getString("text");
     planerId = getIntent().getExtras().getString("planerId");
        CourseID = getIntent().getExtras().getString("CourseID");
        CourseName = getIntent().getExtras().getString("CourseName");
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        if(type.equals("Video")){
            webView.loadUrl(BASE_URL+URl);
        }else {
           /* if(type.equals("Free")){
                test.setVisibility(Button.VISIBLE);
            }*/
            webView.setVisibility(WebView.GONE);
            textView.setVisibility(TextView.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                textView.setText(Html.fromHtml(Text, Html.FROM_HTML_MODE_COMPACT));
            } else {
                textView.setText(Html.fromHtml(Text));
            }
        }
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ReadActivity.this, QuizActivity.class);
                intent.putExtra("CourseID",CourseID);
                intent.putExtra("planerId",planerId);
                intent.putExtra("CourseName",CourseName);
             startActivity(intent);
            }
        });
    }
}