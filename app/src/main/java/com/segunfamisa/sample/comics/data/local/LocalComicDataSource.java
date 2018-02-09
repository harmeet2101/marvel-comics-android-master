package com.segunfamisa.sample.comics.data.local;


import com.segunfamisa.sample.comics.data.ComicDataSource;
import com.segunfamisa.sample.comics.data.local.mappers.Mapper;
import com.segunfamisa.sample.comics.data.local.realm.ComicRealm;
import com.segunfamisa.sample.comics.data.local.realm.Tables;
import com.segunfamisa.sample.comics.data.model.Comic;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Fetches comic items from the db (realm db).
 */
@Singleton
public class LocalComicDataSource implements ComicDataSource {

    private final Mapper<ComicRealm, Comic> mapper;

    @Inject
    public LocalComicDataSource(Mapper<ComicRealm, Comic> mapper) {
        this.mapper = mapper;
    }

    @Override
    public Observable<List<Comic>> getComics() {
        return Observable.just(findComics());
    }

    @Override
    public Observable<Comic> getComic(long comicId) {
        Comic comic = findComicById(comicId);
        if (comic == null) {
            return Observable.empty();
        }
        return Observable.just(comic);
    }

    private List<Comic> findComics() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ComicRealm> comicRealms = realm.where(ComicRealm.class)
                .findAll();

        return new ArrayList<>(mapper.mapMany(comicRealms));
    }

    private Comic findComicById(long comicId) {
        Realm realm = Realm.getDefaultInstance();
        ComicRealm comicRealm = realm.where(ComicRealm.class)
                .equalTo(Tables.Comic.id, comicId)
                .findFirst();

        if (comicRealm != null) {
            return mapper.map1(comicRealm);
        }
        return null;
    }
}
