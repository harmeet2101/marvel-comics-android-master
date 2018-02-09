package com.segunfamisa.sample.comics.data.local.mappers;


import com.segunfamisa.sample.comics.data.local.realm.ComicCreatorsRealm;
import com.segunfamisa.sample.comics.data.model.ComicCreators;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Maps from {@link ComicCreatorsRealm} to {@link ComicCreators}.
 */
@Singleton
public class ComicCreatorsRealmMapper implements Mapper<ComicCreatorsRealm, ComicCreators> {

    private final CreatorSummaryRealmMapper summaryRealmMapper;

    @Inject
    public ComicCreatorsRealmMapper(CreatorSummaryRealmMapper summaryRealmMapper) {
        this.summaryRealmMapper = summaryRealmMapper;
    }

    @Override
    public ComicCreators map1(ComicCreatorsRealm from) {
        return new ComicCreators.Builder()
                .setAvailable(from.getAvailable())
                .setCollectionUri(from.getCollectionUri())
                .setItems(new ArrayList<>(summaryRealmMapper.mapMany(from.getItems())))
                .build();
    }

    @Override
    public Collection<ComicCreators> mapMany(Collection<ComicCreatorsRealm> fromCollection) {
        return null;
    }
}
