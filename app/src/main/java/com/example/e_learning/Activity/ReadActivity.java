package com.example.e_learning.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.e_learning.R;

import java.io.File;
import java.util.List;

import static com.example.e_learning.AppConfig.AppConfig.BASE_URL;

public class ReadActivity extends AppCompatActivity {

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
        String type = getIntent().getExtras().getString("Type");
        String URl = getIntent().getExtras().getString("url");
        String Text = getIntent().getExtras().getString("text");
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        if(type.equals("Video")){
            webView.loadUrl(BASE_URL+URl);
        }else {
            webView.setVisibility(WebView.GONE);
            textView.setVisibility(TextView.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                textView.setText(Html.fromHtml(Text, Html.FROM_HTML_MODE_COMPACT));
            } else {
                textView.setText(Html.fromHtml(Text));
            }
        }
    }
}