package com.example.applicantend_se_fyp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class history extends AppCompatActivity {
    int nextPageID = 0; // Default
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

        setContentView(R.layout.activity_history);

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

        Button btn = (Button) findViewById(R.id.confirm_button);
        btn.setEnabled(false);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                startActivity(new Intent(history.this, nextPageName));
            }
        });

        int total_resource = 2;
        for (int i=0; i<total_resource; i++) {
            // Retrieve ID dynamically
            int id = getResources().getIdentifier("answer_binary"+i, "id", getPackageName());
            Button thisObj = (Button) findViewById(id); // Get the element

            // Set event to the element
            thisObj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (thisObj.getTag() == null) {
                        // Disable all options
                        for (int i=0; i<total_resource; i++) {
                            int id = getResources().getIdentifier("answer_binary"+i, "id", getPackageName());
                            Button obj = (Button) findViewById(id);
                            obj.setBackgroundTintList(ContextCompat.getColorStateList(history.this, R.color.disableColor));
                            obj.setTextColor(ContextCompat.getColorStateList(history.this, R.color.secondaryTextColor));
                            obj.setTag(0);
                        }

                        // Color the clicked option
                        thisObj.setBackgroundTintList(ContextCompat.getColorStateList(history.this, R.color.secondaryColor));
                        thisObj.setTextColor(ContextCompat.getColorStateList(history.this, R.color.primaryTextColor));
                        thisObj.setTag(1);

                        // Enable NEXT QUESTION button
                        btn.setEnabled(true);
                    } else {
                        int tag = (Integer) thisObj.getTag();
                        if (tag == 0){
                            // Disable all options
                            for (int i=0; i<total_resource; i++) {
                                int id = getResources().getIdentifier("answer_binary"+i, "id", getPackageName());
                                Button obj = (Button) findViewById(id);
                                obj.setBackgroundTintList(ContextCompat.getColorStateList(history.this, R.color.disableColor));
                                obj.setTextColor(ContextCompat.getColorStateList(history.this, R.color.secondaryTextColor));
                                obj.setTag(0);
                            }

                            // Color the clicked option
                            thisObj.setBackgroundTintList(ContextCompat.getColorStateList(history.this, R.color.secondaryColor));
                            thisObj.setTextColor(ContextCompat.getColorStateList(history.this, R.color.primaryTextColor));
                            thisObj.setTag(1);
                        }
                    }
                }
            });
        }
    }

    CountDownTimer countDownTimer = new CountDownTimer(600000, 1000) {
        public void onTick(long millisUntilFinished) {
            //TODO: Do something every second
        }
        public void onFinish() {
            countDownTimer.cancel();
            finish();
            System.exit(0); // Clear the memory
            startActivity(new Intent(history.this, language.class));
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