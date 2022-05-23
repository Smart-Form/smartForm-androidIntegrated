package com.example.applicantend_se_fyp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//just to hold the name and id for select. tmp obj.
public class Service implements Serializable {

    protected String s_name, s_id, s_age, s_ageUD, s_creatorID, s_dateEnd;
    protected String s_money, s_target, s_terms, s_dateStart, s_intro;
    protected String s_options, s_img, s_type;

    public Service(String s_name, String s_id, String s_age,
                           String s_ageUD, String s_creatorID,
                           String s_intro, String s_money,
                           String s_target, String s_terms,
                           String s_dateStart, String s_dateEnd,
                           String s_options, String s_img, String s_type
    ){
        this.s_name = s_name;
        this.s_id = s_id;
        this.s_age = s_age;
        this.s_ageUD = s_ageUD;
        this.s_creatorID = s_creatorID;
        this.s_intro = s_intro;
        this.s_money = s_money;
        this.s_target = s_target;
        this.s_terms = s_terms;
        this.s_dateStart = s_dateStart;
        this.s_dateEnd = s_dateEnd;
        this.s_options = s_options;
        this.s_img = s_img;
        this.s_type = s_type;
    }

    public String getS_id() {
        return s_id;
    }

    public String getS_name() {
        return s_name;
    }

    public String getS_dateEnd() {
        return s_dateEnd;
    }

    public String getS_dateStart() {
        return s_dateStart;
    }

    public String getS_age() {
        return s_age;
    }

    public String getS_ageUD() {
        return s_ageUD;
    }

    public String getS_creatorID() {
        return s_creatorID;
    }

    public String getS_intro() {
        return s_intro;
    }

    public String getS_money() {
        return s_money;
    }

    public String getS_target() {
        return s_target;
    }

    public String getS_terms() {
        return s_terms;
    }

    public String getS_options() {
        return s_options;
    }

    public String getS_img() {
        return s_img;
    }

    public String getS_type() {return s_type;}

    @Override
    public String toString() {
        return "Service{" +
                "s_name='" + s_name + '\'' +
                ", s_id='" + s_id + '\'' +
                ", s_age='" + s_age + '\'' +
                ", s_ageUD='" + s_ageUD + '\'' +
                ", s_creatorID='" + s_creatorID + '\'' +
                ", s_dateEnd='" + s_dateEnd + '\'' +
                ", s_money='" + s_money + '\'' +
                ", s_target='" + s_target + '\'' +
                ", s_terms='" + s_terms + '\'' +
                ", s_dateStart='" + s_dateStart + '\'' +
                ", s_intro='" + s_intro + '\'' +
                ", s_options='" + s_options + '\'' +
                ", s_img='" + s_img + '\'' +
                '}';
    }

    public String returnDetail() {
        String tmp = "";
        try {
            JSONObject jsonObj = new JSONObject(s_options);
            JSONArray options = jsonObj.getJSONArray("autoFillOptions");
            for (int i = 0; i < options.length(); i++) {
                JSONObject c = options.getJSONObject(i);
                tmp += c.getString("option") + " | ";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  "Service Name : "         + "\n" + s_name + "\n" + "\n" +
                "Introduction : "         + "\n" + s_intro + "\n" + "\n" +
                "Age : "                  + "\n" + s_age + " " + s_ageUD + "\n" + "\n" +
                "Start date : "           + "\n" + s_dateStart + "\n" + "\n" +
                "End date : "             + "\n" + s_dateEnd + "\n" + "\n" +
                "Amount : "               + "\n" + s_money + "\n" + "\n" +
                "Target Audience : "      + "\n" + s_target + "\n" + "\n" +
                "Terms : "                + "\n" + s_terms + "\n" + "\n" +
                "Auto fill in options : " + "\n" + tmp + "\n" + "\n" +
                "";
    }

    public ArrayList<String> getAutoFillOptions(){
        ArrayList<String> tmp_StrArrList = null;
        try {
            JSONObject jsonObj = new JSONObject(s_options);
            JSONArray options = jsonObj.getJSONArray("autoFillOptions");
            tmp_StrArrList = new ArrayList<String>();
            for (int i = 0; i < options.length(); i++) {
                JSONObject c = options.getJSONObject(i);
                tmp_StrArrList.add(c.getString("option"));
//                Log.d("TAG", c.getString("option"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tmp_StrArrList;
    }

}

//psuh