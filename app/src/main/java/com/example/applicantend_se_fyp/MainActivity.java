package com.example.applicantend_se_fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
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

    //RecyclerView recyclerView;
    //ArrayList<Service> serviceList;
    ImageButton btn_start;
    String[] permissions ={
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);


        btn_start = (ImageButton) findViewById(R.id.btn_start);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // newView
                Intent intent = new Intent(MainActivity.this, SelectTypeActivity.class);
                startActivity(intent);
            }
        });

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


}

//psuh