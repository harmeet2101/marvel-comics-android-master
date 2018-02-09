package com.segunfamisa.sample.comics.data.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Comic Creators item model.
 */
public class ComicCreators {

    @SerializedName("available")
    private int available;

    @SerializedName("collectionURI")
    private String collectionUri;

    @SerializedName("items")
    private List<CreatorSummary> items;

    public ComicCreators() {
    }

    private ComicCreators(int available, String collectionUri, List<CreatorSummary> items) {
        this.available = available;
        this.collectionUri = collectionUri;
        this.items = items;
    }

    public int getAvailable() {
        return available;
    }

    public String getCollectionUri() {
        return collectionUri;
    }

    public List<CreatorSummary> getItems() {
        return items;
    }

    /**
     * Builder class.
     */
    public static class Builder {
        private int available;
        private String collectionUri;
        private List<CreatorSummary> items;

        public Builder setAvailable(int available) {
            this.available = available;
            return this;
        }

        public Builder setCollectionUri(String collectionUri) {
            this.collectionUri = collectionUri;
            return this;
        }

        public Builder setItems(List<CreatorSummary> items) {
            this.items = items;
            return this;
        }

        public ComicCreators build() {
            return new ComicCreators(available, collectionUri, items);
        }
    }
}
