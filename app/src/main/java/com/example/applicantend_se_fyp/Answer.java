package com.example.applicantend_se_fyp;

import java.io.Serializable;

public class Answer implements Serializable {

    String qID, ans;
    int qIndex;

    public Answer(String qID, int qIndex, String ans){
        this.qID = qID;
        this.qIndex = qIndex;
        this.ans = ans;
    }

    public int getqIndex() {
        return qIndex;
    }

    public String getAns() {
        return ans;
    }

    public String getqID() {
        return qID;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "qID='" + qID + '\'' +
                ", ans='" + ans + '\'' +
                ", qIndex=" + qIndex +
                '}';
    }
}


//psuh