package com.example.applicantend_se_fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

public class ServiceDetailActivity extends AppCompatActivity {

    TextView textView_serviceDetail;
    Button button_apply;

    String service_doc_id;
    String personData;
    Service service;

    GlobalVariable gv = ((GlobalVariable) this.getApplication());
    Context context;
    Resources resources;
    Button btm_apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_service_detail);

//        String language = ((GlobalVariable) this.getApplication()).getLanguage();
//        context = LocaleHelper.setLocale(this, language);
//        resources = context.getResources();

        service = (Service) getIntent().getSerializableExtra("service_obj");
        personData = getIntent().getStringExtra("personData");

        textView_serviceDetail = findViewById(R.id.tv_serviceDetail);
        button_apply = findViewById(R.id.btn_form);

        //button_apply.setText(resources.getString(R.string.apply));

        textView_serviceDetail.setText(service.returnDetail());

        service_doc_id = service.getS_id();

    }

    public void onClickApply(View v){

        // newView 5
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra("service", service);
        Log.d("TAG", "serviceDetialpage" + personData);
        intent.putExtra("personData", personData);
        startActivity(intent);
    }

}

//psuh