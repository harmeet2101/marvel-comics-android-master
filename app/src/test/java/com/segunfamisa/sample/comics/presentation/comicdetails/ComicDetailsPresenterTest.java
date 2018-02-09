package com.segunfamisa.sample.comics.presentation.comicdetails;

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

import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ComicDetailsPresenterTest {

    @Mock
    private ComicDetailsContract.View view;

    @Mock
    private ComicRepository comicRepository;

    @Mock
    private ErrorStringMapper errorStringMapper;

    private final Comic comic = TestDataGenerator.getComic();
    private ComicDetailsContract.Presenter presenter;

    /**
     * Set Up.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // setup error mapper;
        setUpErrorMapper();

        SchedulerProvider schedulerProvider = new TestSchedulerProvider();
        presenter = new ComicDetailsPresenter(comicRepository, errorStringMapper,
                schedulerProvider);
    }

    @Test
    public void fetchComicDetails() {
        // given that the repository returns an observable of comic item
        when(comicRepository.getComic(anyLong())).thenReturn(Observable.just(comic));

        // given that the presenter is attached
        presenter.onAttach(view);

        // when we call fetch comic details
        long id = comic.getId();
        presenter.fetchComicDetails(id);

        // then verify that the repository is called
        verify(comicRepository).getComic(id);

        // then verify that the show loading is called to show at first
        verify(view, atLeastOnce()).showLoading(true);

        // then verify that the show loading is called to hide at least once (after request)
        verify(view, atLeastOnce()).showLoading(false);

        // then verify that the methods to update the view details is called
        verify(view).showTitle(comic.getTitle());
        verify(view).showDescription(comic.getDescription());
        verify(view).showPageCount(String.valueOf(comic.getPageCount()));
        verify(view).showPrices(comic.getPrices());
        verify(view).showAuthors(comic.getCreators());
        verify(view).showComicImage(comic.getThumbnail().getUrl());
    }

    @Test
    public void fetchComicDetails_givesError() {
        // given that the repository returns an error
        when(comicRepository.getComic(anyLong())).thenReturn(
                Observable.<Comic>error(new Throwable("Error")));

        // given that the presenter is attached
        presenter.onAttach(view);

        // when we call fetch comic details
        long id = comic.getId();
        presenter.fetchComicDetails(id);

        // then verify that the repository is called
        verify(comicRepository).getComic(id);

        // then verify that the show loading is called to show at first
        verify(view, atLeastOnce()).showLoading(true);

        // then verify that the show loading is called to hide at least once (after request)
        verify(view, atLeastOnce()).showLoading(false);

        // then verify that we show error
        verify(view).showError(anyString());
    }

    private void setUpErrorMapper() {
        when(errorStringMapper.getErrorMessage(any(Throwable.class))).thenReturn("Error");
    }
}