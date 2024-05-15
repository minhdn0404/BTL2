package com.example.btl2.models;

public class Product {
    private String id;
    private String name, owner, image, description;
    private String auctionStartTime, auctionTime;
    private int startPrice, currentPrice, stepPrice;

    public Product() {
    }

    public Product(String id, String name, String owner, String image, String description, String auctionStartTime, String auctionTime, int startPrice, int currentPrice, int stepPrice) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.image = image;
        this.description = description;
        this.auctionStartTime = auctionStartTime;
        this.auctionTime = auctionTime;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getAuctionTime() {
        return auctionTime;
    }

    public void setAuctionTime(String auctionTime) {
        this.auctionTime = auctionTime;
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
}
