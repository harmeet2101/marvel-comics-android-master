package com.segunfamisa.sample.comics.data.di;


import com.segunfamisa.sample.comics.common.scheduler.DefaultSchedulerProvider;
import com.segunfamisa.sample.comics.common.scheduler.SchedulerProvider;
import com.segunfamisa.sample.comics.data.remote.ApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Api module to provide the api related objects.
 */
@Module
public class ApiModule {

    @Singleton
    @Provides
    ApiService provideApiService() {
        return ApiService.Factory.create(ApiService.BASE_URL);
    }

    @Singleton
    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new DefaultSchedulerProvider();
    }
}
