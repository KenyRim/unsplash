package com.appdev.unsplash.db;

public class ModelMain {
    private long id;
    private String pupDate, smallImageUrl, largeImageUrl;
    private int isChosen;

    public ModelMain(long id,String pupDate,String smallImageUrl,String largeImageUrl,int isChosen) {
        this.id = id;
        this.pupDate = pupDate;
        this.smallImageUrl = smallImageUrl;
        this.largeImageUrl = largeImageUrl;
        this.isChosen = isChosen;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPupDate() {
        return pupDate;
    }

    public void setPupDate(String pupDate) {
        this.pupDate = pupDate;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    public void setLargeImageUrl(String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
    }

    public int getIsChosen() {
        return isChosen;
    }

    public void setIsChosen(int isChosen) {
        this.isChosen = isChosen;
    }
}
