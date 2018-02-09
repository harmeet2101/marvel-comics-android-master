package com.segunfamisa.sample.comics.presentation.budget.di;


import com.segunfamisa.sample.comics.data.di.ScreenScoped;
import com.segunfamisa.sample.comics.presentation.budget.BudgetFragment;

import dagger.Subcomponent;

@ScreenScoped
@Subcomponent(modules = BudgetPresenterModule.class)
public interface BudgetComponent {

    void inject(BudgetFragment fragment);

}
