package com.example.checkfuel.classes;

public class Feedbacks {

    private String text,key;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Feedbacks(String key, String text) {
        this.key = key;
        this.text = text;
    }
}
