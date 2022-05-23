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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;

public class select extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Service> serviceList;

    Context context;
    Resources resources;
    Button btnBack;
    public static String personData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_select);






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

        // Service data ArrayList
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

        Button btn = (Button) findViewById(R.id.confirm_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checkIfAnyCheckBoxIsChecked 2
                boolean isChecked = false;

                outerloop:
                for (String size: arrayList2) {
                    int ID = getResources().getIdentifier(size, "id", getPackageName());
                    ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(ID);

                    for (int i = 0; i < constraintLayout.getChildCount(); i++) {
                        View element = constraintLayout.getChildAt(i);

                        if (element instanceof CardView) {
                            CardView cardView = (CardView) element;

                            for (int i2 = 0; i2 < cardView.getChildCount(); i2++) {
                                View element2 = cardView.getChildAt(i2);

                                if (element2 instanceof ConstraintLayout) {
                                    ConstraintLayout constraintLayout2 = (ConstraintLayout) element2;

                                    for (int i3 = 0; i3 < constraintLayout2.getChildCount(); i3++) {
                                        View element3 = constraintLayout2.getChildAt(i3);

                                        if (element3 instanceof CheckBox) {
                                            if (((CheckBox) element3).isChecked()) {
                                                isChecked = true;
                                                System.out.println("ok"); // Or log.t("TAG", "MSG")
                                                break outerloop; // Break out of nested loops
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                // checkIfAnyCheckBoxIsChecked 2 end

                // checkIfAnyCheckBoxIsChecked 3
                if (isChecked) {
                    countDownTimer.cancel();
                    startActivity(new Intent(select.this, ServiceDetailActivity.class));
                } else {
                    Toast.makeText(select.this, "Please select form(s)", Toast.LENGTH_SHORT).show();
                }
                // checkIfAnyCheckBoxIsChecked 3 end
            }
        });

        // checkTheSameCheckboxOnOtherTab 2

        // Array
        String[] arrayList_TabSuffix_raw = new String[] {
                "_all",
                "_gov",
                "_ngo",
                "_cha"
        };
        // Array List
        ArrayList<String> arrayList_TabSuffix = new ArrayList<>();
        // Adding an Array into an Array List
        arrayList_TabSuffix.addAll(Arrays.asList(arrayList_TabSuffix_raw));

        // Loop through all ConstraintLayout (Top Layouts)
        for (String size: arrayList2) {
            int ID = getResources().getIdentifier(size, "id", getPackageName());
            ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(ID);

            // Loop through all child elements of this ConstraintLayout
            for (int i = 0; i < constraintLayout.getChildCount(); i++) {
                View element = constraintLayout.getChildAt(i);

                // If a child element is a CardView
                if (element instanceof CardView) {
                    CardView cardView = (CardView) element;

                    // Loop through all child elements of this CardView
                    for (int i2 = 0; i2 < cardView.getChildCount(); i2++) {
                        View element2 = cardView.getChildAt(i2);

                        // If a child element is a ConstraintLayout
                        if (element2 instanceof ConstraintLayout) {
                            ConstraintLayout constraintLayout2 = (ConstraintLayout) element2;

                            // Loop through all child elements of this ConstraintLayout
                            for (int i3 = 0; i3 < constraintLayout2.getChildCount(); i3++) {
                                View element3 = constraintLayout2.getChildAt(i3);

                                // If a child element is a CheckBox
                                if (element3 instanceof CheckBox) {

                                    // Set a setOnCheckedChangeListener for this CheckBox
                                    ((CheckBox) element3).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                            String idString = buttonView.getResources().getResourceEntryName(buttonView.getId()); // Not using getId() only, to avoid retrieving a unique ID instead of a String name

                                            if (buttonView.isChecked()) { // Checked
                                                // Check all the same checkboxes for all tab (Top Layouts) by ID
                                                for (String size : arrayList2) { // Loop through all ConstraintLayout (Top Layouts)
                                                    // Set target ConstraintLayout (Top Layouts)
                                                    int targetLayoutID = getResources().getIdentifier(size, "id", getPackageName());
                                                    final ConstraintLayout targetLayout = (ConstraintLayout) findViewById(targetLayoutID);

                                                    for (String TabSuffix: arrayList_TabSuffix) {
                                                        // Remove character from idString
                                                        // Creating a constructor of StringBuffer class
                                                        StringBuffer idString_removedLast4Char = new StringBuffer(idString);
                                                        // Invoking the method
                                                        for (int i=0; i<4; i++) { // How many times equals how many characters will be removed
                                                            idString_removedLast4Char.deleteCharAt(idString_removedLast4Char.length() - 1); // Remove last character
                                                        }

                                                        // Set target CheckBox (View) ID
                                                        int targetViewID = targetLayout.getResources().getIdentifier(idString_removedLast4Char + TabSuffix, "id", getPackageName());

                                                        if (targetViewID != 0) { // If we successfully get the targetViewID
                                                            // Set target CheckBox (View)
                                                            CheckBox targetCheckbox = (CheckBox) targetLayout.findViewById(targetViewID);

                                                            if (targetCheckbox != null) { // If we successfully get the targetViewID
                                                                targetCheckbox.setChecked(true);
                                                            }
                                                        }
                                                    }
                                                }
                                            } else { // Not checked
                                                // Uncheck all the same checkboxes for all tab (Top Layouts) by ID
                                                for (String size : arrayList2) { // Loop through all ConstraintLayout (Top Layouts)
                                                    // Set target ConstraintLayout (Top Layouts)
                                                    int targetLayoutID = getResources().getIdentifier(size, "id", getPackageName());
                                                    final ConstraintLayout targetLayout = (ConstraintLayout) findViewById(targetLayoutID);

                                                    for (String TabSuffix: arrayList_TabSuffix) {
                                                        // Remove character from idString
                                                        // Creating a constructor of StringBuffer class
                                                        StringBuffer idString_removedLast4Char = new StringBuffer(idString);
                                                        // Invoking the method
                                                        for (int i=0; i<4; i++) { // How many times equals how many characters will be removed
                                                            idString_removedLast4Char.deleteCharAt(idString_removedLast4Char.length() - 1); // Remove last character
                                                        }

                                                        // Set target CheckBox (View) ID
                                                        int targetViewID = targetLayout.getResources().getIdentifier(idString_removedLast4Char + TabSuffix, "id", getPackageName());

                                                        if (targetViewID != 0) { // If we successfully get the targetViewID
                                                            // Set target CheckBox (View)
                                                            CheckBox targetCheckbox = (CheckBox) targetLayout.findViewById(targetViewID);

                                                            if (targetCheckbox != null) { // If we successfully get the targetViewID
                                                                targetCheckbox.setChecked(false);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    }
                }
            }
        }
        // checkTheSameCheckboxOnOtherTab 2 end

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

                        otherBtns.setBackgroundTintList(ContextCompat.getColorStateList(select.this, R.color.disableColor));
                        otherBtns.setTextColor(ContextCompat.getColorStateList(select.this, R.color.secondaryTextColor));
                    }

                    // Set color to the selected tab (button) only
                    thisBtn.setBackgroundTintList(ContextCompat.getColorStateList(select.this, R.color.secondaryColor));
                    thisBtn.setTextColor(ContextCompat.getColorStateList(select.this, R.color.primaryTextColor));

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
            startActivity(new Intent(select.this, language.class));
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