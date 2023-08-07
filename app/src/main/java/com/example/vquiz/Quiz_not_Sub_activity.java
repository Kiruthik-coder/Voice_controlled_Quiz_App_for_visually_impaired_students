package com.example.vquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class Quiz_not_Sub_activity extends AppCompatActivity {

    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_not_sub);

        //will replace the number later...
        String Speak = "So far you have Missed 2 Quizzes";

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i!=TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            public void run() {
                textToSpeech.speak(Speak,TextToSpeech.QUEUE_FLUSH,null);
            }
        }, 100);

    }
}