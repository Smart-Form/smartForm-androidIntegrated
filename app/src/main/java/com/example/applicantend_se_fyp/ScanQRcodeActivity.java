package com.example.applicantend_se_fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;


import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import java.io.File;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanQRcodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView zXingScannerView;
    String language = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();

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

        setContentView(R.layout.activity_scan_qrcode);

        language = getIntent().getStringExtra("language");

        zXingScannerView = findViewById(R.id.ZXingScannerView_QRCode);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ActivityCompat.checkSelfPermission(this
                        , Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    100);
        } else {

            openQRCamera();

        }
    }

    private void openQRCamera() {
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100 && grantResults[0] == 0) {
            openQRCamera();
        } else {
            Toast.makeText(this, "權限勒？", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        zXingScannerView.stopCamera();
        super.onStop();
    }

    @Override
    public void handleResult(Result rawResult) {

        String result = rawResult+"";

        // newView 2.2.2
        Intent intent = new Intent(ScanQRcodeActivity.this, select.class);
        intent.putExtra("result", result);
        startActivity(intent);
        //send result


        //openQRCamera();
    }
}
