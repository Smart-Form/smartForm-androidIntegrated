package com.example.applicantend_se_fyp;

import android.app.Application;

public class GlobalVariable extends Application {
    private String language = "en";

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
