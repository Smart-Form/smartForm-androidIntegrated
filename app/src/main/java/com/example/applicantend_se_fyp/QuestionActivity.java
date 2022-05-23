package com.example.applicantend_se_fyp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class QuestionActivity extends AppCompatActivity {

    public static int REQUEST_CODE = 1;
    ArrayList<Question> questionList;
    String data_jsonString = "";
    JSONObject cardData;
    String cardID = "";
    String service_doc_id;
    String autoFillWithAns;

    TextView textView;
    TextView textView_show;
    EditText editText;
    Button button;
    RadioGroup radioGroup;
    RadioButton radio_y, radio_n;

    RadioGroup radio_group_mc;
    RadioButton radio_o1, radio_o2, radio_o3, radio_o4;

    ArrayList<String> autoFillOptions;
    ArrayList<Answer> answerArrayList = new ArrayList<>();

    Question theQuestion;


    Context context;
    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_question);

        String language = ((GlobalVariable) this.getApplication()).getLanguage();
        context = LocaleHelper.setLocale(this, language);
        resources = context.getResources();

        questionList = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Service service = (Service) getIntent().getSerializableExtra("service");
        data_jsonString = getIntent().getStringExtra("personData");

        String show_card = "";

        try {
            cardData = new JSONObject(data_jsonString);
//            show_card += "Your ID card info.";
//            show_card += "name : " + cardData.getString("chname") + "\n";
//            show_card += "id : " + cardData.getString("id") + "\n";
//            show_card += "brithday : " + cardData.getString("brithday") + "\n";
//            show_card += "eng name : " + cardData.getString("engname") + "\n";
//            show_card += "gender : " + cardData.getString("gender") + "\n";
//            cardID = cardData.getString("id");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("TAG", "show_craad" + show_card);

        textView = findViewById(R.id.tv_Question);
        //textView_show = findViewById(R.id.tv_showAPI);
        editText = findViewById(R.id.et_Ans);
        button = findViewById(R.id.btn_next);
        radioGroup = findViewById(R.id.radio_group);
        radio_y = findViewById(R.id.radio_ys);
        radio_n = findViewById(R.id.radio_no);

        radio_group_mc = findViewById(R.id.radio_group_mc);
        radio_o1 = findViewById(R.id.radio_o1);
        radio_o2 = findViewById(R.id.radio_o2);
        radio_o3 = findViewById(R.id.radio_o3);
        radio_o4 = findViewById(R.id.radio_o4);

        button.setText(resources.getString(R.string.next_question));


        //textView_show.setText(show_card);

        service_doc_id = service.getS_id();
        autoFillOptions = service.getAutoFillOptions();


        autoFillWithAns = "{autoFillWithAns:[";

        try {

            if(!autoFillOptions.isEmpty()){
                for(int i = 0; i < autoFillOptions.size(); i++){

                    Log.d("TAG", "arrList :" + autoFillOptions.get(i));

                    if((autoFillOptions.get(i)).equals("hkid")){
                        autoFillWithAns += "{'" + autoFillOptions.get(i) + "':";
                        autoFillWithAns += "'" + cardData.getString("id") + "'}";
                        Log.d("TAG", "o hkid");
                    }
                    else if((autoFillOptions.get(i)).equals("engName")){
                        autoFillWithAns += "{'" + autoFillOptions.get(i) + "':";
                        autoFillWithAns += "'" + cardData.getString("engname") + "'}";
                        Log.d("TAG", "o eng name");
                    }
                    else if((autoFillOptions.get(i)).equals("chName")){
                        autoFillWithAns += "{'" + autoFillOptions.get(i) + "':";
                        autoFillWithAns += "'" + cardData.getString("chname") + "'}";
                        Log.d("TAG", "o ch name");
                    }
                    else if((autoFillOptions.get(i)).equals("birthday")){
                        autoFillWithAns += "{'" + autoFillOptions.get(i) + "':";
                        autoFillWithAns += "'" + cardData.getString("brithday") + "'}";
                    }
                    else if((autoFillOptions.get(i)).equals("gender")){
                        autoFillWithAns += "{'" + autoFillOptions.get(i) + "':";
                        autoFillWithAns += "'" + cardData.getString("gender") + "'}";
                    }

                    if(i < autoFillOptions.size()){
                        autoFillWithAns += ",";
                    }

                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        autoFillWithAns += "]}";

        Log.d("TAG", autoFillWithAns);

        db.collection("service").document(service_doc_id).collection("questions")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("TAG", document.getId() + " => " + document.getData());
                                Question tmp;
                                if(document.getString("type") == "mc"){
                                    tmp = new Question(
                                            document.getId(),
                                            Integer.parseInt(document.getString("index")),
                                            document.getString("question"),
                                            document.getString("description"),
                                            document.getString("type"),
                                            document.getString("mcOption")
                                    );
                                } else {
                                    tmp = new Question(
                                            document.getId(),
                                            Integer.parseInt(document.getString("index")),
                                            document.getString("question"),
                                            document.getString("description"),
                                            document.getString("type")
                                    );
                                }

                                questionList.add(tmp);
                            }

                            Collections.sort(questionList);

                            //Object[] qArr = new Question[questionList.size()];
                            //qArr = questionList.toArray();

                            //------------------------------------
                            Question tmp_firstQ = questionList.get(0);
                            questionList.remove(0);

                            theQuestion = tmp_firstQ;

                            if(tmp_firstQ.getType().equals("yORn")){
                                radioGroup.setVisibility(View.VISIBLE);
                                //editText.setText(cardID);
                            }
                            else if(tmp_firstQ.getType().equals("shortAns")){
                                editText.setVisibility(View.VISIBLE);
                            }
                            else if(tmp_firstQ.getType().equals("mc")){
                                String tmp_mc_option = tmp_firstQ.getMc_option();
                                StringTokenizer st = new StringTokenizer(tmp_mc_option,"|");

                                for(int i=0; i<4; i++){
                                    if(i == 0){
                                        radio_o1.setText(st.nextToken());
                                    }
                                    else if(i == 1){
                                        radio_o2.setText(st.nextToken());
                                    }
                                    else if(i == 2){
                                        radio_o3.setText(st.nextToken());
                                    }
                                    else if(i == 3){
                                        radio_o4.setText(st.nextToken());
                                    }
                                }

                                radio_group_mc.setVisibility(View.VISIBLE);
                            }

                            textView.setText(tmp_firstQ.questionSelf);
                            //------------------------------------


                            //Log.d("TAG", questionList.toString());

                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    public void onClickNext(View view) {

        questionIteration();

    }

    public void questionIteration(){

        String flag = "allGood";

        Answer answer = null;

        if(editText.getVisibility() == View.VISIBLE){
            editText.setVisibility(View.INVISIBLE);
            answer = new Answer(theQuestion.getQ_id(),
                    theQuestion.getIndex(),
                    editText.getText().toString());
        }
        else if(radioGroup.getVisibility() == View.VISIBLE){
            radioGroup.setVisibility(View.VISIBLE);
            if(radio_y.isChecked()){
                answer = new Answer(theQuestion.getQ_id(),
                        theQuestion.getIndex(),
                        "Yes");

            }
            else if(radio_n.isChecked()) {
                answer = new Answer(theQuestion.getQ_id(),
                        theQuestion.getIndex(),
                        "No");
            }
            else {
                flag = "unSelect";
            }

        }
        else if(radio_group_mc.getVisibility() == View.VISIBLE){
            if(radio_o1.isChecked()){
                answer = new Answer(theQuestion.getQ_id(),
                        theQuestion.getIndex(),
                        ((String)radio_o1.getText()));
            }
            else if(radio_o2.isChecked()){
                answer = new Answer(theQuestion.getQ_id(),
                        theQuestion.getIndex(),
                        ((String)radio_o2.getText()));
            }
            else if(radio_o3.isChecked()){
                answer = new Answer(theQuestion.getQ_id(),
                        theQuestion.getIndex(),
                        ((String)radio_o3.getText()));
            }
            else if(radio_o4.isChecked()){
                answer = new Answer(theQuestion.getQ_id(),
                        theQuestion.getIndex(),
                        ((String)radio_o4.getText()));
            }
            else {
                flag = "unSelect";
            }
        }

        if(flag == "unSelect"){
            Log.d("TAG","radio unSelect");
        }
        else{

            Log.d("TAG","Next Question");

            textView.setText("");
            editText.setText("");
            radioGroup.clearCheck();
            radio_group_mc.clearCheck();

            answerArrayList.add(answer);

            if(!questionList.isEmpty()){

                theQuestion = null;

                Question tmp = questionList.get(0);
                theQuestion = tmp;

                if(tmp.getType().equals("yORn")){
                    radioGroup.setVisibility(View.VISIBLE);
                    //editText.setText(cardID);
                }
                else if(tmp.getType().equals("shortAns")){
                    editText.setVisibility(View.VISIBLE);
                }
                else if(tmp.getType().equals("mc")){
                    String tmp_mc_option = tmp.getMc_option();
                    StringTokenizer st = new StringTokenizer(tmp_mc_option,"|");

                    for(int i=0; i<4; i++){
                        if(i == 0){
                            radio_o1.setText(st.nextToken());
                        }
                        else if(i == 1){
                            radio_o2.setText(st.nextToken());
                        }
                        else if(i == 2){
                            radio_o3.setText(st.nextToken());
                        }
                        else if(i == 3){
                            radio_o4.setText(st.nextToken());
                        }
                    }

                    radio_group_mc.setVisibility(View.VISIBLE);
                }

                textView.setText(tmp.getQuestionSelf());

                questionList.remove(0);

            }
            else {

                //just to confirm the answers
                String tmp_asdasd = "";
                for(int i=0; i < answerArrayList.size(); i++){
                    Answer ans_tmp = answerArrayList.get(i);
                    tmp_asdasd += ans_tmp.getqID() + "," + ans_tmp.qIndex + "," + ans_tmp.getAns() + " | ";
                }
                Log.d("TAG", tmp_asdasd);
                //----------------------------

                //create Response Obj
                Response responseObj;

                //pack the ansList into the response obj
                responseObj = new Response(answerArrayList, service_doc_id, autoFillWithAns);

                Log.d("TAG", responseObj.toString());

                //Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();

                // newView 8
                Intent intent = new Intent(QuestionActivity.this, SubmitAnsActivity.class);
                intent.putExtra("responsePack", responseObj);
                startActivity(intent);

            }

        }

    }


}

//psuh