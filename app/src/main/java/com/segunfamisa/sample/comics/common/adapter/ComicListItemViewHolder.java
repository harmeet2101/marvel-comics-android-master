package com.segunfamisa.sample.comics.common.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.segunfamisa.sample.comics.data.model.Comic;
import com.segunfamisa.sample.comics.databinding.ComicListItemBinding;

/**
 * Comic item view holder.
 */
public class ComicListItemViewHolder extends RecyclerView.ViewHolder {

    private ComicListItemBinding binding;

    /**
     * Creates a new comic list item view holder.
     *
     * @param binding - binding
     */
    public ComicListItemViewHolder(ComicListItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    /**
     * Bind comic item to the view holder.
     *
     * @param comic - comic
     * @param listener - listener
     */
    public void bind(final Comic comic, final ComicListAdapter.ItemClickListener listener) {
        binding.comicTitle.setText(comic.getTitle());

        // load image
        Glide.with(binding.comicImage.getContext())
                .load(comic.getThumbnail().getUrl())
                .into(binding.comicImage);

        // set listeners
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClicked(comic);
                }
            }
        });

        binding.executePendingBindings();
    }
}
