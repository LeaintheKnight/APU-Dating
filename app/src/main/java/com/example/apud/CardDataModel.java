package com.example.apud;

public class CardDataModel {

    public int images;
    public String names;
    public String Occupation;
    public String location;

    public int getImages() {
        return images;
    }

    public String getNames() {
        return names;
    }

    public String getOccupation() {
        return Occupation;
    }

    public String getLocation() {
        return location;
    }

    public void setImages(int images) {
        this.images = images;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public void setOccupation(String occupation) {
        Occupation = occupation;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
