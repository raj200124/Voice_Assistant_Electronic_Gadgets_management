package com.example.voice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    TextView mTextTv;
    ImageButton mVoiceBtn;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    int c=0;
    String string;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("object");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextTv = findViewById(R.id.textTv);
        mVoiceBtn = findViewById(R.id.voiceBtn);
        mVoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });
    }

    private void speak() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Now...");
        try {
            startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT);
        }
        catch (Exception e)
        {
            Toast.makeText(this,"+e.getMessage()" , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_SPEECH_INPUT : {
                if (resultCode == RESULT_OK && null!=data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mTextTv.setText(result.get(0));
                    c=0;
                    if(result.get(0).contains("on"))
                    {
                        c++;
                        if(result.get(0).contains("light"))
                        {
                            string = "light on";
                            c++;
                        }
                        if(result.get(0).contains("fan"))
                        {
                            string = "fan on";
                            c++;
                        }
                        if(result.get(0).contains("refrigerator"))
                        {
                            string = "refrigerator on";
                            c++;
                        }
                        if(result.get(0).contains("AC"))
                        {
                            string = "AC on";
                            c++;
                        }
                    }

                    if(result.get(0).contains("off"))
                    {
                        c++;
                        if(result.get(0).contains("light"))
                        {
                            string = "light off";
                            c++;
                        }
                        if(result.get(0).contains("fan"))
                        {
                            string = "fan off";
                            c++;
                        }
                        if(result.get(0).contains("refrigerator"))
                        {
                            string = "refrigerator off";
                            c++;
                        }
                        if(result.get(0).contains("AC"))
                        {
                            string = "AC off";
                            c++;
                        }
                    }

                    if (c!=2) {
                        mTextTv.setText("Please give right command");
                    }
                    else
                        myRef.setValue(string);
                }
                break;
            }

        }
    }

}
