package com.example.applicantend_se_fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ScrollView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;

//
//
//
// This page is merged into "select.java" and will never be used
//
//
//

public class SelectServiceActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView recyclerView_gov;
    RecyclerView recyclerView_ngo;
    RecyclerView recyclerView_cha;
    ArrayList<Service> serviceList;

    public static String personData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_select_service);






        personData = getIntent().getStringExtra("result");

        // UI
        recyclerView = findViewById(R.id.recyclerView_service);
        recyclerView_gov = findViewById(R.id.recyclerView_service_gov);
        recyclerView_ngo = findViewById(R.id.recyclerView_service_ngo);
        recyclerView_cha = findViewById(R.id.recyclerView_service_cha);

        // Firebase
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Service data ArrayList
        serviceList = new ArrayList<>();

        // newView 3
        RecyclerViewAdapter rvAdapter = new RecyclerViewAdapter(this, serviceList);
        recyclerView.setAdapter(rvAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_gov.setAdapter(rvAdapter);
        recyclerView_gov.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_ngo.setAdapter(rvAdapter);
        recyclerView_ngo.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_cha.setAdapter(rvAdapter);
        recyclerView_cha.setLayoutManager(new LinearLayoutManager(this));

        // Firebase
        db.collection("service")
                .whereEqualTo("status", "verified")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("TAG", document.getId() + " => " + document.getString("serviceName"));
                                    Service tmp = new Service(
                                            document.getString("serviceName"),
                                            document.getId(),
                                            document.getString("age"),
                                            document.getString("ageUpDown"),
                                            document.getString("creatorID"),
                                            document.getString("introduction"),
                                            document.getString("money"),
                                            document.getString("targetAudience"),
                                            document.getString("terms"),
                                            document.getString("dateStart"),
                                            document.getString("dateEnd"),
                                            document.getString("autoFillOptions"),
                                            document.getString("posterImg"),
                                            document.getString("type")
                                    );
                                // Insert into service data ArrayList
                                serviceList.add(tmp);
                            }
                            rvAdapter.notifyDataSetChanged();
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });







        // checkTheSameCheckboxOnOtherTab
        // checkIfAnyCheckBoxIsChecked
        //ArrayList<String> arrayList = new ArrayList<>();
        //arrayList.add("anArrayItem");
        //arrayList.addAll(arrayList2);
        //arrayList.remove("anArrayItem");
        //arrayList.removeAll(arrayList2);
        //arrayList.clear();
        //arrayList.get(0) // Return "anArrayItem"
        //int a = arrayList.indexOf("anArrayItem"); // Return 0
        //int i = arrayList.size();
        //boolean b = arrayList.contains("anArrayItem"); // b = true
        //boolean empty = arrayList.isEmpty(); // empty = false
        // Array
        String[] arrayList = new String[] {
                "constraintLayout_all",
                "constraintLayout_gov",
                "constraintLayout_ngo",
                "constraintLayout_cha"
        };
        // Array List
        ArrayList<String> arrayList2 = new ArrayList<>();
        // Adding an Array into an Array List
        arrayList2.addAll(Arrays.asList(arrayList));
        // checkIfAnyCheckBoxIsChecked end
        // checkTheSameCheckboxOnOtherTab end







        // onChangeTabButtonsColor
        int total_resource = 4;
        // Set how many buttons there are
        for (int i=0; i<total_resource; i++) {
            String category = "";
            if (i == 0){
                category = "all";
            } else if (i == 1){
                category = "gov";
            } else if (i == 2){
                category = "ngo";
            } else if (i == 3){
                category = "cha";
            }

            int id_btn = getResources().getIdentifier(category + "_form", "id", getPackageName());
            Button thisBtn = (Button) findViewById(id_btn);
            int id_form = getResources().getIdentifier("scroll_view_" + category, "id", getPackageName());
            ScrollView thisForm = (ScrollView) findViewById(id_form);

            // Set onClick action to the button
            thisBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Set disableColor to all buttons
                    for (int i=0; i<total_resource; i++) {
                        String category = "";
                        if (i == 0){
                            category = "all";
                        } else if (i == 1){
                            category = "gov";
                        } else if (i == 2){
                            category = "ngo";
                        } else if (i == 3){
                            category = "cha";
                        }

                        int id_btn = getResources().getIdentifier(category + "_form", "id", getPackageName());
                        Button otherBtns = (Button) findViewById(id_btn);

                        otherBtns.setBackgroundTintList(ContextCompat.getColorStateList(SelectServiceActivity.this, R.color.disableColor));
                        otherBtns.setTextColor(ContextCompat.getColorStateList(SelectServiceActivity.this, R.color.secondaryTextColor));
                    }

                    // Set color to the selected tab (button) only
                    thisBtn.setBackgroundTintList(ContextCompat.getColorStateList(SelectServiceActivity.this, R.color.secondaryColor));
                    thisBtn.setTextColor(ContextCompat.getColorStateList(SelectServiceActivity.this, R.color.primaryTextColor));

                    // Set INVISIBLE to all layouts
                    for (int i=0; i<total_resource; i++) {
                        String category = "";
                        if (i == 0){
                            category = "all";
                        } else if (i == 1){
                            category = "gov";
                        } else if (i == 2){
                            category = "ngo";
                        } else if (i == 3){
                            category = "cha";
                        }

                        int id_form = getResources().getIdentifier("scroll_view_" + category, "id", getPackageName());
                        ScrollView otherForms = (ScrollView) findViewById(id_form);

                        otherForms.setVisibility(View.INVISIBLE);
                    }

                    // Set VISIBLE to the selected tab (layout) only
                    thisForm.setVisibility(View.VISIBLE);
                }
            });
        }
        // onChangeTabButtonsColor end
    }







    CountDownTimer countDownTimer = new CountDownTimer(600000, 1000) {
        public void onTick(long millisUntilFinished) {
            //TODO: Do something every second
        }
        public void onFinish() {
            countDownTimer.cancel();
            finish();
            System.exit(0); // Clear the memory
            startActivity(new Intent(SelectServiceActivity.this, language.class));
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