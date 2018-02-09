package com.segunfamisa.sample.comics.data.remote;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Locale;

import io.reactivex.observers.TestObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okio.BufferedSource;
import okio.Okio;

import static org.junit.Assert.assertEquals;

public class ApiServiceTest {

    private ApiService apiService;
    private MockWebServer mockWebServer;

    @Before
    public void setUp() {
        mockWebServer = new MockWebServer();
        apiService = ApiService.Factory.create(mockWebServer.url("/").toString());
    }

    /**
     * Test that the API service can get a list of comics.
     *
     * @throws IOException - IOException
     * @throws InterruptedException - InterruptedException
     */
    @Test
    public void getComics() throws IOException, InterruptedException {
        // given that the mock web server returns the comics-response.json response
        enqueueResponse("comics-response.json");

        // given the following parameters
        int limit = 20;
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String apiKey = "api-key";
        String hash = "hash";

        // when api service is called to get comics
        TestObserver<ComicDataResponse> testObserver = new TestObserver<>();
        apiService.getComics(limit, timeStamp, apiKey, hash).subscribe(testObserver);

        // then assert that the path is as expected
        RecordedRequest request = mockWebServer.takeRequest();

        String expectedPath = String.format(Locale.getDefault(),
                "/comics?limit=%d&ts=%s&apikey=%s&hash=%s", limit, timeStamp, apiKey, hash);
        assertEquals(expectedPath, request.getPath());

        // then assert that the number of comics returned is 20 (as per the json file)
        ComicDataResponse response = testObserver.values().get(0);
        assertEquals(20, response.getData().getResults().size());
    }

    /**
     * Test that the API service can get a single comic resource.
     */
    @Test
    public void getComic() throws IOException, InterruptedException {
        // given that the mock web server returns the single comic resource
        enqueueResponse("single-comic-response.json");

        // given the following parameters
        long comicId = 1234;
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String apiKey = "api-key";
        String hash = "hash";

        // when api service is called to get single comic
        TestObserver<ComicDataResponse> testObserver = new TestObserver<>();
        apiService.getComic(comicId, timeStamp, apiKey, hash).subscribe(testObserver);

        // then assert that the request path is as expected
        RecordedRequest request = mockWebServer.takeRequest();
        String expectedPath = String.format(Locale.getDefault(),
                "/comics/%d?ts=%s&apikey=%s&hash=%s", comicId, timeStamp, apiKey, hash);
        assertEquals(expectedPath, request.getPath());

        // then assert that the number of comics returned is 1
        ComicDataResponse response = testObserver.values().get(0);
        assertEquals(1, response.getData().getResults().size());
    }

    @After
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    private void enqueueResponse(String fileName) throws IOException {
        InputStream in = getClass().getClassLoader()
                .getResourceAsStream("api-responses/" + fileName);
        BufferedSource source = Okio.buffer(Okio.source(in));
        MockResponse mockResponse = new MockResponse();
        mockResponse.setBody(source.readString(Charset.defaultCharset()));
        mockWebServer.enqueue(mockResponse);
    }
}