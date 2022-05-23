package com.example.applicantend_se_fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.LocaleList;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText; // changeKeyboardLanguage
import android.widget.TextView;

import java.util.Locale;

public class question_text extends AppCompatActivity {
    int nextPageID = 99; // Default
    Class nextPageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_question_text);

        // Here nextPageID is changed by data received from DB
        // ...
        // Find next page when new nextPageID is received from DB
        if (nextPageID == 0){
            nextPageName = question_binary.class;
        } else if (nextPageID == 1){
            nextPageName = question_date.class;
        } else if (nextPageID == 2){
            nextPageName = question_daterange.class;
        } else if (nextPageID == 3){
            nextPageName = question_mc.class;
        } else if (nextPageID == 4){
            nextPageName = question_option.class;
        } else if (nextPageID == 5){
            nextPageName = question_text.class;
        } else if (nextPageID == 6){
            nextPageName = question_signature.class;
        } else if (nextPageID == 7){
            nextPageName = bye.class;
        }
        // The page for testing only
        if (nextPageID == 99){
            nextPageName = question_option2.class;
        } else if (nextPageID == 98) {
            nextPageName = question_option3.class;
        }

        Button btn = (Button) findViewById(R.id.loading);
        btn.setEnabled(false);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                startActivity(new Intent(question_text.this, nextPageName));
            }
        });

        // changeKeyboardLanguage 2
        EditText editText = (EditText) findViewById(R.id.plain_text_input);
        editText.setImeHintLocales(new LocaleList(new Locale("en", "USA")));
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().trim().length() == 0) {
                    // set text to Not typing
                    //confirm.setText("Not Typing");
                } else {
                    // set text to typing
                    //confirm.setText(" Typing");
                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Enable NEXT QUESTION button
                btn.setEnabled(true);
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    //confirm.setText("Stopped Typing");
                }
            }
        });
        // changeKeyboardLanguage 2 end

        TextView backBtn = (TextView) findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                finish();
            }
        });
    }

    CountDownTimer countDownTimer = new CountDownTimer(600000, 1000) {
        public void onTick(long millisUntilFinished) {
            //TODO: Do something every second
        }
        public void onFinish() {
            countDownTimer.cancel();
            finish();
            System.exit(0); // Clear the memory
            startActivity(new Intent(question_text.this, language.class));
        }
    }.start();
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            countDownTimer.cancel();
            countDownTimer.start();
        }
        return super.onTouchEvent(event);
    }
}