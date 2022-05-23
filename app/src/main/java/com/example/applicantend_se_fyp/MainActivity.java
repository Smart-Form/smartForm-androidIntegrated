package com.example.applicantend_se_fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

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

        setContentView(R.layout.activity_main);

        // buttonOnclickNewIntent 2
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                finish();

                // newView
                startActivity(new Intent(MainActivity.this, SelectTypeActivity.class));
            }
        });
        // buttonOnclickNewIntent 2 end

        // backBTN 2
        TextView backBtn = (TextView) findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                finish();

                // newView
                startActivity(new Intent(MainActivity.this, language.class));
            }
        });
        // backBTN 2 end
    }

    CountDownTimer countDownTimer = new CountDownTimer(600000, 1000) {
        public void onTick(long millisUntilFinished) {
            //TODO: Do something every second
        }
        public void onFinish() {
            countDownTimer.cancel();
            finish();
            System.exit(0); // Clear the memory
            startActivity(new Intent(MainActivity.this, language.class));
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

//        Intent intent = new Intent(this, GetAPIActivity.class);
//        startActivity(intent);

//        recyclerView = findViewById(R.id.recyclerView_service);
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//        serviceList = new ArrayList<>();
//
//        RecyclerViewAdapter rvAdapter = new RecyclerViewAdapter(this, serviceList);
//        recyclerView.setAdapter(rvAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        db.collection("service")
//                .whereEqualTo("status", "verified")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                //Log.d("TAG", document.getId() + " => " + document.getString("serviceName"));
//                                Service tmp = new Service(
//                                        document.getString("serviceName"),
//                                        document.getId(),
//                                        document.getString("age"),
//                                        document.getString("ageUpDown"),
//                                        document.getString("creatorID"),
//                                        document.getString("introduction"),
//                                        document.getString("money"),
//                                        document.getString("targetAudience"),
//                                        document.getString("terms"),
//                                        document.getString("dateStart"),
//                                        document.getString("dateEnd")
//                                );
//                                serviceList.add(tmp);
//                            }
//                            rvAdapter.notifyDataSetChanged();
//                        } else {
//                            Log.d("TAG", "Error getting documents: ", task.getException());
//                        }
//                    }
//                });

}