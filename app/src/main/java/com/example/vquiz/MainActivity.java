package com.example.vquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_main);


        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            public void run() {
                SharedPreferences settings = getApplicationContext().getSharedPreferences("UserData", 0);
                String mail = settings.getString("email", "");

                if(mail.equals("")) {
                    Intent i;
                    i = new Intent(MainActivity.this, Get_Started_Activity.class);
                    startActivity(i);
                }else {
                    Intent i;
                    i = new Intent(MainActivity.this, Home_Activity.class);
                    startActivity(i);
                }

            }
        }, 5000);
    }
}