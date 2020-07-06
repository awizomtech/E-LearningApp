package com.example.e_learning.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.View;
import com.example.e_learning.R;
import com.example.e_learning.SharePrefrence.SharedPrefManager;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tts=new TextToSpeech(SplashActivity.this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                // TODO Auto-generated method stub
                if(status == TextToSpeech.SUCCESS){
                    int result=tts.setLanguage(Locale.US);
                    if(result==TextToSpeech.LANG_MISSING_DATA ||
                            result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("error", "This Language is not supported");
                    }
                    else{
                        ConvertTextToSpeech();
                    }
                }
                else
                    Log.e("error", "Initilization Failed!");
            }
        });

        String userid = SharedPrefManager.getInstance(this).getUser().getUserID().toString();
        if (userid != "") {
            new Timer().schedule(new TimerTask() {
                public void run() {
                    startActivity(new Intent(SplashActivity.this, HomePageActivity.class));
                    finish();

                }
            }, 2000);

        }
        else {
            new Timer().schedule(new TimerTask() {
                public void run() {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();

                }
            }, 2000);
        }

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub

        if(tts != null){

            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }

    public void onClick(View v){

        ConvertTextToSpeech();

    }

    private void ConvertTextToSpeech() {
        // TODO Auto-generated method stub
        String Speck="Edua 4 Sports";

        Set<String> a=new HashSet<>();
        a.add("male");
        Voice v=new Voice("en-us-x-sfg#male_2-local",new Locale("en","US"),400,200,true,a);
        tts.setVoice(v);
       /* tts.setSpeechRate(0.5f);*/
        tts.speak(Speck, TextToSpeech.QUEUE_FLUSH, null);
    }
}