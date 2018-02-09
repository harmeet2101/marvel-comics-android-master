package com.segunfamisa.sample.comics.presentation.comicdetails.di;


import com.segunfamisa.sample.comics.presentation.comicdetails.ComicDetailsContract;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


/**
 * Module for comic details.
 */
@Module
public class ComicDetailsPresenterModule {

    private final ComicDetailsContract.View view;

    public ComicDetailsPresenterModule(ComicDetailsContract.View view) {
        this.view = view;
    }

    @Provides
    @Singleton
    ComicDetailsContract.View provideView() {
        return view;
    }

}
