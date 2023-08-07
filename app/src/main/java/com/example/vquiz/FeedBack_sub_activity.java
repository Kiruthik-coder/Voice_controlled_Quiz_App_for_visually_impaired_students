package com.example.vquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;
import java.util.UUID;

public class FeedBack_sub_activity extends AppCompatActivity {

    Button bt;

    TextToSpeech textToSpeech;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String uniqueID;

    ReportInfo EReportInfo;
    EditText et1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back_sub2);
        bt = (Button) findViewById(R.id.report_sub_bt);

        EReportInfo = new ReportInfo();

        uniqueID = UUID.randomUUID().toString();

        String ref = "FeedBack/" + uniqueID;

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(ref);

        String area = getIntent().getStringExtra("area");
        String other_details = getIntent().getStringExtra("det");

        addDatatoFirebase(area, other_details);

        String Speak = "You Report Has been Submitted successfully, thanks for you feedback";

        textToSpeech = new TextToSpeech(FeedBack_sub_activity.this, new TextToSpeech.OnInitListener() {
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
                Intent i = new Intent(FeedBack_sub_activity.this, Home_Activity.class);
                startActivity(i);
            }
        });
    }

    public void addDatatoFirebase(String errArea, String otherdet) {
        // below 3 lines of code is used to set
        // data in our object class.
        EReportInfo.setErrorArea(errArea);
        EReportInfo.setOtherDetails(otherdet);

        // we are use add value event listener method
        // which is called with database reference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.

                try {
                    databaseReference.setValue(EReportInfo);

                    // after adding this data we are showing toast message.
                    // Toast.makeText(Farmer_Sign_up_Activity.this, "Registration Completed", Toast.LENGTH_SHORT).show();
                    Toast.makeText(FeedBack_sub_activity.this, "Submitted", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(FeedBack_sub_activity.this, "R error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(FeedBack_sub_activity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}