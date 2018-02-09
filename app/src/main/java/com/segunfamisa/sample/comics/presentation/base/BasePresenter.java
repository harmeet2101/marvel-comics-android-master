package com.segunfamisa.sample.comics.presentation.base;


/**
 * Base presenter.
 *
 * @param <V> - View
 */
public interface BasePresenter<V> {

    void onAttach(V view);

    void onDetach();

}
