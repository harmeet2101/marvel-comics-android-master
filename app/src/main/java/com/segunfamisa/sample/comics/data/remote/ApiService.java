package com.segunfamisa.sample.comics.data.remote;

import com.segunfamisa.sample.comics.BuildConfig;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * API service to interact with the API.
 */
public interface ApiService {

    String BASE_URL = "http://gateway.marvel.com/v1/public/";
    String DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ssZ"; //2029-12-31T00:00:00-0500
    String PUBLIC_KEY =  "5bd6e7afb05ca3bcb483fe2ff9b93f94"/*BuildConfig.PUBLIC_API_KEY*/;
    String PRIVATE_KEY = "62ecaa977670d3d8d658d7c42358c8b26e5c77f6"/*BuildConfig.PRIVATE_API_KEY*/;
    int DEFAULT_LIMIT = 20000;

    /**
     * Fetches lists of comics with filters.
     *
     * @param limit - limit of the responses to return
     * @param timeStamp - timestamp of the request
     * @param apiKey - public api key
     * @param hash - hash of the timestamp+private_key+public_key
     *
     * @return an observable of {@link ComicDataResponse} containing a list of comics matching the
     *          filters
     */
    @GET("comics")
    Observable<ComicDataResponse> getComics(@Query("limit") long limit,
                                            @Query("ts") String timeStamp,
                                            @Query("apikey") String apiKey,
                                            @Query("hash") String hash);

    /**
     * This method fetches a single comic resource.
     *
     * @param comicId - id of the comic resource to fetch
     * @param timeStamp - timestamp of the request
     * @param apiKey - public api key
     * @param hash - hash of the timestamp+public_key+private_key
     *
     * @return an observable of {@link ComicDataResponse} containing a single comic item
     */
    @GET("comics/{comicId}")
    Observable<ComicDataResponse> getComic(@Path("comicId") long comicId,
                                                  @Query("ts") String timeStamp,
                                                  @Query("apikey") String apiKey,
                                                  @Query("hash") String hash);

    /**
     * Convenience class to create API Service.
     */
    class Factory {

        public static ApiService create(String baseUrl) {
            Retrofit retrofit =  new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return retrofit.create(ApiService.class);
        }
    }
}
