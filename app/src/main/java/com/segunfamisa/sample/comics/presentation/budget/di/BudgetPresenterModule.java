package com.segunfamisa.sample.comics.presentation.budget.di;


import com.segunfamisa.sample.comics.presentation.budget.BudgetContract;

import dagger.Module;
import dagger.Provides;

@Module
public class BudgetPresenterModule {

    private final BudgetContract.View view;

    public BudgetPresenterModule(BudgetContract.View view) {
        this.view = view;
    }

    @Provides
    BudgetContract.View provideBudgetView() {
        return view;
    }
}
