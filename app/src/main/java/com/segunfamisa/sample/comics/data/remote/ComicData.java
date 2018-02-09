package com.segunfamisa.sample.comics.data.remote;

import com.google.gson.annotations.SerializedName;
import com.segunfamisa.sample.comics.data.model.Comic;

import java.util.List;

/**
 * Comic Data Content.
 */
public class ComicData {

    @SerializedName("count")
    private int count;

    @SerializedName("total")
    private int total;

    @SerializedName("results")
    private List<Comic> results;

    public int getCount() {
        return count;
    }

    public int getTotal() {
        return total;
    }

    public List<Comic> getResults() {
        return results;
    }
}
