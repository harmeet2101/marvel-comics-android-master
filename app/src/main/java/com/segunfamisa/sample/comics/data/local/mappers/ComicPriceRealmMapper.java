package com.segunfamisa.sample.comics.data.local.mappers;


import com.segunfamisa.sample.comics.data.local.realm.ComicPriceRealm;
import com.segunfamisa.sample.comics.data.model.ComicPrice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ComicPriceRealmMapper implements Mapper<ComicPriceRealm, ComicPrice> {

    @Inject
    public ComicPriceRealmMapper() {
    }

    @Override
    public ComicPrice map1(ComicPriceRealm comicPriceRealm) {
        return new ComicPrice.Builder()
                .setPrice(comicPriceRealm.getPrice())
                .setType(comicPriceRealm.getType())
                .build();
    }

    @Override
    public Collection<ComicPrice> mapMany(Collection<ComicPriceRealm> fromCollection) {
        List<ComicPrice> priceList = new ArrayList<>();

        for (ComicPriceRealm comicPriceRealm : fromCollection) {
            priceList.add(map1(comicPriceRealm));
        }

        return priceList;
    }
}
