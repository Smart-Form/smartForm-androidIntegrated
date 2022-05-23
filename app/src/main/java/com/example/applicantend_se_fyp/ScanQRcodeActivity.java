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
        getSupportActionBar().hide();
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
        Intent intent = new Intent(ScanQRcodeActivity.this, SelectServiceActivity.class);
        intent.putExtra("result", result);
        startActivity(intent);
        //send result


        //openQRCamera();
    }
}
