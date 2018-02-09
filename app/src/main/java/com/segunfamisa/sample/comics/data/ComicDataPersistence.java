package com.segunfamisa.sample.comics.data;


import android.support.annotation.NonNull;

import com.segunfamisa.sample.comics.data.model.Comic;

/**
 * Interface to save comic data.
 */
public interface ComicDataPersistence {

    void save(@NonNull Comic comic);

}
