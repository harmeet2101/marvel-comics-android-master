package com.segunfamisa.sample.comics.di;


import android.content.Context;

import com.segunfamisa.sample.comics.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * App module defining how to provide app context.
 */
@Module
public class AppModule {

    private final App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    App provideApplication() {
        return app;
    }

    @Singleton
    @Provides
    Context provideContext() {
        return app.getApplicationContext();
    }

}
