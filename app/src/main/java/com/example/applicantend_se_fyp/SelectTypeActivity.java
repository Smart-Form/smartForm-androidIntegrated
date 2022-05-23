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

    Button btn_genQR, btn_scanQR, btn_changeLan;
    Context context;
    Resources resources;
    public static String language = "en";
    GlobalVariable gv = ((GlobalVariable) this.getApplication());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_select_type);

        ((GlobalVariable) this.getApplication()).setLanguage("en");

        btn_genQR = findViewById(R.id.btnGenQR);
        btn_scanQR = findViewById(R.id.btnScanQR);
        btn_changeLan = findViewById(R.id.btnChangeLan);

        //Log.d("TAG", "onCreate : " + Locale. getDefault(). getLanguage());
    }

    //scan ID card to gen QR code
    public void onClick_GenQR(View view) {
        // TMP : thisActivity -> GetAPIActivity
        // CAM : thisActivity -> camera -> ...
        // Lucifer

        // newView 2.1.1
        Intent intent = new Intent(this, cameraActivity.class);

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

    public void onClick_changeLan(View view) {
        if(Locale. getDefault().getLanguage() == "en"){
            language = "zh";
            ((GlobalVariable) this.getApplication()).setLanguage("zh");
        }
        else {
            language = "en";
            ((GlobalVariable) this.getApplication()).setLanguage("en");
        }
        context = LocaleHelper.setLocale(this, language);
        resources = context.getResources();
        btn_changeLan.setText(resources.getString(R.string.change_lan));
        btn_scanQR.setText(resources.getString(R.string.scan_qr_code));
        btn_genQR.setText(resources.getString(R.string.generate_qr_code));

        //Log.d("TAG", "btnClick : " + Locale. getDefault(). getLanguage());
    }


}
