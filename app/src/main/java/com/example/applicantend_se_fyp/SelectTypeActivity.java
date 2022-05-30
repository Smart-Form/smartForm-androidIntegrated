package com.example.applicantend_se_fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.load.engine.Resource;

import java.util.Locale;

import io.grpc.internal.SharedResourceHolder;

public class SelectTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_select_type);

        Button btn_genQR = (Button) findViewById(R.id.btnGenQR);
        Button btn_scanQR = (Button) findViewById(R.id.btnScanQR);

        // changeLang
//        String language = (String) Locale.getDefault().getLanguage(); // Method 1: If matches system setting
//        Log.d("TAG", language);
        String language = (String) ((GlobalVariable) getApplication()).getLanguage(); // Method 2: If matches GlobalVariable
        Log.d("TAG", language);

        Context context = (Context) LocaleHelper.setLocale(this, language);
        Resources resources = (Resources) context.getResources();
        btn_scanQR.setText(resources.getString(R.string.scan_qr_code));
        btn_genQR.setText(resources.getString(R.string.generate_qr_code));
        // changeLang end
    }

    //scan ID card to gen QR code
    public void onClick_GenQR(View view) {
        // TMP : thisActivity -> GetAPIActivity
        // CAM : thisActivity -> camera -> ...
        // Lucifer

        // newView 2.1.1
        Intent intent = new Intent(this, id_card.class);

        startActivity(intent);
    }

    //scan QR code to get person's data.
    public void onClick_ScanQR(View view) {
        // Lucifer

        //Intent intent = new Intent(this, XXXX.class);
        //startActivity(intent);

        // newView 2.2.1
        Intent intent = new Intent(this, ScanQRcodeActivity.class);

        startActivity(intent);
    }
}
