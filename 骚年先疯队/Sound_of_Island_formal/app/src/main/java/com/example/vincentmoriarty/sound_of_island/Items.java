package com.example.vincentmoriarty.sound_of_island;

public class Items {
    private String name;
    private String description;
    private int imageId;
    private int image2Id;

    public Items(int imageId,String name, String description,int image2Id){
        this.imageId = imageId;
        this.name = name;
        this.description = description;
        this.image2Id = image2Id;
    }

    public String getName(){
        return name;
    }

    public int getImageId(){
        return imageId;
    }

    public String getDescription(){
        return description;
    }

    public int getImage2Id(){return image2Id;}
}
