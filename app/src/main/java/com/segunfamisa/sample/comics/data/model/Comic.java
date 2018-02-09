package com.segunfamisa.sample.comics.data.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Comic item data model.
 */
public class Comic {

    @SerializedName("id")
    private long id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("pageCount")
    private int pageCount;

    @SerializedName("thumbnail")
    private Image thumbnail;

    @SerializedName("images")
    private List<Image> images;

    @SerializedName("prices")
    private List<ComicPrice> prices;

    @SerializedName("creators")
    private ComicCreators creators;

    @SerializedName("dates")
    private List<ComicDate> dates;

    public Comic() {
    }

    private Comic(long id, String title, String description, int pageCount, Image thumbnail,
                 List<Image> images, List<ComicPrice> prices, ComicCreators creators,
                 List<ComicDate> dates) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pageCount = pageCount;
        this.thumbnail = thumbnail;
        this.images = images;
        this.prices = prices;
        this.creators = creators;
        this.dates = dates;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPageCount() {
        return pageCount;
    }

    public Image getThumbnail() {
        return thumbnail;
    }

    public List<Image> getImages() {
        return images;
    }

    public List<ComicPrice> getPrices() {
        return prices;
    }

    public ComicCreators getCreators() {
        return creators;
    }

    public List<ComicDate> getDates() {
        return dates;
    }

    /**
     * Gets the least possible price of the comic item (as it is possible to have multiple prices.
     *
     * @return - the value of the least price you can get the comic book
     */
    public double getLeastPrice() {
        List<ComicPrice> prices = getPrices();
        double leastPrice = Double.MAX_VALUE;
        for (ComicPrice price : prices) {
            if (price.getPrice() < leastPrice) {
                leastPrice = price.getPrice();
            }
        }
        return leastPrice;
    }

    /**
     * Builder class.
     */
    public static class Builder {
        private long id;
        private String title;
        private String description;
        private int pageCount;
        private Image thumbnail;
        private List<Image> images;
        private List<ComicPrice> prices;
        private ComicCreators creators;
        private List<ComicDate> dates;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setPageCount(int pageCount) {
            this.pageCount = pageCount;
            return this;
        }

        public Builder setThumbnail(Image thumbnail) {
            this.thumbnail = thumbnail;
            return this;
        }

        public Builder setImages(List<Image> images) {
            this.images = images;
            return this;
        }

        public Builder setPrices(List<ComicPrice> prices) {
            this.prices = prices;
            return this;
        }

        public Builder setCreators(ComicCreators creators) {
            this.creators = creators;
            return this;
        }

        public Builder setDates(List<ComicDate> dates) {
            this.dates = dates;
            return this;
        }

        public Comic build() {
            return new Comic(id, title, description, pageCount, thumbnail, images, prices, creators,
                    dates);
        }
    }
}
