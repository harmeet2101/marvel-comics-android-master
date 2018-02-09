package com.segunfamisa.sample.comics.data;


import com.google.gson.Gson;
import com.segunfamisa.sample.comics.data.model.Comic;
import com.segunfamisa.sample.comics.data.remote.ComicDataResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TestDataGenerator {

    public static List<Comic> getComics() {
        ComicDataResponse response = getComicDataResponseForListOfComics();
        return response == null ? new ArrayList<Comic>() : response.getData().getResults();
    }

    public static Comic getComic() {
        return getComicDataResponseForSingleComic().getData().getResults().get(0);
    }

    /**
     * Get {@link ComicDataResponse} for list of comics.
     * @return - ComicDataResponse object
     */
    public static ComicDataResponse getComicDataResponseForListOfComics() {
        Gson gson = new Gson();
        return gson.fromJson(readJsonResponse("comics-response.json"),
                ComicDataResponse.class);
    }

    /**
     * Get {@link ComicDataResponse} for single comic.
     * @return - ComicDataResponse object
     */
    public static ComicDataResponse getComicDataResponseForSingleComic() {
        Gson gson = new Gson();
        return gson.fromJson(readJsonResponse("single-comic-response.json"),
                ComicDataResponse.class);
    }

    private static String readJsonResponse(String fileName) {
        StringBuilder response = new StringBuilder();
        InputStream in = TestDataGenerator.class.getClassLoader()
                .getResourceAsStream("api-responses/" + fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return response.toString();
    }
}
