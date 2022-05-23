package com.example.applicantend_se_fyp;

import java.io.Serializable;
import java.util.ArrayList;

public class Response implements Serializable {

    ArrayList<Answer> ansList;
    String serviceID;
    String autoFillWithAns_json;

    public Response(){

    }

    public Response(ArrayList<Answer> ansList, String serviceID, String autoFillWithAns_json){
        this.ansList = ansList;
        this.serviceID = serviceID;
        this.autoFillWithAns_json = autoFillWithAns_json;
    }

    public ArrayList<Answer> getAnsList() {
        return ansList;
    }

    public String getServiceID() {
        return serviceID;
    }

    public String getAutoFillOption_json() {
        return autoFillWithAns_json;
    }

    @Override
    public String toString() {
        return "Response{" +
                "ansList=" + ansList +
                ", serviceID='" + serviceID + '\'' +
                ", autoFillWithAns_json='" + autoFillWithAns_json + '\'' +
                '}';
    }
}


//psuh