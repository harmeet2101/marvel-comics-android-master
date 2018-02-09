package com.segunfamisa.sample.comics.data.local.realm;


import io.realm.RealmObject;

/**
 * Realm table to store comic prices.
 */
public class ComicPriceRealm extends RealmObject {

    private String type;
    private double price;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
