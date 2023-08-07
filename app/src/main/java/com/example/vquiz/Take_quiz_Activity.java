package com.example.vquiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class Take_quiz_Activity extends AppCompatActivity implements View.OnClickListener{

    Button ansA, ansB, ansC, ansD, bt;

    TextView totalQuestionsTextView;
    TextToSpeech textToSpeech;
    TextView questionTextView;

    private static final int REQ_CODE_SPEECH_INPUT = 100;

    private TextView mVoiceInputTv;

    int score=0;
    int totalQuestion = QuestionAnswer.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_quiz);

        ansA = (Button) findViewById(R.id.button);
        ansB = (Button) findViewById(R.id.button2);
        ansC = (Button) findViewById(R.id.button3);
        ansD = (Button) findViewById(R.id.button4);
        mVoiceInputTv = (TextView) findViewById(R.id.vcinput2);

        totalQuestionsTextView = findViewById(R.id.txt1);
        questionTextView = findViewById(R.id.txt2);

        bt = (Button) findViewById(R.id.button5);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        bt.setOnClickListener(this);

        totalQuestionsTextView.setText("Total questions : "+totalQuestion);

        loadNewQuestion();

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i!=TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });

    }
    @Override
    public void onClick(View view) {

        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);

        Button clickedButton = (Button) view;
        if(clickedButton.getId()==R.id.button5){
            if(selectedAnswer.equals(QuestionAnswer.correctAnswers[currentQuestionIndex])){
                score++;
            }
            currentQuestionIndex++;
            loadNewQuestion();


        }else{
            //choices button clicked
            selectedAnswer  = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.MAGENTA);

        }

    }

    void loadNewQuestion(){

        if(currentQuestionIndex == totalQuestion ){
            finishQuiz();
            return;
        }

        questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
        ansA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
        ansB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
        ansC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
        ansD.setText(QuestionAnswer.choices[currentQuestionIndex][3]);

        String Speak = QuestionAnswer.question[currentQuestionIndex] +"options are "+ "or " +QuestionAnswer.choices[currentQuestionIndex][0] + " or "+
                QuestionAnswer.choices[currentQuestionIndex][1] +" or "+ QuestionAnswer.choices[currentQuestionIndex][2] +" or "+
                QuestionAnswer.choices[currentQuestionIndex][3];

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            public void run() {
                textToSpeech.speak(Speak,TextToSpeech.QUEUE_FLUSH,null);

            }

        }, 100);

        /*Handler j = new Handler();
        j.postDelayed(new Runnable() {
            public void run() {
                startVoiceInput();
            }

        }, 8000);*/




    }

    void finishQuiz(){
        String passStatus = "";
        if(score > totalQuestion*0.60){
            passStatus = "Completed";
        }else{
            passStatus = "Completed";
        }

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Score is "+ score+" out of "+ totalQuestion)
                .setPositiveButton("ok",(dialogInterface, i) -> QuizComplete() )
                .setNegativeButton("Try Again",(dialogInterface, i) -> restartQuiz() )
                .setCancelable(false)
                .show();
    }

    void restartQuiz(){
        score = 0;
        currentQuestionIndex =0;
        loadNewQuestion();
    }

    void QuizComplete(){
        Intent i = new Intent(Take_quiz_Activity.this, Home_Activity.class);
        startActivity(i);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mVoiceInputTv.setText(result.get(0));
                }
                if(mVoiceInputTv.getText().toString().equals("profile"))
                {

                }
                break;
            }

        }
    }

}
