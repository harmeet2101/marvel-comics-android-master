package com.segunfamisa.sample.comics.presentation.comiclist;

import com.segunfamisa.sample.comics.common.scheduler.SchedulerProvider;
import com.segunfamisa.sample.comics.data.ComicRepository;
import com.segunfamisa.sample.comics.data.TestDataGenerator;
import com.segunfamisa.sample.comics.data.model.Comic;
import com.segunfamisa.sample.comics.util.ErrorStringMapper;
import com.segunfamisa.sample.comics.util.TestSchedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class ComicListPresenterTest {

    @Mock
    private ComicListContract.View view;

    @Mock
    private ComicRepository comicRepository;

    @Mock
    private ErrorStringMapper errorStringMapper;

    private List<Comic> comicList = TestDataGenerator.getComics();
    private ComicListContract.Presenter presenter;

    /**
     * Set Up.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        SchedulerProvider schedulerProvider = new TestSchedulerProvider();
        presenter = new ComicListPresenter(comicRepository, errorStringMapper, schedulerProvider);
    }

    @Test
    public void fetchAndShowComicList_showLoading() {
        // given the repository returns an observable list of comics
        when(comicRepository.getComics()).thenReturn(Observable.just(comicList));

        // given that view has been attached
        presenter.onAttach(view);

        // when we call fetch list and showing the progress
        boolean showLoading = true;
        presenter.fetchComicList(showLoading);

        // then verify that the repository is called
        verify(comicRepository).getComics();

        // then verify that the view is called to show/hide loading before the request at least once
        verify(view, atLeastOnce()).setLoading(showLoading);

        // then verify that the view is called to hide the loading (after request has been made)
        verify(view, atLeastOnce()).setLoading(false);

        // then verify that the view shows the list of comics with the emitted comic list
        verify(view).showComicList(comicList);
    }

    @Test
    public void fetchAndShowComicList_doNotShow_Loading() {
        // given the repository returns an observable list of comics
        when(comicRepository.getComics()).thenReturn(Observable.just(comicList));

        // given that view has been attached
        presenter.onAttach(view);

        boolean showLoading = false;

        // when we call fetch list without showing the progress
        presenter.fetchComicList(showLoading);

        // then verify that the repository is called
        verify(comicRepository).getComics();

        // then verify that the view is called to show/hide loading before the request at least once
        verify(view, atLeastOnce()).setLoading(showLoading);

        // then verify that the view is called to hide the loading (after request has been made)
        verify(view, atLeastOnce()).setLoading(false);

        // then verify that the view shows the list of comics with the emitted comic list
        verify(view).showComicList(comicList);
    }

    @Test
    public void fetchComicList_givesError() {
        // given the repository returns an error
        when(comicRepository.getComics()).thenReturn(
                Observable.<List<Comic>>error(new Throwable("Error")));

        // given that the error mapper returns a string
        when(errorStringMapper.getErrorMessage(any(Throwable.class))).thenReturn("Error");

        // given that the view has been attached
        presenter.onAttach(view);

        // when we call fetch list
        boolean showLoading = true;
        presenter.fetchComicList(showLoading);

        // verify that the view is called to show loading (before the request)
        verify(view, atLeastOnce()).setLoading(showLoading);

        // verify that the view is called to hide loading
        verify(view, atLeastOnce()).setLoading(false);

        // verify that the view shows error
        verify(view).showLoadingError(anyString());
    }

    @Test
    public void refreshComicList() {
        // given the repository returns comic list
        when(comicRepository.getComics()).thenReturn(Observable.just(comicList));

        // given that the view has been attached
        presenter.onAttach(view);

        // when we call to refresh the list
        presenter.refreshComicList();

        // then verify that we call repository to refresh tasks
        verify(comicRepository).refresh();

        // then verify that we call repository to get comics
        verify(comicRepository).getComics();

        // then verify that we show the comic list
        verify(view).showComicList(comicList);

        // then verify that we call to show refreshing
        verify(view, atLeastOnce()).setRefreshing(true);

        // then verify that we call to hide refreshing
        verify(view, atLeastOnce()).setRefreshing(false);
    }

    @Test
    public void navigateToComicDetails() {
        // given the repository returns comic list
        when(comicRepository.getComics()).thenReturn(Observable.just(comicList));

        // given that the view has been attached
        presenter.onAttach(view);

        // when we show comic details
        Comic destinationComic = comicList.get(0);
        presenter.navigateToComicDetails(destinationComic);

        // then verify that we call the view to show the details with the right comic id
        verify(view).showComicDetails(destinationComic.getId());
    }

    @Test
    public void navigateToBudgetScreen() {
        // given the view is attached
        presenter.onAttach(view);

        // when we call the presenter to navigate to budget screen
        presenter.navigateToBudgetScreen();

        // then verify that the view is called on show budget screen
        verify(view).showComicBudgetScreen();
    }
}