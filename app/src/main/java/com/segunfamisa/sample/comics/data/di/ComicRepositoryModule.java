package com.segunfamisa.sample.comics.data.di;


import com.segunfamisa.sample.comics.data.ComicDataPersistence;
import com.segunfamisa.sample.comics.data.ComicDataSource;
import com.segunfamisa.sample.comics.data.local.LocalComicDataPersistence;
import com.segunfamisa.sample.comics.data.local.LocalComicDataSource;
import com.segunfamisa.sample.comics.data.local.mappers.ComicRealmMapper;
import com.segunfamisa.sample.comics.data.remote.ApiService;
import com.segunfamisa.sample.comics.data.remote.RemoteComicDataSource;
import com.segunfamisa.sample.comics.data.remote.TimeStampProvider;
import com.segunfamisa.sample.comics.util.HashCalculator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module for providing comic repository.
 */
@Module
public class ComicRepositoryModule {

    @Provides
    @Singleton
    @Remote
    ComicDataSource provideRemoteComicDataSource(ApiService apiService,
                                                 HashCalculator hashCalculator,
                                                 TimeStampProvider timeStampProvider) {
        return new RemoteComicDataSource(apiService, hashCalculator, timeStampProvider);
    }

    @Provides
    @Singleton
    @Local
    ComicDataSource provideLocalComicDataSource(ComicRealmMapper mapper) {
        return new LocalComicDataSource(mapper);
    }

    @Provides
    @Singleton
    @Local
    ComicDataPersistence provideLocalComicDataPersistence() {
        return new LocalComicDataPersistence();
    }

}
