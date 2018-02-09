package com.segunfamisa.sample.comics.util;


import com.segunfamisa.sample.comics.data.model.Comic;
import com.segunfamisa.sample.comics.data.model.ComicPrice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Calculates the budget.
 */
public class BudgetCalculator {

    /**
     * Create a new calculator based on the budget and the list of items.
     */
    @Inject
    public BudgetCalculator() {
    }

    /**
     * Calculate the optimal comic books that can be bought for budget USD while
     *      maximizing the number of pages to be read.
     *
     * @return a map containing a single k,v pair of the max spending and the list of the
     *      matching comic books.
     */
    public Map<Integer, List<Comic>> calculate(int budget, List<Comic> items) {
        return solveKnapsack(budget, items);
    }

    /**
     * Solve this problem treating it as a 0/1 knapsack problem.
     *
     * @param budget    - in cents
     * @param comicList - comic list
     */
    private Map<Integer, List<Comic>> solveKnapsack(int budget, List<Comic> comicList) {
        comicList = new ArrayList<>(comicList);
        comicList.add(0, getZerothComicItem());

        int comicCount = comicList.size();
        int[][] pages = new int[comicCount + 1][budget + 1];
        int comicIndex;
        int price;

        // fill in the cases where we don't have any item or we don't have budget
        // base case of the knapsack problem
        for (price = 0; price <= budget; price++) {
            pages[0][price] = 0;
        }
        for (comicIndex = 0; comicIndex <= comicCount; comicIndex++) {
            pages[comicIndex][0] = 0;
        }

        // fill in the table for the dynamic program
        for (comicIndex = 1; comicIndex <= comicCount; comicIndex++) {
            for (price = 1; price <= budget; price++) {
                Comic comic = comicList.get(comicIndex - 1);
                int comicPrice = (int) (comic.getLeastPrice());
                if (comicPrice <= price) {
                    pages[comicIndex][price] = Math.max(pages[comicIndex - 1][price],
                            comic.getPageCount() + pages[comicIndex - 1][price - comicPrice]);
                } else {
                    pages[comicIndex][price] = pages[comicIndex - 1][price];
                }
            }
        }

        // maximum page count that can fit within the budget.
        final int maxPageCount = pages[comicCount][budget];

        // find the items that match
        comicIndex = comicCount;
        price = budget;
        List<Comic> results = new ArrayList<>();

        while (comicIndex > 0 && price > 0) {
            Comic comic = comicList.get(comicIndex - 1);

            int comicPrice = (int) (comic.getLeastPrice());
            if (pages[comicIndex][price] != pages[comicIndex - 1][price]) {
                results.add(comic);
                price = price - comicPrice;
                comicIndex--;
            } else {
                comicIndex--;
            }
        }

        Map<Integer, List<Comic>> map = new HashMap<>();
        map.put(maxPageCount, results);
        return map;
    }

    /**
     * Creates an "empty" comic item with price 0 and budget 0.
     *
     * @return - the empty comic item to bias the list.
     */
    private Comic getZerothComicItem() {
        ComicPrice comicPrice = new ComicPrice.Builder()
                .setPrice(0)
                .setType("any")
                .build();
        List<ComicPrice> priceList = new ArrayList<>();
        priceList.add(comicPrice);

        return new Comic.Builder()
                .setPrices(priceList)
                .setPageCount(0)
                .build();
    }
}
