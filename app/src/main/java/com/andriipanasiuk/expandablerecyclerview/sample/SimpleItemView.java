package com.andriipanasiuk.expandablerecyclerview.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.andriipanasiuk.expandablerecyclerview.R;
import com.andriipanasiuk.expandablerecyclerview.core.ExpandableView;

import java.util.List;

/**
 * Created by andriipanasiuk on 19.09.15.
 */
public class SimpleItemView extends FrameLayout implements ExpandableView<SimpleItemView> {

    public static class Data {
        String name;
        List<String> repositories;
    }

    private Data mData;

    private View mExpandedView;
    private TextView mExpandedContributionsText;
    private TextView mExpandedNameText;

    private View mCollapsedView;
    private TextView mCollapsedNameText;

    public SimpleItemView(Context context) {
        super(context);
    }

    public SimpleItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d("Expand", "onFinishInflate for " + this);
        mExpandedView = findViewById(R.id.expanded_container);
        mExpandedNameText = (TextView) findViewById(R.id.expanded_name);
        mExpandedContributionsText = (TextView) findViewById(R.id.contributions_text);
        mCollapsedView = findViewById(R.id.collapsed_container);
        mCollapsedNameText = (TextView) findViewById(R.id.collapsed_name);
    }

    private static String buildExpandedText(Data data) {
        StringBuilder result = new StringBuilder();
        for (String contribution : data.repositories) {
            result.append(contribution);
            result.append("\n");
        }
        result.delete(result.length() - 1, result.length());
        return result.toString();
    }

    private static void bindExpanded(Data data, SimpleItemView view) {
        view.mExpandedContributionsText.setText(buildExpandedText(data));
        view.mExpandedNameText.setText(data.name);
    }

    public void bind(Data data, boolean isExpanded, int height) {
        this.mData = data;
        if (isExpanded) {
            bindExpanded(data, this);
        } else {
            bindCollapsed(data);
        }
        getLayoutParams().height = height;
    }

    private void bindCollapsed(Data data) {
        mCollapsedNameText.setText(data.name);
    }

    @Override
    public View getContainer() {
        return this;
    }

    @Override
    public View getExpandedStateView() {
        return mExpandedView;
    }

    @Override
    public View getCollapsedStateView() {
        return mCollapsedView;
    }

    @Override
    public void bindViews(boolean expanded) {
        if (expanded) {
            bindExpanded(mData, this);
        } else {
            bindCollapsed(mData);
        }
    }

    @Override
    public void bindExpandedState(SimpleItemView another) {
        another.mExpandedView.setVisibility(VISIBLE);
        another.mCollapsedView.setVisibility(GONE);
        bindExpanded(mData, another);
        another.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
    }
}
