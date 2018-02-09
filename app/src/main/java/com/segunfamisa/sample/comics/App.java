package com.segunfamisa.sample.comics;

import android.app.Application;

import com.segunfamisa.sample.comics.di.AppComponent;
import com.segunfamisa.sample.comics.di.AppModule;
import com.segunfamisa.sample.comics.di.DaggerAppComponent;
import com.segunfamisa.sample.comics.util.RealmHelper;


public class App extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        buildAppComponent();

        // init realm
        RealmHelper.init(this);
    }

    private void buildAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
