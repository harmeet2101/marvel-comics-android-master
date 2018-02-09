package com.segunfamisa.sample.comics.data.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Data class for a price item.
 */
public class ComicPrice implements Comparable<ComicPrice> {

    @SerializedName("type")
    private String type;

    @SerializedName("price")
    private double price;

    public ComicPrice() {
    }

    private ComicPrice(String type, double price) {
        this.type = type;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public int compareTo(@NonNull ComicPrice otherPrice) {
        if (price > otherPrice.price) {
            return 1;
        } else if (price < otherPrice.price) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * Builder class.
     */
    public static class Builder {
        private String type;
        private double price;

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }

        public ComicPrice build() {
            return new ComicPrice(type, price);
        }
    }
}
