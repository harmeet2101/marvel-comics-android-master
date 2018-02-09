package com.segunfamisa.sample.comics.presentation.base;


import android.support.v4.app.Fragment;

import com.segunfamisa.sample.comics.R;

public class BaseFragment extends Fragment {


    /**
     * Starts a fragment and adds it to back stack.
     *
     * @param fragment - fragment to start
     */
    public void startFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(null)
                .commit();
    }

}
