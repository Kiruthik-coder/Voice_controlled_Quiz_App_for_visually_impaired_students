package com.example.vquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;

import android.content.SharedPreferences;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;


public class Login_Activity extends AppCompatActivity {

    Button bt1, bt2;
    EditText et1, et2;
    TextToSpeech textToSpeech;
    FirebaseAuth mAuth;

    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        et1 = (EditText) findViewById(R.id.log_et1);
        et2 = (EditText) findViewById(R.id.log_et2);
        bt1 = (Button) findViewById(R.id.login_button);
        bt2 = (Button) findViewById(R.id.sign_up_bt);

        AlertDialog.Builder builder = new AlertDialog.Builder(Login_Activity.this);

        builder.setMessage("Please fill all the values");

        builder.setTitle("Error !");

        builder.setCancelable(false);
        builder.setView(R.layout.customalert);


        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("ok", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();

        String Speak = "Now lets set up your account, if you are a user already then login else sign up";

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


        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent
                        = new Intent(Login_Activity.this,
                        Home_Activity.class);
                startActivity(intent);

                String mail = et1.getText().toString();
                String pass = et2.getText().toString();

                if (mail.equals("") || pass.equals("")) {
                    alertDialog.show();
                    //Toast.makeText(Login_Activity.this, "Please fill all values", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(mail, pass)
                            .addOnCompleteListener(
                                    new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(
                                                @NonNull Task<AuthResult> task)
                                        {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(),
                                                                "Login successful!!",
                                                                Toast.LENGTH_LONG)
                                                        .show();

                                                SharedPreferences settings = getApplicationContext().getSharedPreferences("UserData", 0);
                                                SharedPreferences.Editor editor = settings.edit();

                                                editor.putString("email", mail);
                                                editor.apply();

                                                Intent intent
                                                        = new Intent(Login_Activity.this,
                                                        Home_Activity.class);
                                                startActivity(intent);
                                            }

                                            else {
                                                Toast.makeText(getApplicationContext(),
                                                                "Login failed!!",
                                                                Toast.LENGTH_LONG)
                                                        .show();
                                                //progressbar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                }
            }
        });


        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login_Activity.this, Reg_activity.class);
                startActivity(i);
            }
        });
    }
}