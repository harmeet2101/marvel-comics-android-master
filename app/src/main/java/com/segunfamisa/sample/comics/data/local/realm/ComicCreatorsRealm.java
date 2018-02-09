package com.segunfamisa.sample.comics.data.local.realm;


import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Realm table for {@link com.segunfamisa.sample.comics.data.model.ComicCreators}
 */
public class ComicCreatorsRealm extends RealmObject {

    private int available;
    private String collectionUri;
    private RealmList<CreatorSummaryRealm> items;

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public String getCollectionUri() {
        return collectionUri;
    }

    public void setCollectionUri(String collectionUri) {
        this.collectionUri = collectionUri;
    }

    public RealmList<CreatorSummaryRealm> getItems() {
        return items;
    }

    public void setItems(RealmList<CreatorSummaryRealm> items) {
        this.items = items;
    }
}
