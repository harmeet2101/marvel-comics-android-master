package com.segunfamisa.sample.comics.presentation.widget;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.segunfamisa.sample.comics.R;

/**
 * Cell to set the summary text from a list of items.
 */
public class SummaryCellView extends FrameLayout {

    private TextView contentText;

    public SummaryCellView(Context context) {
        super(context);
        init();
    }

    public SummaryCellView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SummaryCellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = inflate(getContext(), R.layout.item_view_summary_cell, this);
        contentText = (TextView) view.findViewById(R.id.content_text);
    }

    public void setText(String text) {
        contentText.setText(text);
    }
}
