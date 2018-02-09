package com.segunfamisa.sample.comics.common.adapter;


import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.segunfamisa.sample.comics.R;
import com.segunfamisa.sample.comics.data.model.Comic;
import com.segunfamisa.sample.comics.databinding.ComicListItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for the comic list items.
 */
public class ComicListAdapter extends RecyclerView.Adapter<ComicListItemViewHolder> {

    public interface ItemClickListener {
        void onItemClicked(Comic comic);
    }

    private final ItemClickListener itemClickListener;
    private List<Comic> comicList;

    public ComicListAdapter(ItemClickListener itemClickListener) {
        comicList = new ArrayList<>();
        this.itemClickListener = itemClickListener;
    }

    public void setComicList(List<Comic> comicList) {
        this.comicList = comicList;
        notifyDataSetChanged();
    }

    @Override
    public ComicListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ComicListItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.item_comic_list, parent, false);
        return new ComicListItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ComicListItemViewHolder holder, int position) {
        final Comic comic = comicList.get(position);
        holder.bind(comic, itemClickListener);
    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }
}
