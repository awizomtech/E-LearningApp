package com.example.e_learning.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_learning.R;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class FitnessTestActivity extends AppCompatActivity {
    TextToSpeech tts;
    String Speck = "";
    public int counter;
    TextView counttime;

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_test);
        counttime = findViewById(R.id.counttime);
        CoundowdStart();
       /* new CountDownTimer(6000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                counttime.setText(String.valueOf(counter));
                Speck =counttime.getText().toString();
                SpeachText();
                counter++;
            }
            @Override
            public void onFinish() {
                Speck="Go";
                SpeachText();
                RunningStart();
                counttime.setText("Finished");
            }

        }.start();*/
    }

    public void CoundowdStart() {
        final int[] count1 = new int[1];
        new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                Speck = String.valueOf(count1[0]);
                SpeachText();
                count1[0]++;
            }

            @Override
            public void onFinish() {
                Speck = "Go";
                SpeachText();
                RunningStart();
                counttime.setText("Finished");
            }

        }.start();
    }

    public void RunningStart() {
        new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                counter++;
            }

            @Override
            public void onFinish() {
                Speck = "Beep Beep Beep";
                SpeachText();
                RrestTime();
            }

        }.start();

    }

    public void RrestTime() {
        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Speck = "Rest   Kro   bhai";
                counter++;
            }

            @Override
            public void onFinish() {
                Speck = "Beeeeeeep";
                SpeachText();
                for (int i = 1; i < 5; i++) {
                    CoundowdStart();
                }
            }

        }.start();
    }

    private void SpeachText() {

        tts = new TextToSpeech(FitnessTestActivity.this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                // TODO Auto-generated method stub
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("error", "This Language is not supported");
                    } else {
                        ConvertTextToSpeech();
                    }
                } else
                    Log.e("error", "Initilization Failed!");
            }
        });
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub

        if (tts != null) {

            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }

    public void onClick(View v) {

        ConvertTextToSpeech();

    }

    private void ConvertTextToSpeech() {
        // TODO Auto-generated method stub
        Set<String> a = new HashSet<>();
        a.add("male");
        Voice v = new Voice("en-us-x-sfg#male_2-local", new Locale("en", "US"), 400, 200, true, a);
        tts.setVoice(v);
        /* tts.setSpeechRate(0.5f);*/
        tts.speak(Speck, TextToSpeech.QUEUE_FLUSH, null);
    }
}