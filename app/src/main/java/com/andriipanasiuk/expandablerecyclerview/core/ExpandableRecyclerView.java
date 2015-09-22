package com.andriipanasiuk.expandablerecyclerview.core;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;

/**
 * Created by andriipanasiuk on 19.09.15.
 */
public abstract class ExpandableRecyclerView<V extends View, EV extends ExpandableView<V>,
        EVH extends ExpandableHolder<V, EV>> extends FrameLayout implements ExpandableAdapter.onToggleListener {

    protected abstract V createMeasurableView(Context context, ViewGroup parent);

    private RecyclerView mRecyclerView;

    private LinearLayoutManager mLayoutManager;
    private V mMeasurableView;

    public ExpandableRecyclerView(Context context, AttributeSet set) {
        super(context, set);
        ScrollView scrollView = new ScrollView(context);
        mMeasurableView = createMeasurableView(context, scrollView);
        ViewGroup.LayoutParams measurableParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        scrollView.addView(mMeasurableView, measurableParams);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(scrollView, params);
        scrollView.setVisibility(INVISIBLE);
        LayoutParams recyclerViewParams = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mRecyclerView = new RecyclerView(context);
        addView(mRecyclerView, recyclerViewParams);
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    public void setAdapter(ExpandableAdapter<V, EV, EVH> adapter) {
        adapter.mMeasurableView = mMeasurableView;
        adapter.mOnToggleListener = this;
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onToggle(int position, View itemVIew) {
        Animator recyclerViewAnimator = AnimatorHelper.createRecyclerViewAnimator(mLayoutManager, itemVIew, position);
        recyclerViewAnimator.start();
    }
}
