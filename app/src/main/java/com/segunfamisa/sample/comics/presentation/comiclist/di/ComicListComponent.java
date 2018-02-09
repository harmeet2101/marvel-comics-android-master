package com.segunfamisa.sample.comics.presentation.comiclist.di;


import com.segunfamisa.sample.comics.data.di.ScreenScoped;
import com.segunfamisa.sample.comics.presentation.comiclist.ComicListFragment;

import dagger.Subcomponent;

/**
 * ComicList dagger sub component.
 */
@ScreenScoped
@Subcomponent(modules = ComicListPresenterModule.class)
public interface ComicListComponent {

    void inject(ComicListFragment fragment);

}
