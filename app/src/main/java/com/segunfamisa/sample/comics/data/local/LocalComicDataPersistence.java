package com.segunfamisa.sample.comics.data.local;


import android.support.annotation.NonNull;

import com.segunfamisa.sample.comics.data.ComicDataPersistence;
import com.segunfamisa.sample.comics.data.local.realm.ComicCreatorsRealm;
import com.segunfamisa.sample.comics.data.local.realm.ComicDateRealm;
import com.segunfamisa.sample.comics.data.local.realm.ComicPriceRealm;
import com.segunfamisa.sample.comics.data.local.realm.ComicRealm;
import com.segunfamisa.sample.comics.data.local.realm.CreatorSummaryRealm;
import com.segunfamisa.sample.comics.data.local.realm.ImageRealm;
import com.segunfamisa.sample.comics.data.local.realm.Tables;
import com.segunfamisa.sample.comics.data.model.Comic;
import com.segunfamisa.sample.comics.data.model.ComicCreators;
import com.segunfamisa.sample.comics.data.model.ComicDate;
import com.segunfamisa.sample.comics.data.model.ComicPrice;
import com.segunfamisa.sample.comics.data.model.CreatorSummary;
import com.segunfamisa.sample.comics.data.model.Image;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Writes comic data into local db (using realm).
 */
@Singleton
public class LocalComicDataPersistence implements ComicDataPersistence {

    @Inject
    public LocalComicDataPersistence() {
    }

    @Override
    public void save(@NonNull final Comic comic) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                findOrCreateComic(realm, comic);
            }
        });
    }

    private ComicRealm findOrCreateComic(Realm realm, Comic comic) {
        ComicRealm comicRealm = realm.where(ComicRealm.class)
                .equalTo("id", comic.getId())
                .findFirst();

        if (comicRealm == null) {
            comicRealm = realm.createObject(ComicRealm.class, comic.getId());

            comicRealm.setTitle(comic.getTitle());
            comicRealm.setDescription(comic.getDescription());
            comicRealm.setPageCount(comic.getPageCount());
            comicRealm.setThumbnail(findOrCreateImage(realm, comic.getThumbnail()));
            comicRealm.setImages(findOrCreateImages(realm, comic.getImages()));
            comicRealm.setPrices(findOrCreatePrices(realm, comic.getPrices()));
            comicRealm.setCreators(findOrCreateCreators(realm, comic.getCreators()));
            comicRealm.setDates(findOrCreateDates(realm, comic.getDates()));
        }
        return comicRealm;
    }

    private ImageRealm findOrCreateImage(Realm realm, Image image) {
        ImageRealm imageRealm = realm.where(ImageRealm.class)
                .equalTo(Tables.Image.path, image.getPath())
                .equalTo(Tables.Image.extension, image.getExtension())
                .findFirst();

        if (imageRealm == null) {
            imageRealm = realm.createObject(ImageRealm.class, image.getPath());

            imageRealm.setExtension(image.getExtension());
        }
        return imageRealm;
    }

    private RealmList<ImageRealm> findOrCreateImages(Realm realm, List<Image> images) {
        RealmList<ImageRealm> imageRealms = new RealmList<>();
        for (Image image : images) {
            imageRealms.add(findOrCreateImage(realm, image));
        }
        return imageRealms;
    }

    private RealmList<ComicPriceRealm> findOrCreatePrices(Realm realm, List<ComicPrice> prices) {
        RealmList<ComicPriceRealm> priceRealms = new RealmList<>();

        for (ComicPrice price : prices) {
            priceRealms.add(findOrCreatePrice(realm, price));
        }
        return priceRealms;
    }

    private ComicPriceRealm findOrCreatePrice(Realm realm, ComicPrice price) {
        ComicPriceRealm priceRealm = realm.where(ComicPriceRealm.class)
                .equalTo(Tables.ComicPrice.type, price.getType())
                .equalTo(Tables.ComicPrice.price, price.getPrice())
                .findFirst();

        if (priceRealm == null) {
            priceRealm = realm.createObject(ComicPriceRealm.class);

            priceRealm.setPrice(price.getPrice());
            priceRealm.setType(price.getType());
        }
        return priceRealm;
    }

    private ComicCreatorsRealm findOrCreateCreators(Realm realm, ComicCreators creators) {
        ComicCreatorsRealm creatorsRealm = realm.where(ComicCreatorsRealm.class)
                .equalTo(Tables.ComicCreators.collectionUri, creators.getCollectionUri())
                .findFirst();

        if (creatorsRealm == null) {
            creatorsRealm = realm.createObject(ComicCreatorsRealm.class);
        }

        creatorsRealm.setAvailable(creators.getAvailable());
        creatorsRealm.setCollectionUri(creators.getCollectionUri());
        creatorsRealm.setItems(findOrCreateCreatorSummaries(realm, creators.getItems()));

        return creatorsRealm;
    }

    private RealmList<CreatorSummaryRealm> findOrCreateCreatorSummaries(
            Realm realm,
            List<CreatorSummary> summaries) {
        RealmList<CreatorSummaryRealm> creatorSummaryRealms = new RealmList<>();

        for (CreatorSummary summary : summaries) {
            creatorSummaryRealms.add(findOrCreateCreatorSummary(realm, summary));
        }

        return creatorSummaryRealms;
    }

    private CreatorSummaryRealm findOrCreateCreatorSummary(Realm realm, CreatorSummary summary) {
        CreatorSummaryRealm summaryRealm = realm.where(CreatorSummaryRealm.class)
                .equalTo(Tables.CreatorSummary.name, summary.getName())
                .equalTo(Tables.CreatorSummary.resourceUri, summary.getResourceUri())
                .equalTo(Tables.CreatorSummary.role, summary.getRole())
                .findFirst();

        if (summaryRealm == null) {
            summaryRealm = realm.createObject(CreatorSummaryRealm.class);

            summaryRealm.setName(summary.getName());
            summaryRealm.setResourceUri(summary.getResourceUri());
            summaryRealm.setRole(summary.getRole());
        }
        return summaryRealm;
    }

    private RealmList<ComicDateRealm> findOrCreateDates(Realm realm, List<ComicDate> dates) {
        RealmList<ComicDateRealm> dateRealms = new RealmList<>();

        for (ComicDate date : dates) {
            dateRealms.add(findOrCreateDate(realm, date));
        }
        return dateRealms;
    }

    private ComicDateRealm findOrCreateDate(Realm realm, ComicDate date) {
        ComicDateRealm dateRealm = realm.where(ComicDateRealm.class)
                .equalTo(Tables.ComicDate.type, date.getType())
                .equalTo(Tables.ComicDate.date, date.getDate())
                .findFirst();

        if (dateRealm == null) {
            dateRealm = realm.createObject(ComicDateRealm.class);

            dateRealm.setType(date.getType());
            dateRealm.setDate(date.getDate());
        }
        return dateRealm;
    }
}
