package com.example.vquiz;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SupportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SupportFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    static TextToSpeech textToSpeech;
    Button bt;


    String sel_cmp = "";

    EditText et1;


    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String[] cmp_lst = {"Voice Detection", "Design", "Navigation", "Quiz Submission", "Others"};

    public SupportFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SupportFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SupportFragment newInstance(String param1, String param2) {
        SupportFragment fragment = new SupportFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root =  inflater.inflate(R.layout.fragment_support, container, false);

        bt = (Button) root.findViewById(R.id.sp_sub_bt);
        et1 = (EditText) root.findViewById(R.id.other_et);



        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String area = sel_cmp;
                String other_details = et1.getText().toString();

                if (area.equals("") || other_details.equals("")) {
                    Toast.makeText(getActivity(), "Please Fill all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(getActivity(), FeedBack_sub_activity.class);
                    i.putExtra("area", area);
                    i.putExtra("det", other_details);
                    startActivity(i);
                }
            }
        });

        Spinner sl = (Spinner) root.findViewById(R.id.spinner);
        sl.setOnItemSelectedListener(this);


        ArrayAdapter ad = new ArrayAdapter(getActivity(), R.layout.spinner_item, cmp_lst);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_item);

        sl.setAdapter(ad);

        String Speak = "You are now at Support screen";

        textToSpeech = new TextToSpeech(getActivity().getApplicationContext(), new TextToSpeech.OnInitListener() {
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

        // Inflate the layout for this fragment
        return root;
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        sel_cmp = cmp_lst[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}