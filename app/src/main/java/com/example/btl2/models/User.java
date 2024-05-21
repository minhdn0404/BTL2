package com.example.btl2.models;

import android.graphics.Bitmap;

public class User {

    private String id;
    private String username, phone, email, password;

    private Bitmap avatar;
    private boolean isAdmin;

    public User() {
    }

    public User(String username, String phone, String email, String password, Bitmap avatar, boolean isAdmin) {
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }
}
