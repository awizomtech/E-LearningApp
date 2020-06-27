package com.example.e_learning.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.e_learning.R;

import static com.example.e_learning.AppConfig.AppConfig.BASE_URL;

public class ReadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        WebView webView=findViewById(R.id.webview);
        TextView textView=findViewById(R.id.textview);
        String type = getIntent().getExtras().getString("Type");
        String URl = getIntent().getExtras().getString("url");
        String Text = getIntent().getExtras().getString("text");
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(BASE_URL+URl);
        textView.setText(textView.toString());


    }
}