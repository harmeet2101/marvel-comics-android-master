package com.segunfamisa.sample.comics.presentation.comiclist;


import com.segunfamisa.sample.comics.data.model.Comic;
import com.segunfamisa.sample.comics.presentation.base.BasePresenter;
import com.segunfamisa.sample.comics.presentation.base.BaseView;

import java.util.List;

/**
 * Contract between the View and Presenter.
 */
public interface ComicListContract {

    interface Presenter extends BasePresenter<View> {

        void navigateToComicDetails(Comic comic);

        void navigateToBudgetScreen();

        void refreshComicList();

        void fetchComicList(boolean showLoading);

    }

    interface View extends BaseView {

        void setLoading(boolean loading);

        void setRefreshing(boolean refreshing);

        void showComicDetails(long comicId);

        void showComicList(List<Comic> comicList);

        void showLoadingError(String error);

        void showComicBudgetScreen();

    }

}
