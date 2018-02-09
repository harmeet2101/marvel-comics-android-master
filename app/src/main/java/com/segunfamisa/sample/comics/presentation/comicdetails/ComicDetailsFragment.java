package com.segunfamisa.sample.comics.presentation.comicdetails;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.segunfamisa.sample.comics.App;
import com.segunfamisa.sample.comics.R;
import com.segunfamisa.sample.comics.data.model.ComicCreators;
import com.segunfamisa.sample.comics.data.model.ComicPrice;
import com.segunfamisa.sample.comics.data.model.CreatorSummary;
import com.segunfamisa.sample.comics.databinding.ComicDetailsBinding;
import com.segunfamisa.sample.comics.presentation.base.BaseFragment;
import com.segunfamisa.sample.comics.presentation.comicdetails.di.ComicDetailsPresenterModule;
import com.segunfamisa.sample.comics.presentation.widget.SummaryCellView;
import com.segunfamisa.sample.comics.util.ListUtils;
import com.segunfamisa.sample.comics.util.StringUtils;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

/**
 * Fragment for comic details.
 */
public class ComicDetailsFragment extends BaseFragment implements ComicDetailsContract.View {

    private static final String ARG_COMIC_ID = "arg_comic_id";

    private ComicDetailsBinding binding;
    private long comicId;

    @Inject
    ComicDetailsPresenter presenter;

    /**
     * Creates a new instance of {@link ComicDetailsFragment}.
     *
     * @param comicId - comic id
     * @return a fragment instance with comic id set in the arguments;
     */
    public static Fragment newInstance(long comicId) {
        Fragment frag = new ComicDetailsFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_COMIC_ID, comicId);
        frag.setArguments(args);
        return frag;
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
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_comic_details, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupToolbar();

        comicId = retrieveComicId(savedInstanceState);

        fetchComic(comicId);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong(ARG_COMIC_ID, comicId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    private void injectComponents() {
        ((App) getActivity().getApplication()).getAppComponent()
                .plus(new ComicDetailsPresenterModule(this))
                .inject(this);
    }

    private long retrieveComicId(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return getArguments().getLong(ARG_COMIC_ID, -1);
        } else {
            return savedInstanceState.getLong(ARG_COMIC_ID, -1);
        }
    }

    private void fetchComic(long comicId) {
        presenter.fetchComicDetails(comicId);
    }

    private void setupToolbar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_nav_back);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void showTitle(String title) {
        binding.comicTitle.setText(title);
    }

    @Override
    public void showDescription(String description) {
        if (StringUtils.isNullOrEmpty(description)) {
            binding.comicDescriptionPrompt.setVisibility(View.GONE);
            binding.comicDescription.setVisibility(View.GONE);
        } else {
            binding.comicDescriptionPrompt.setVisibility(View.VISIBLE);
            binding.comicDescription.setVisibility(View.VISIBLE);
            binding.comicDescription.setText(description);
        }
    }

    @Override
    public void showPageCount(String pageCount) {
        binding.comicPageCount.setText(pageCount);
    }

    @Override
    public void showPrices(List<ComicPrice> prices) {
        if (ListUtils.isNullOrEmpty(prices)) {
            binding.comicPricesPrompt.setVisibility(View.GONE);
            binding.comicPrices.setVisibility(View.GONE);
        } else {
            binding.comicPricesPrompt.setVisibility(View.VISIBLE);
            binding.comicPrices.setVisibility(View.VISIBLE);
            binding.comicPrices.removeAllViews();
            for (ComicPrice price : prices) {
                SummaryCellView cell = new SummaryCellView(getContext());
                cell.setText(String.format(Locale.getDefault(),
                        "$%.2f - %s", price.getPrice(), price.getType()));
                binding.comicPrices.addView(cell);
            }
        }
    }

    @Override
    public void showAuthors(ComicCreators authors) {
        if (ListUtils.isNullOrEmpty(authors.getItems())) {
            binding.comicAuthors.setVisibility(View.GONE);
            binding.comicAuthorsPrompt.setVisibility(View.GONE);
        } else {
            binding.comicAuthors.setVisibility(View.VISIBLE);
            binding.comicAuthorsPrompt.setVisibility(View.VISIBLE);
            binding.comicAuthors.removeAllViews();
            for (CreatorSummary summary : authors.getItems()) {
                SummaryCellView cell = new SummaryCellView(getContext());
                cell.setText(summary.getName() + " - " + summary.getRole());
                binding.comicAuthors.addView(cell);
            }
        }
    }

    @Override
    public void showComicImage(String imageUrl) {
        Glide.with(getContext())
                .load(imageUrl)
                .into(binding.comicImage);
    }

    @Override
    public void showLoading(boolean isLoading) {
        binding.loadingProgress.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showError(String error) {
        Snackbar.make(binding.scrollContent, error, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.error_action_retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.fetchComicDetails(comicId);
                    }
                }).show();
    }
}
