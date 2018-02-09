package com.segunfamisa.sample.comics.presentation.comiclist;


import com.segunfamisa.sample.comics.common.scheduler.SchedulerProvider;
import com.segunfamisa.sample.comics.data.ComicRepository;
import com.segunfamisa.sample.comics.data.model.Comic;
import com.segunfamisa.sample.comics.util.ErrorStringMapper;

import java.util.List;
import java.util.Observable;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Presenter implementation for the comic list.
 */
public class ComicListPresenter implements ComicListContract.Presenter {

    private final ComicRepository comicRepository;
    private final ErrorStringMapper errorStringMapper;
    private final SchedulerProvider schedulerProvider;
    private CompositeDisposable compositeDisposable;
    private ComicListContract.View view;

    /**
     * Construct new presenter.
     *
     * @param comicRepository   - comic repository
     * @param errorStringMapper - error string mapper
     * @param schedulerProvider - scheduler provider
     */
    @Inject
    public ComicListPresenter(ComicRepository comicRepository,
                              ErrorStringMapper errorStringMapper,
                              SchedulerProvider schedulerProvider) {
        this.comicRepository = comicRepository;
        this.errorStringMapper = errorStringMapper;
        this.schedulerProvider = schedulerProvider;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onAttach(ComicListContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        compositeDisposable.clear();
    }

    @Override
    public void navigateToComicDetails(Comic comic) {
        view.showComicDetails(comic.getId());
    }

    @Override
    public void navigateToBudgetScreen() {
        view.showComicBudgetScreen();
    }

    @Override
    public void refreshComicList() {
        comicRepository.refresh();
        view.setRefreshing(true);
        fetchComics();
    }

    @Override
    public void fetchComicList(boolean showLoading) {
        view.setLoading(showLoading);
        fetchComics();
    }

    private void fetchComics() {
        Disposable disposable = comicRepository.getComics()
                .observeOn(schedulerProvider.mainThread())
                .subscribeOn(schedulerProvider.io())
                .subscribe(new Consumer<List<Comic>>() {
                    @Override
                    public void accept(List<Comic> comicList) throws Exception {
                        // on next
                        view.setLoading(false);
                        view.setRefreshing(false);
                        view.showComicList(comicList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // on error
                        view.setLoading(false);
                        view.setRefreshing(false);
                        view.showLoadingError(errorStringMapper.getErrorMessage(throwable));
                    }
                });
        compositeDisposable.add(disposable);
    }
}
