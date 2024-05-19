package com.example.btl2.models;

public class NotiModel {
    int image;
    String headNoti;
    String noti;

    public NotiModel(int image, String headNoti, String noti) {
        this.image = image;
        this.headNoti = headNoti;
        this.noti = noti;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getHeadNoti() {
        return headNoti;
    }

    public void setHeadNoti(String headNoti) {
        this.headNoti = headNoti;
    }

    public String getNoti() {
        return noti;
    }

    public void setNoti(String noti) {
        this.noti = noti;
    }
}
