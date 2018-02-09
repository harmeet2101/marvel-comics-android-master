package com.segunfamisa.sample.comics.presentation.budget;


import com.segunfamisa.sample.comics.data.model.Comic;
import com.segunfamisa.sample.comics.presentation.base.BasePresenter;
import com.segunfamisa.sample.comics.presentation.base.BaseView;

import java.util.List;

/**
 * Contract between the budget view and presenter.
 */
public interface BudgetContract {

    interface Presenter extends BasePresenter<View> {

        void findComics(String budget);

        void navigateToComicDetails(Comic comic);
    }

    interface View extends BaseView {

        void showMatchingComicList(List<Comic> comicList);

        void showError(String error);

        void showTotalComicPagesCountForBudget(int pagesCount);

        void showNoMatchingComic(boolean noMatchingComic);

        void showLoading(boolean isLoading);

        void showComicDetails(long comicId);

    }

}
