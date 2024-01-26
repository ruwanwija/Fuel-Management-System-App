package com.example.checkfuel.classes;

public class User {

    private String username,email,phone,key;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public User(String key, String username,String email,String phone) {
        this.key = key;
        this.username = username;
        this.email = email;
        this.phone = phone;
    }
}
