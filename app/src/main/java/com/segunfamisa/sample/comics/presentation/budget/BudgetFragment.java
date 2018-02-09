package com.segunfamisa.sample.comics.presentation.budget;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.segunfamisa.sample.comics.App;
import com.segunfamisa.sample.comics.R;
import com.segunfamisa.sample.comics.common.adapter.ComicListAdapter;
import com.segunfamisa.sample.comics.data.model.Comic;
import com.segunfamisa.sample.comics.databinding.ComicBudgetBinding;
import com.segunfamisa.sample.comics.presentation.base.BaseFragment;
import com.segunfamisa.sample.comics.presentation.budget.di.BudgetPresenterModule;
import com.segunfamisa.sample.comics.presentation.comicdetails.ComicDetailsFragment;

import java.util.List;

import javax.inject.Inject;

/**
 * Fragment to input the budget.
 */
public class BudgetFragment extends BaseFragment implements BudgetContract.View,
        ComicListAdapter.ItemClickListener {

    private ComicBudgetBinding binding;
    private ComicListAdapter adapter;

    @Inject
    BudgetPresenter presenter;

    /**
     * Creates a new instance of {@link BudgetFragment}.
     *
     * @return a budget fragment
     */
    public static Fragment newInstance() {
        return new BudgetFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        injectComponents();

        presenter.onAttach(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_budget, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupToolbar();

        setupRecyclerView();

        setupBudgetSearch();
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    private void injectComponents() {
        ((App) getActivity().getApplication())
                .getAppComponent()
                .plus(new BudgetPresenterModule(this))
                .inject(this);
    }

    private void setupToolbar() {
        binding.toolbar.setTitle(R.string.title_budget);
        binding.toolbar.setNavigationIcon(R.drawable.ic_nav_back);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    private void setupRecyclerView() {
        if (adapter == null) {
            adapter = new ComicListAdapter(this);
        }
        binding.comicList.setAdapter(adapter);
        binding.comicList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupBudgetSearch() {
        binding.buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    presenter.findComics(binding.editBudget.getText().toString());
                } catch (NumberFormatException nfe) {
                    showError(nfe.toString());
                }
            }
        });
    }

    @Override
    public void showMatchingComicList(List<Comic> comicList) {
        adapter.setComicList(comicList);
    }

    @Override
    public void showError(String error) {
        Snackbar.make(binding.comicList, error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showTotalComicPagesCountForBudget(int pagesCount) {
        String text = getString(R.string.budget_prompt_page_count,
                binding.editBudget.getText().toString(), pagesCount);
        binding.pageCountText.setText(text);
    }

    @Override
    public void showNoMatchingComic(boolean noMatchingComic) {
        if (noMatchingComic) {
            Snackbar.make(binding.comicList, R.string.error_no_match, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showLoading(boolean isLoading) {
        binding.loadingProgress.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showComicDetails(long comicId) {
        startFragment(ComicDetailsFragment.newInstance(comicId));
    }

    @Override
    public void onItemClicked(Comic comic) {
        presenter.navigateToComicDetails(comic);
    }
}
