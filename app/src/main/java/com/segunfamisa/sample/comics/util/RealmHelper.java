package com.segunfamisa.sample.comics.util;


import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Helps to init realm.
 */
public class RealmHelper {

    private static final int schemaVersion = 1;

    /**
     * Initializes realm for use in the app.
     *
     * @param context - context
     */
    public static void init(Context context) {
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

}
