package com.segunfamisa.sample.comics.data;


import android.support.annotation.NonNull;

import com.segunfamisa.sample.comics.data.model.Comic;

import java.util.List;

import io.reactivex.Observable;

/**
 * Interface for Comic data sources.
 */
public interface ComicDataSource {

    Observable<List<Comic>> getComics();

    Observable<Comic> getComic(long comicId);

}
