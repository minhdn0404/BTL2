package com.example.btl2.models;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private String id;
    private String name, owner, description;
    private ArrayList<Bitmap> image;
    private String auctionStartTime, auctionEndTime;
    private int startPrice, currentPrice, stepPrice;

    public Product() {
    }

    public Product(String id, String name, String owner, String description, ArrayList<Bitmap> image, String auctionStartTime, String auctionEndTime, int startPrice, int currentPrice, int stepPrice) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.image = image;
        this.auctionStartTime = auctionStartTime;
        this.auctionEndTime = auctionEndTime;
        this.startPrice = startPrice;
        this.currentPrice = currentPrice;
        this.stepPrice = stepPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public ArrayList<Bitmap> getImage() {
        return image;
    }
    public void setImage(List<Bitmap> image) {
        this.image = (ArrayList<Bitmap>) image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuctionStartTime() {
        return auctionStartTime;
    }

    public void setAuctionStartTime(String auctionStartTime) {
        this.auctionStartTime = auctionStartTime;
    }

    public String getAuctionEndTime() {
        return auctionEndTime;
    }

    public void setAuctionEndTime(String auctionEndTime) {
        this.auctionEndTime = auctionEndTime;
    }

    public int getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getStepPrice() {
        return stepPrice;
    }

    public void setStepPrice(int stepPrice) {
        this.stepPrice = stepPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", description='" + description + '\'' +
                ", image=" + image +
                ", auctionStartTime='" + auctionStartTime + '\'' +
                ", auctionEndTime='" + auctionEndTime + '\'' +
                ", startPrice=" + startPrice +
                ", currentPrice=" + currentPrice +
                ", stepPrice=" + stepPrice +
                '}';
    }
}
