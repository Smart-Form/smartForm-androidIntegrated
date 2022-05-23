package com.example.applicantend_se_fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent; // buttonOnclickNewIntent
import android.os.Bundle;
import android.view.View; // buttonOnclickNewIntent
import android.view.Window; // removeTitleBar
import android.view.WindowManager; // removeTitleBar
import android.widget.Button; // buttonOnclickNewIntent
import android.widget.ImageButton;

public class language extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // keepScreenOn

        // removeTitleBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // removeTitleBar end

        // hideTheNavigationBar
        View decorView = getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // hideTheNavigationBar end

        setContentView(R.layout.activity_language);

        // buttonOnclickNewIntent 2
        Button btn = (Button)findViewById(R.id.eng);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(language.this, SelectTypeActivity.class));
            }
        });
        Button btn2 = (Button)findViewById(R.id.cht);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(language.this, language.class));
            }
        });
        // buttonOnclickNewIntent 2 end
    }
}