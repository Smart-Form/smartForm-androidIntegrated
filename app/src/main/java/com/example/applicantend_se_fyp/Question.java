package com.example.applicantend_se_fyp;

public class Question implements Comparable{

    int index;
    String q_id, questionSelf, description, type, mc_option;

    public Question(String q_id, int index, String questionSelf, String description, String type){
        this.q_id = q_id;
        this.index = index;
        this.questionSelf = questionSelf;
        this.description = description;
        this.type = type;
    }

    public Question(String q_id, int index, String questionSelf, String description, String type, String mc_option){
        this.q_id = q_id;
        this.index = index;
        this.questionSelf = questionSelf;
        this.description = description;
        this.type = type;
        this.mc_option = mc_option;
    }

    public String getQ_id() {
        return q_id;
    }

    public int getIndex() {
        return index;
    }

    public String getDescription() {
        return description;
    }

    public String getQuestionSelf() {
        return questionSelf;
    }

    public String getType() {
        return type;
    }

    public String getMc_option() {
        return mc_option;
    }

    @Override
    public int compareTo(Object o) {
        int tmp = ((Question)o).getIndex();
        return this.index-tmp;
    }

    @Override
    public String toString() {
        return "Question{" +
                "index=" + index +
                ", q_id='" + q_id + '\'' +
                ", questionSelf='" + questionSelf + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}


//psuh