package com.segunfamisa.sample.comics.util;

import com.segunfamisa.sample.comics.data.model.Comic;
import com.segunfamisa.sample.comics.data.model.ComicPrice;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class BudgetCalculatorTest {

    @Test
    public void calculate() {
        // given comic books
        List<Comic> comicList = given_comic_list();

        BudgetCalculator calculator = new BudgetCalculator();

        // when we calculate
        Map<Integer, List<Comic>> results = calculator.calculate(5, comicList);

        // then we want to verify that the results returned contains
        // items 1 & 4 with prices $3 and $1 giving us 140 pages
        int pageCount = (int) results.keySet().toArray()[0];
        List<Comic> comics = results.get(pageCount);
        assertEquals(140, pageCount);
        assertEquals(4, comics.get(0).getId());
        assertEquals(1, comics.get(1).getId());
    }

    private List<Comic> given_comic_list() {
        List<Comic> items = new ArrayList<>();
        // first item with id 1, price $3 and page count 100
        items.add(new Comic.Builder()
                .setId(1)
                .setPrices(convertPriceToPriceList(3))
                .setPageCount(100)
                .build());

        // second item with id 2, price $2 and page count 20
        items.add(new Comic.Builder()
                .setId(2)
                .setPrices(convertPriceToPriceList(2))
                .setPageCount(20)
                .build());

        // third item with id 3, price $4 and page count 60
        items.add(new Comic.Builder()
                .setId(3)
                .setPrices(convertPriceToPriceList(4))
                .setPageCount(60)
                .build());

        // fourth item with id 4, price $1 and page count 40
        items.add(new Comic.Builder()
                .setId(4)
                .setPrices(convertPriceToPriceList(1))
                .setPageCount(40)
                .build());

        return items;
    }

    private List<ComicPrice> convertPriceToPriceList(double price) {
        ComicPrice comicPrice = new ComicPrice.Builder()
                .setPrice(price)
                .setType("any")
                .build();
        List<ComicPrice> priceList = new ArrayList<>();
        priceList.add(comicPrice);
        return priceList;
    }

}