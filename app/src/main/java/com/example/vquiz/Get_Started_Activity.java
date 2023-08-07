package com.example.vquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import java.util.Locale;

public class Get_Started_Activity extends AppCompatActivity {
    TextToSpeech textToSpeech;

    private TextView mVoiceInputTv;

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_get_started);
        bt = (Button) findViewById(R.id.bt1);
        mVoiceInputTv = (TextView) findViewById(R.id.voiceInput);
        String Speak = "Welcome to our voice quiz app! Get ready to put your listening skills to the test and have some fun along the way," +
                "Now say start when your hear a sound";

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i!=TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Get_Started_Activity.this, Login_Activity.class);
                startActivity(i);
            }
        });



        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            public void run() {
                textToSpeech.speak(Speak,TextToSpeech.QUEUE_FLUSH,null);

            }

        }, 100);

        Handler j = new Handler();
        j.postDelayed(new Runnable() {
            public void run() {
                startVoiceInput();
            }
        }, 10000);

    }

    public void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mVoiceInputTv.setText(result.get(0));
                }
                if(mVoiceInputTv.getText().toString().equals("start") || mVoiceInputTv.getText().toString().equals("ok") || mVoiceInputTv.getText().toString().equals("lets start"))
                {
                    Intent intent=new Intent(getApplicationContext(),Login_Activity.class);
                    startActivity(intent);
                }
                break;
            }

        }
    }
}