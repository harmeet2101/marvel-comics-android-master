package com.segunfamisa.sample.comics.data.remote;

import com.segunfamisa.sample.comics.data.ComicDataSource;
import com.segunfamisa.sample.comics.data.TestDataGenerator;
import com.segunfamisa.sample.comics.data.model.Comic;
import com.segunfamisa.sample.comics.util.HashCalculator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test remote data source functions.
 */
public class RemoteDataSourceTest {

    @Mock
    private ApiService apiService;

    @Mock
    private HashCalculator hashCalculator;

    @Mock
    private TimeStampProvider timeStampProvider;

    private ComicDataResponse comicListResponse;
    private ComicDataResponse singleComicResponse;
    private ComicDataSource remoteDataSource;

    /**
     * Setup.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        comicListResponse = TestDataGenerator.getComicDataResponseForListOfComics();
        singleComicResponse = TestDataGenerator.getComicDataResponseForSingleComic();
        remoteDataSource = new RemoteComicDataSource(apiService, hashCalculator, timeStampProvider);
    }

    @Test
    public void getComics() {
        // given the api service is setup to return comics
        given_apiServiceReturnsComics();

        // given the following params
        final long limit = ApiService.DEFAULT_LIMIT;
        final String timeStamp = String.valueOf(System.currentTimeMillis());
        final String apiKey = ApiService.PUBLIC_KEY;
        final String hash = "hash";

        // given the time stamp provider returns known value
        given_timeStampProviderReturnsTimeStamp(timeStamp);

        // given hash calculator returns hash
        given_hashCalculatorReturnsHash(hash);

        final TestObserver<List<Comic>> testObserver = new TestObserver<>();

        // when we call remote data source to get comics
        remoteDataSource.getComics().subscribe(testObserver);

        // then verify that the api service is called
        verify(apiService).getComics(limit, timeStamp, apiKey, hash);

        // then assert that the observed items are the same as the given ones
        List<Comic> comics = testObserver.values().get(0);
        assertEquals(this.comicListResponse.getData().getResults(), comics);
    }

    @Test
    public void getComic() {
        // given the api returns a single comic
        given_apiServiceReturnsSingleComic();

        // given the following params
        final long comicId = 1;
        final String timeStamp = String.valueOf(System.currentTimeMillis());
        final String apiKey = ApiService.PUBLIC_KEY;
        final String hash = "singlehash";

        // given the time stamp provider returns timeStamp
        given_timeStampProviderReturnsTimeStamp(timeStamp);

        // given the hash calculator returns hash
        given_hashCalculatorReturnsHash(hash);

        final TestObserver<Comic> testObserver = new TestObserver<>();

        // when we call remote data source to get single comic detail
        remoteDataSource.getComic(comicId).subscribe(testObserver);

        // then verify that the api service is called with the expected parameters
        verify(apiService).getComic(comicId, timeStamp, apiKey, hash);

        // then assert that the observed items are the same as the given ones
        Comic comic = testObserver.values().get(0);
        assertEquals(this.singleComicResponse.getData().getResults().get(0), comic);
    }

    private void given_timeStampProviderReturnsTimeStamp(String timeStamp) {
        when(timeStampProvider.getTimeStamp()).thenReturn(timeStamp);
    }

    private void given_hashCalculatorReturnsHash(String hash) {
        when(hashCalculator.calculate(ArgumentMatchers.<String>any())).thenReturn(hash);
    }

    private void given_apiServiceReturnsComics() {
        when(apiService.getComics(anyLong(), anyString(), anyString(), anyString()))
                .thenReturn(Observable.just(comicListResponse));
    }

    private void given_apiServiceReturnsSingleComic() {
        when(apiService.getComic(anyLong(), anyString(), anyString(), anyString()))
                .thenReturn(Observable.just(singleComicResponse));
    }
}
