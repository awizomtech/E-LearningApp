package com.awizomtech.elearning.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.awizomtech.elearning.R;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class FitnessTestActivity extends AppCompatActivity {
    TextToSpeech tts;
    String Speck = "";
    public int counter;
    public int timer;
    String distance, runningTime, restTime, round;
   public int SetRunning,SetRest,SetRound;
    TextView counttime, Distance, RunningTime, RestTime, Round, TotalTime, CurrentRound;
    Button Start, Stop;
    public int i;
    CountDownTimer myCountDownTimer;
    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_test);
        counttime = findViewById(R.id.counttime);
        InitView();
    }

    private void InitView() {
        ImageView backpress=findViewById(R.id.back);
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Distance = findViewById(R.id.distance);
        RunningTime = findViewById(R.id.runningtime);
        RestTime = findViewById(R.id.resttime);
        Round = findViewById(R.id.round);
        CurrentRound = findViewById(R.id.currentround);
        TotalTime = findViewById(R.id.totaltime);
        Start = findViewById(R.id.startbtn);
        Stop = findViewById(R.id.stopbtn);

        distance = getIntent().getExtras().getString("Distance");
        runningTime = getIntent().getExtras().getString("RunningTime");
        restTime = getIntent().getExtras().getString("RestTime");
        round = getIntent().getExtras().getString("Round");

        Distance.setText(distance);
        RunningTime.setText(runningTime);
        RestTime.setText(restTime);
        Round.setText(round);

        String runtime= runningTime + "000";
        String resttime= restTime + "000";
        SetRunning=Integer.valueOf(runtime);
        SetRest =Integer.valueOf(resttime);
        SetRound =Integer.valueOf(round);
        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Start.setVisibility(Button.GONE);
                Stop.setVisibility(Button.VISIBLE);
                CoundowdStart();
            }
        });
        Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCountDownTimer.cancel();
                Start.setVisibility(Button.VISIBLE);
                Stop.setVisibility(Button.GONE);
            }
        });
    }

    public void CoundowdStart() {

      myCountDownTimer =  new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                Speck = String.valueOf(counter);
                SpeachText();
                TotalTime.setText(String.valueOf(timer));
                CurrentRound.setText(String.valueOf(i));
                timer++;
                counter++;
            }

            @Override
            public void onFinish() {
                counter=0;
               /* Speck = "Go";
                SpeachText();*/
                MediaPlayer ring= MediaPlayer.create(FitnessTestActivity.this,R.raw.beep1);
                ring.start();
                RunningStart();
            }

        }.start();
    }

    public void RunningStart() {
        myCountDownTimer =   new CountDownTimer(SetRunning, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TotalTime.setText(String.valueOf(timer));
                timer++;
            }

            @Override
            public void onFinish() {
                BeepVoicedealy();
            }

        }.start();

    }

    public void BeepVoicedealy() {
        myCountDownTimer =    new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                MediaPlayer ring= MediaPlayer.create(FitnessTestActivity.this,R.raw.beep1);
                ring.start();
                TotalTime.setText(String.valueOf(timer));
                timer++;
            }

            @Override
            public void onFinish() {
                RrestTime();
            }

        }.start();
    }

    public void RrestTime() {
        myCountDownTimer =   new CountDownTimer(SetRest, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                MediaPlayer ring= MediaPlayer.create(FitnessTestActivity.this,R.raw.beep2);
                ring.start();
                TotalTime.setText(String.valueOf(timer));
                timer++;
            }

            @Override
            public void onFinish() {
                NewStartDealy();
            }

        }.start();
    }

    public void NewStartDealy() {
        myCountDownTimer =    new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                MediaPlayer ring= MediaPlayer.create(FitnessTestActivity.this,R.raw.beep1);
                ring.start();
                TotalTime.setText(String.valueOf(timer));
                timer++;
            }

            @Override
            public void onFinish() {

                if (i <= SetRound) {
                    i = i + 1;

                    new CountDownTimer(1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            Speck = "Round " + i;
                            SpeachText();
                            TotalTime.setText(String.valueOf(timer));
                            timer++;
                        }

                        @Override
                        public void onFinish() {
                           /* CoundowdStart();*/
                            RunningStart();
                        }
                    }.start();

                } else {
                    Speck = "All Rounds Completed";
                    SpeachText();
                    Start.setVisibility(Button.VISIBLE);
                    Stop.setVisibility(Button.GONE);
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