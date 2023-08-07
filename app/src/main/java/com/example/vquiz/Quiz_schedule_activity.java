package com.example.vquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;



import com.example.vquiz.databinding.ActivityQuizScheduleBinding;

public class Quiz_schedule_activity extends AppCompatActivity {

    ActivityQuizScheduleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        binding = ActivityQuizScheduleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int quiz_count = 1; //will decide frag

        try {
            if (quiz_count == 0 ) {
                replaceFragment(new No_schedule());
            } else {
                replaceFragment(new QuizListFragment());
            }

        } catch (Exception e) {
            Toast.makeText(Quiz_schedule_activity.this,"Fragment Error",Toast.LENGTH_SHORT).show();
        }

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Frg_2, fragment);
        fragmentTransaction.commit();
    }
}