package com.segunfamisa.sample.comics.data.local.mappers;


import com.segunfamisa.sample.comics.data.local.realm.ComicRealm;
import com.segunfamisa.sample.comics.data.model.Comic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Maps from {@link ComicRealm} to {@link Comic}.
 */
@Singleton
public class ComicRealmMapper implements Mapper<ComicRealm, Comic> {

    private final ImageRealmMapper imageRealmMapper;
    private final ComicCreatorsRealmMapper creatorsRealmMapper;
    private final ComicDateRealmMapper dateRealmMapper;
    private final ComicPriceRealmMapper priceRealmMapper;

    /**
     * Create new comic realm mapper - it depends on the following mappers.
     *
     * @param imageRealmMapper - image realm mapper
     * @param creatorsRealmMapper - creators realm mapper
     * @param dateRealmMapper - date realm mapper
     * @param priceRealmMapper - price realm mapper
     */
    @Inject
    public ComicRealmMapper(ImageRealmMapper imageRealmMapper,
                            ComicCreatorsRealmMapper creatorsRealmMapper,
                            ComicDateRealmMapper dateRealmMapper,
                            ComicPriceRealmMapper priceRealmMapper) {
        this.imageRealmMapper = imageRealmMapper;
        this.creatorsRealmMapper = creatorsRealmMapper;
        this.dateRealmMapper = dateRealmMapper;
        this.priceRealmMapper = priceRealmMapper;
    }

    @Override
    public Comic map1(ComicRealm from) {
        return new Comic.Builder()
                .setId(from.getId())
                .setTitle(from.getTitle())
                .setDescription(from.getDescription())
                .setPageCount(from.getPageCount())
                .setThumbnail(imageRealmMapper.map1(from.getThumbnail()))
                .setImages(new ArrayList<>(imageRealmMapper.mapMany(from.getImages())))
                .setPrices(new ArrayList<>(priceRealmMapper.mapMany(from.getPrices())))
                .setCreators(creatorsRealmMapper.map1(from.getCreators()))
                .setDates(new ArrayList<>(dateRealmMapper.mapMany(from.getDates())))
                .build();
    }

    @Override
    public Collection<Comic> mapMany(Collection<ComicRealm> fromCollection) {
        List<Comic> comicList = new ArrayList<>();

        for (ComicRealm comicRealm : fromCollection) {
            comicList.add(map1(comicRealm));
        }

        return comicList;
    }
}
