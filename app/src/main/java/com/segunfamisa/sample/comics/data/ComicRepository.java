package com.segunfamisa.sample.comics.data;


import android.support.annotation.VisibleForTesting;

import com.segunfamisa.sample.comics.data.di.Local;
import com.segunfamisa.sample.comics.data.di.Remote;
import com.segunfamisa.sample.comics.data.model.Comic;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Repository to handle fetching comic data from both remote and local data sources.
 */
@Singleton
public class ComicRepository {

    private final ComicDataSource localDataSource;
    private final ComicDataSource remoteDataSource;
    private final ComicDataPersistence localDataPersistence;

    @VisibleForTesting
    final Map<Long, Comic> inMemoryDataSource = new LinkedHashMap<>();

    @VisibleForTesting
    boolean forceRefresh = false;

    /**
     * Comic Repository.
     *
     * @param localDataSource      - local data source
     * @param remoteDataSource     - remote data source
     * @param localDataPersistence - local data persistence
     */
    @Inject
    public ComicRepository(@Local ComicDataSource localDataSource,
                           @Remote ComicDataSource remoteDataSource,
                           @Local ComicDataPersistence localDataPersistence) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
        this.localDataPersistence = localDataPersistence;
    }

    /**
     * Get comics.
     *
     * @return comics
     */
    public Observable<List<Comic>> getComics() {
        if (!forceRefresh && !inMemoryDataSource.isEmpty()) {
            return getInMemoryComics();
        }

        Observable<List<Comic>> remoteComics = getAndSaveRemoteComics();
        if (forceRefresh) {
            forceRefresh = false;
            return remoteComics;
        } else {
            List<Observable<List<Comic>>> sourcesObservable = new ArrayList<>();
            sourcesObservable.add(getLocalComics());
            sourcesObservable.add(remoteComics);

            return Observable.concatDelayError(sourcesObservable)
                    .filter(new Predicate<List<Comic>>() {
                        @Override
                        public boolean test(List<Comic> comics) throws Exception {
                            return !comics.isEmpty();
                        }
                    })
                    .firstElement()
                    .toObservable();
        }
    }

    /**
     * Get single comic.
     *
     * @param comicId - id of the comic to get
     * @return - a {@link Comic} object
     */
    public Observable<Comic> getComic(long comicId) {
        final Comic inMemoryComic = inMemoryDataSource.get(comicId);

        if (inMemoryComic != null) {
            return Observable.just(inMemoryComic);
        }

        List<Observable<Comic>> sourcesObservable = new ArrayList<>();
        sourcesObservable.add(getLocalComic(comicId));
        sourcesObservable.add(getAndSaveRemoteComic(comicId));

        return Observable.concat(sourcesObservable)
                .filter(new Predicate<Comic>() {
                    @Override
                    public boolean test(Comic comic) throws Exception {
                        return comic != null;
                    }
                })
                .firstElement()
                .toObservable();
    }

    public void refresh() {
        forceRefresh = true;
    }

    private Observable<List<Comic>> getLocalComics() {
        return localDataSource.getComics()
                .doOnNext(new Consumer<List<Comic>>() {
                    @Override
                    public void accept(List<Comic> comics) throws Exception {
                        for (Comic comic : comics) {
                            inMemoryDataSource.put(comic.getId(), comic);
                        }
                    }
                });
    }

    private Observable<List<Comic>> getAndSaveRemoteComics() {
        return remoteDataSource.getComics()
                .doOnNext(new Consumer<List<Comic>>() {
                    @Override
                    public void accept(List<Comic> comics) throws Exception {
                        for (Comic comic : comics) {
                            localDataPersistence.save(comic);
                            inMemoryDataSource.put(comic.getId(), comic);
                        }
                    }
                });
    }

    private Observable<Comic> getAndSaveRemoteComic(long comicId) {
        return remoteDataSource.getComic(comicId)
                .doOnNext(new Consumer<Comic>() {
                    @Override
                    public void accept(Comic comic) throws Exception {
                        localDataPersistence.save(comic);
                        inMemoryDataSource.put(comic.getId(), comic);
                    }
                });
    }

    private Observable<Comic> getLocalComic(final long comicId) {
        return localDataSource.getComic(comicId)
                .doOnNext(new Consumer<Comic>() {
                    @Override
                    public void accept(Comic comic) throws Exception {
                        inMemoryDataSource.put(comic.getId(), comic);
                    }
                });
    }

    private Observable<List<Comic>> getInMemoryComics() {
        List<Comic> inMemoryComics = new ArrayList<>(inMemoryDataSource.values());
        return Observable.just(inMemoryComics);
    }
}
