package com.segunfamisa.sample.comics.presentation.comiclist;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.segunfamisa.sample.comics.App;
import com.segunfamisa.sample.comics.R;
import com.segunfamisa.sample.comics.common.adapter.ComicListAdapter;
import com.segunfamisa.sample.comics.data.model.Comic;
import com.segunfamisa.sample.comics.databinding.ComicListBinding;
import com.segunfamisa.sample.comics.presentation.base.BaseFragment;
import com.segunfamisa.sample.comics.presentation.budget.BudgetFragment;
import com.segunfamisa.sample.comics.presentation.comicdetails.ComicDetailsFragment;
import com.segunfamisa.sample.comics.presentation.comiclist.di.ComicListPresenterModule;

import java.util.List;

import javax.inject.Inject;

/**
 * Fragment for the Comic List.
 */
public class ComicListFragment extends BaseFragment implements ComicListContract.View,
        ComicListAdapter.ItemClickListener {

    private ComicListBinding binding;
    private ComicListAdapter adapter;

    @Inject
    ComicListPresenter presenter;

    public static Fragment newInstance() {
        return new ComicListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        injectComponents();

        presenter.onAttach(this);
    }

    private void injectComponents() {
        ((App) getActivity().getApplication())
                .getAppComponent()
                .plus(new ComicListPresenterModule(this))
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comic_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupToolbar();
        setupRecyclerView();
        presenter.fetchComicList(true);
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    private void setupToolbar() {
        binding.toolbar.setTitle(R.string.app_name);
        binding.toolbar.inflateMenu(R.menu.comic_list_menu);
        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.menu_budget) {
                    presenter.navigateToBudgetScreen();
                    return true;
                }
                return false;
            }
        });
    }

    private void setupRecyclerView() {
        if (adapter == null) {
            adapter = new ComicListAdapter(this);
        }
        binding.comicList.setAdapter(adapter);
        binding.comicList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refreshComicList();
            }
        });
    }

    @Override
    public void setLoading(boolean loading) {
        binding.loadingProgress.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        binding.refresh.setRefreshing(refreshing);
    }

    @Override
    public void showComicDetails(long comicId) {
        startFragment(ComicDetailsFragment.newInstance(comicId));
    }

    @Override
    public void showComicList(List<Comic> comicList) {
        adapter.setComicList(comicList);
    }

    @Override
    public void showLoadingError(String error) {
        Snackbar.make(binding.comicList, error, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.error_action_retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.fetchComicList(true);
                    }
                }).show();
    }

    @Override
    public void showComicBudgetScreen() {
        startFragment(BudgetFragment.newInstance());
    }

    @Override
    public void onItemClicked(Comic comic) {
        presenter.navigateToComicDetails(comic);
    }
}
