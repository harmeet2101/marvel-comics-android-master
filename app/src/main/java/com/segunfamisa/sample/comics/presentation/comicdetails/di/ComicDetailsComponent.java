package com.segunfamisa.sample.comics.presentation.comicdetails.di;


import com.segunfamisa.sample.comics.data.di.ScreenScoped;
import com.segunfamisa.sample.comics.presentation.comicdetails.ComicDetailsFragment;

import dagger.Subcomponent;

@ScreenScoped
@Subcomponent(modules = ComicDetailsPresenterModule.class)
public interface ComicDetailsComponent {

    void inject(ComicDetailsFragment fragment);

}
