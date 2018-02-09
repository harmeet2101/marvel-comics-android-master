package com.segunfamisa.sample.comics.data.local.mappers;


import com.segunfamisa.sample.comics.data.local.realm.ComicDateRealm;
import com.segunfamisa.sample.comics.data.model.ComicDate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Maps from comic date realm to comic date.
 */
@Singleton
public class ComicDateRealmMapper implements Mapper<ComicDateRealm, ComicDate> {

    @Inject
    public ComicDateRealmMapper() {
    }

    @Override
    public ComicDate map1(ComicDateRealm from) {
        return new ComicDate.Builder()
                .setDate(from.getDate())
                .setType(from.getType())
                .build();
    }

    @Override
    public Collection<ComicDate> mapMany(Collection<ComicDateRealm> fromCollection) {
        List<ComicDate> comicDateList = new ArrayList<>();

        for (ComicDateRealm comicDateRealm : fromCollection) {
            comicDateList.add(map1(comicDateRealm));
        }

        return comicDateList;
    }
}
