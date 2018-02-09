package com.segunfamisa.sample.comics.data.model;


import com.google.gson.annotations.SerializedName;

/**
 * Comic Date model.
 */
public class ComicDate {

    @SerializedName("type")
    private String type;

    @SerializedName("date")
    private String date;

    public ComicDate() {
    }

    private ComicDate(String type, String date) {
        this.type = type;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    /**
     * Builder class.
     */
    public static class Builder {
        private String type;
        private String date;

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setDate(String date) {
            this.date = date;
            return this;
        }

        public ComicDate build() {
            return new ComicDate(type, date);
        }
    }
}
