package com.example.lenovo.elapp;

public class Achievement_item {
    private String name;
    private int imageId;

    public Achievement_item(String name,int imageId){
        this.imageId = imageId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
