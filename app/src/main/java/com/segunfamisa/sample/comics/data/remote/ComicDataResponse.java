package com.segunfamisa.sample.comics.data.remote;

import com.google.gson.annotations.SerializedName;

/**
 * Comic data response from the API call.
 */
public class ComicDataResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("code")
    private int code;

    @SerializedName("data")
    private ComicData data;


    public String getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public ComicData getData() {
        return data;
    }
}
