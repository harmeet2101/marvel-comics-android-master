package com.segunfamisa.sample.comics.data.local.realm;


import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Realm table for {@link com.segunfamisa.sample.comics.data.model.Comic}
 */
public class ComicRealm extends RealmObject {

    @PrimaryKey
    private long id;
    private String title;
    private String description;
    private int pageCount;
    private ImageRealm thumbnail;
    private RealmList<ImageRealm> images;
    private RealmList<ComicPriceRealm> prices;
    private ComicCreatorsRealm creators;
    private RealmList<ComicDateRealm> dates;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public ImageRealm getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ImageRealm thumbnail) {
        this.thumbnail = thumbnail;
    }

    public RealmList<ImageRealm> getImages() {
        return images;
    }

    public void setImages(RealmList<ImageRealm> images) {
        this.images = images;
    }

    public RealmList<ComicPriceRealm> getPrices() {
        return prices;
    }

    public void setPrices(RealmList<ComicPriceRealm> prices) {
        this.prices = prices;
    }

    public ComicCreatorsRealm getCreators() {
        return creators;
    }

    public void setCreators(ComicCreatorsRealm creators) {
        this.creators = creators;
    }

    public RealmList<ComicDateRealm> getDates() {
        return dates;
    }

    public void setDates(RealmList<ComicDateRealm> dates) {
        this.dates = dates;
    }
}
