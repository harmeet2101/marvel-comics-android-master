package com.segunfamisa.sample.comics.presentation;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.segunfamisa.sample.comics.App;
import com.segunfamisa.sample.comics.R;
import com.segunfamisa.sample.comics.presentation.comiclist.ComicListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, ComicListFragment.newInstance(),
                            ComicListFragment.class.getName())
                    .commit();
        }
    }
}
