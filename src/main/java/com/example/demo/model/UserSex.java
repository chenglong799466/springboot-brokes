package com.example.demo.model;


public enum UserSex {
    MALE("MALE"),
    FEMALE("FEMALE");

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    UserSex(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }

}
