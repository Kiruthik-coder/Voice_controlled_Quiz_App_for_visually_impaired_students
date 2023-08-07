package com.example.vquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.*;

import java.util.Locale;

public class Reg_activity extends AppCompatActivity {

    EditText et1, et2;
    Button bt;
    TextToSpeech textToSpeech;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_reg);

        mAuth = FirebaseAuth.getInstance();

        et1 = (EditText) findViewById(R.id.reg_et1);
        et2 = (EditText) findViewById(R.id.reg_et2);
        bt = (Button)  findViewById(R.id.reg_bt);

        String Speak = "Lets create an account for you, Please use your mail id with a password with more than six characters " +
                "and Say register";

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

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = et1.getText().toString();
                String pass = et2.getText().toString();

                if (mail.equals("") || pass.equals("")) {
                    Toast.makeText(Reg_activity.this, "Please fill all values", Toast.LENGTH_SHORT).show();
                } else {
                        mAuth.createUserWithEmailAndPassword(mail, pass)
                                .addOnCompleteListener(Reg_activity.this,new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(Reg_activity.this, "Registration Completed", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(Reg_activity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                }
            }
        });
    }
}