package com.segunfamisa.sample.comics.presentation.budget;


import com.segunfamisa.sample.comics.common.scheduler.SchedulerProvider;
import com.segunfamisa.sample.comics.data.ComicRepository;
import com.segunfamisa.sample.comics.data.model.Comic;
import com.segunfamisa.sample.comics.util.BudgetCalculator;
import com.segunfamisa.sample.comics.util.ErrorStringMapper;
import com.segunfamisa.sample.comics.util.ListUtils;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Budget presenter.
 */
public class BudgetPresenter implements BudgetContract.Presenter {

    private final BudgetCalculator budgetCalculator;
    private final ComicRepository comicRepository;
    private final ErrorStringMapper errorStringMapper;
    private final SchedulerProvider schedulerProvider;
    private CompositeDisposable compositeDisposable;

    private BudgetContract.View view;

    /**
     * Create a new budget presenter.
     *
     * @param budgetCalculator  - budget calculator
     * @param comicRepository   - comic repository
     * @param errorStringMapper - error string mapper
     * @param schedulerProvider - scheduler provider
     */
    @Inject
    public BudgetPresenter(BudgetCalculator budgetCalculator,
                           ComicRepository comicRepository,
                           ErrorStringMapper errorStringMapper,
                           SchedulerProvider schedulerProvider) {
        this.budgetCalculator = budgetCalculator;
        this.comicRepository = comicRepository;
        this.errorStringMapper = errorStringMapper;
        this.schedulerProvider = schedulerProvider;
        compositeDisposable = new CompositeDisposable();
    }


    @Override
    public void onAttach(BudgetContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        compositeDisposable.clear();
    }

    @Override
    public void findComics(final String budget) {
        if (budget == null || budget.isEmpty()) {
            return;
        }
        view.showLoading(true);
        Disposable disposable = comicRepository.getComics()
                .observeOn(schedulerProvider.mainThread())
                .subscribeOn(schedulerProvider.io())
                .flatMap(new Function<List<Comic>, ObservableSource<Map<Integer, List<Comic>>>>() {
                    @Override
                    public ObservableSource<Map<Integer, List<Comic>>> apply(List<Comic> comicList)
                            throws Exception {
                        return Observable.just(
                                budgetCalculator.calculate(intValueOf(budget), comicList));
                    }
                })
                .subscribe(new Consumer<Map<Integer, List<Comic>>>() {
                    @Override
                    public void accept(Map<Integer, List<Comic>> budgetResults) throws Exception {
                        // on next
                        view.showLoading(false);

                        handleBudgetResults(budgetResults);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // on error
                        view.showLoading(false);
                        view.showError(errorStringMapper.getErrorMessage(throwable));
                    }
                });
        compositeDisposable.add(disposable);
    }

    private void handleBudgetResults(Map<Integer, List<Comic>> budgetResults) {
        int budgetPages = (int) budgetResults.keySet().toArray()[0];
        List<Comic> comics = budgetResults.get(budgetPages);

        view.showTotalComicPagesCountForBudget(budgetPages);
        view.showMatchingComicList(comics);
        view.showNoMatchingComic(ListUtils.isNullOrEmpty(comics));
    }

    private int intValueOf(String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            return 0;
        }
    }

    @Override
    public void navigateToComicDetails(Comic comic) {
        view.showComicDetails(comic.getId());
    }
}
