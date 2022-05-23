package com.example.applicantend_se_fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class SubmitAnsActivity extends AppCompatActivity {

    TextView tv_success, tv_countdown;
    Context context;
    Resources resources;
    GlobalVariable gv = ((GlobalVariable) this.getApplication());
    Button btn_backToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_submit_ans);

        String language = ((GlobalVariable) this.getApplication()).getLanguage();
        context = LocaleHelper.setLocale(this, language);
        resources = context.getResources();

        tv_success = findViewById(R.id.tv_success);
        tv_countdown = findViewById(R.id.tv_countdown);

        TimeZone tz = TimeZone.getTimeZone("GMT+05:30");
        Calendar c = Calendar.getInstance(tz);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = c.getTime();
        String currentDate = sdf.format(date);

        Response response_obj = (Response) getIntent().getSerializableExtra("responsePack");
        Log.d("TAG", "getAns");

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Add a new document with a generated id.
        Map<String, Object> data_response = new HashMap<>();

        data_response.put("serviceID", response_obj.getServiceID());
        data_response.put("autoFillWithAns", response_obj.getAutoFillOption_json());
        data_response.put("date", currentDate);

        db.collection("response")
                .add(data_response)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //Log.d("TAG", "DocumentSnapshot written with ID: " + documentReference.getId());
                        ArrayList<Answer> tmp_ansList = response_obj.getAnsList();
                        String response_doc_id = documentReference.getId();

                        Map<String, Object> data_ans;
                        Answer tmp_ans;
                        for(int i=0; i < tmp_ansList.size(); i++){
                            data_ans = new HashMap<>();
                            tmp_ans = tmp_ansList.get(i);
                            data_ans.put("questionID", tmp_ans.getqID());
                            data_ans.put("questionIndex", tmp_ans.getqIndex());
                            data_ans.put("ansContent", tmp_ans.getAns());

                            db.collection("response").document(response_doc_id).collection("answers")
                                    .add(data_ans)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            new CountDownTimer(5000, 1000) {

                                                public void onTick(long millisUntilFinished) {
                                                    tv_countdown.setText(resources.getString(R.string.backtohome) + millisUntilFinished / 1000);
                                                }

                                                public void onFinish() {
                                                    Intent intent = new Intent(SubmitAnsActivity.this, MainActivity.class);
                                                    startActivity(intent);
                                                }
                                            }.start();
                                            //Log.d("TAG", "DocumentSnapshot written in collection 'answers' with ID: " + documentReference.getId());
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("TAG", "Error adding document in answers", e);
                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
    }


    public void onClick_BackToHome(View view) {
        Intent intent = new Intent(SubmitAnsActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
//psuh