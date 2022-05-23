package com.example.applicantend_se_fyp;

import static com.example.applicantend_se_fyp.SelectTypeActivity.language;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SelectServiceActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Service> serviceList;

    Context context;
    Resources resources;
    Button btnBack;
    public static String personData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_select_service);

        String language = ((GlobalVariable) this.getApplication()).getLanguage();
        context = LocaleHelper.setLocale(this, language);
        resources = context.getResources();

        personData = getIntent().getStringExtra("result");

        // UI
        btnBack = findViewById(R.id.btnBack);
        recyclerView = findViewById(R.id.recyclerView_service);
        btnBack.setText(resources.getString(R.string.back));

        // Firebase
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        serviceList = new ArrayList<>();

        // newView 3
        RecyclerViewAdapter rvAdapter = new RecyclerViewAdapter(this, serviceList);
        recyclerView.setAdapter(rvAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
                                serviceList.add(tmp);
                            }
                            rvAdapter.notifyDataSetChanged();
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    public void onClick_Back(View view) {

        Intent intent = new Intent(this, SelectTypeActivity.class);
        startActivity(intent);
    }
}

//psuh