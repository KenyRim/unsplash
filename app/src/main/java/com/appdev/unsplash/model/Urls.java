package com.appdev.unsplash.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Urls {

    @SerializedName("full")
    @Expose
    private String full;

    @SerializedName("small")
    @Expose
    private String small;

    /**
     * 
     * @return
     *     The full
     */
    public String getFull() {
        return full;
    }


    /**
     * 
     * @return
     *     The small
     */
    public String getSmall() {
        return small;
    }

}