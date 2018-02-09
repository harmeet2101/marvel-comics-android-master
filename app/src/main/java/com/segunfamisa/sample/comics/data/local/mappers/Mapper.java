package com.segunfamisa.sample.comics.data.local.mappers;


import java.util.Collection;

/**
 * Interface to map from realm objects to the data models.
 *
 * @param <F> - type of object to map from
 * @param <T> - type of object to map to
 */
public interface Mapper<F, T> {

    T map1(F from);

    Collection<T> mapMany(Collection<F> fromCollection);

}
