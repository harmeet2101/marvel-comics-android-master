package com.segunfamisa.sample.comics.data.local.realm;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Realm table to store image objects.
 */
public class ImageRealm extends RealmObject {

    @PrimaryKey
    private String path;

    private String extension;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
