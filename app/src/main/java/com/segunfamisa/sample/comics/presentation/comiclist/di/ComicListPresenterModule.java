package com.segunfamisa.sample.comics.presentation.comiclist.di;


import com.segunfamisa.sample.comics.data.di.ScreenScoped;
import com.segunfamisa.sample.comics.presentation.comiclist.ComicListContract;

import dagger.Module;
import dagger.Provides;

/**
 * Comic list module.
 */
@Module
public class ComicListPresenterModule {

    private final ComicListContract.View view;

    public ComicListPresenterModule(ComicListContract.View view) {
        this.view = view;
    }

    @Provides
    @ScreenScoped
    ComicListContract.View provideView() {
        return view;
    }

}
