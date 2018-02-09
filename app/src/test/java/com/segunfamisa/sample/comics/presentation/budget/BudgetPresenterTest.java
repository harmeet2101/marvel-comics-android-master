package com.segunfamisa.sample.comics.presentation.budget;

import com.segunfamisa.sample.comics.common.scheduler.SchedulerProvider;
import com.segunfamisa.sample.comics.data.ComicRepository;
import com.segunfamisa.sample.comics.data.TestDataGenerator;
import com.segunfamisa.sample.comics.data.model.Comic;
import com.segunfamisa.sample.comics.util.BudgetCalculator;
import com.segunfamisa.sample.comics.util.ErrorStringMapper;
import com.segunfamisa.sample.comics.util.TestSchedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BudgetPresenterTest {

    @Mock
    private ComicRepository comicRepository;

    @Mock
    private BudgetContract.View view;

    @Mock
    private ErrorStringMapper errorStringMapper;

    @Mock
    private BudgetCalculator budgetCalculator;

    private final List<Comic> comics = TestDataGenerator.getComics();
    private BudgetContract.Presenter presenter;

    /**
     * Set Up.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // setup error mapper;
        setUpErrorMapper();

        final SchedulerProvider schedulerProvider = new TestSchedulerProvider();
        presenter = new BudgetPresenter(budgetCalculator, comicRepository, errorStringMapper,
                schedulerProvider);
    }

    @Test
    public void findComics() {
        // given the repository returns comics books
        when(comicRepository.getComics()).thenReturn(Observable.just(comics));

        // given a budget of $50
        int budget = 50;

        // given the budget calculator returns the first two items as the items matching the budget
        List<Comic> budgetItems = comics.subList(0, 2);
        int pages = 100;
        Map<Integer, List<Comic>> results = new HashMap<>();
        results.put(pages, budgetItems);
        when(budgetCalculator.calculate(budget, comics)).thenReturn(results);

        // given the view has been attached
        presenter.onAttach(view);

        // when we find comics
        presenter.findComics(String.valueOf(budget));

        // then verify that the budget calculator is called with the expected params
        verify(budgetCalculator).calculate(budget, comics);

        // then verify that we show loading at least once
        verify(view, atLeastOnce()).showLoading(true);

        // then verify that we loading is hidden at least once
        verify(view, atLeastOnce()).showLoading(false);

        // then verify that the budget item list is showing on the view
        verify(view).showMatchingComicList(budgetItems);

        // verify that the count of pages is showing on the view
        verify(view).showTotalComicPagesCountForBudget(pages);
    }

    @Test
    public void findComics_emptyResult() {
        // given the repository returns comics books
        when(comicRepository.getComics()).thenReturn(Observable.just(comics));

        // given a budget of $50
        int budget = 50;

        // given the budget calculator returns an empty results
        List<Comic> budgetItems = new ArrayList<>();
        int pages = 0;
        Map<Integer, List<Comic>> results = new HashMap<>();
        results.put(pages, budgetItems);
        when(budgetCalculator.calculate(budget, comics)).thenReturn(results);

        // given the view has been attached
        presenter.onAttach(view);

        // when we find comics
        presenter.findComics(String.valueOf(budget));

        // then verify that the budget calculator is called with the expected params
        verify(budgetCalculator).calculate(budget, comics);

        // then verify that we show loading at least once
        verify(view, atLeastOnce()).showLoading(true);

        // then verify that we loading is hidden at least once
        verify(view, atLeastOnce()).showLoading(false);

        // verify that the count of pages is showing on the view
        verify(view).showTotalComicPagesCountForBudget(0);

        // then verify that the no matching comic is showing
        verify(view).showNoMatchingComic(true);
    }

    @Test
    public void findComics_givesError() {
        // given the repository gives error
        when(comicRepository.getComics()).thenReturn(
                Observable.<List<Comic>>error(new Throwable("Error")));

        // given presenter is attached
        presenter.onAttach(view);

        // when we call find comics
        int budget = 40;
        presenter.findComics(String.valueOf(budget));

        // then verify that we show loading at least once
        verify(view, atLeastOnce()).showLoading(true);

        // then verify that we loading is hidden at least once
        verify(view, atLeastOnce()).showLoading(false);

        // then verify that the error screen is shown
        verify(view).showError(anyString());
    }

    @Test
    public void navigateToComicDetails() {
        // given presenter has been attached to the view
        presenter.onAttach(view);

        // when navigate to comic details is clicked
        Comic comic = new Comic.Builder()
                .setId(1)
                .build();
        presenter.navigateToComicDetails(comic);

        // then verify that the view is called to show the details
        verify(view).showComicDetails(comic.getId());
    }

    private void setUpErrorMapper() {
        when(errorStringMapper.getErrorMessage(any(Throwable.class))).thenReturn("Error");
    }

}
