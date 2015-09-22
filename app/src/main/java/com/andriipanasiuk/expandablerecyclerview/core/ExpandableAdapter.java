package com.andriipanasiuk.expandablerecyclerview.core;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by andriipanasiuk on 19.09.15.
 */
public abstract class ExpandableAdapter<
        V extends View,
        EV extends ExpandableView<V>,
        EVH extends ExpandableHolder<V, EV>>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements View.OnClickListener {

    private ExpandableView mExpandedView;
    private int mExpandedHeight;
    private int mCollapsedHeight;
    private int mExpandedPosition;

    V mMeasurableView;

    onToggleListener mOnToggleListener;

    interface onToggleListener {
        void onToggle(int position, View itemVIew);
    }

    protected abstract boolean isExpandable(int viewType);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isExpandable(viewType)) {
            EVH expandableViewHolder = onCreateExpandableViewHolder(parent, viewType);
            expandableViewHolder.getView().getContainer().setOnClickListener(this);
            return expandableViewHolder;
        } else {
            return onCreateUnexpandableViewHolder(parent, viewType);
        }
    }

    protected abstract EVH onCreateExpandableViewHolder(ViewGroup parent, int viewType);

    protected abstract RecyclerView.ViewHolder onCreateUnexpandableViewHolder(ViewGroup parent, int viewType);

    protected abstract void onBindExpandableViewHolder(EVH holder, boolean isExpanded,
                                                       int height, int position);

    protected abstract void onBindUnexpandableViewHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (isExpandable(getItemViewType(position))) {
            EVH holderCast = (EVH) viewHolder;
            EV view = holderCast.getView();
            if (mExpandedView == view) {
                mExpandedView = null;
            }
            view.getContainer().setTag(position);
            boolean isExpanded = mExpandedPosition == position;
            onBindExpandableViewHolder(holderCast, isExpanded,
                    isExpanded ? mExpandedHeight : ViewGroup.LayoutParams.WRAP_CONTENT,
                    position);
            if (isExpanded) {
                mExpandedView = view;
                view.getExpandedStateView().setVisibility(View.VISIBLE);
                view.getCollapsedStateView().setVisibility(View.GONE);
            } else {
                view.getExpandedStateView().setVisibility(View.GONE);
                view.getCollapsedStateView().setVisibility(View.VISIBLE);
            }
        } else {
            onBindUnexpandableViewHolder(viewHolder, position);
        }
    }

    @Override
    public void onClick(final View v) {
        final ExpandableView<V> expandableView = (ExpandableView<V>) v;
        final int position = (int) expandableView.getContainer().getTag();
        if (expandableView == mExpandedView) {
            expandableView.bindViews(false);
            AnimatorHelper.crossfadeViews(expandableView.getContainer(),
                    expandableView.getCollapsedStateView(),
                    expandableView.getExpandedStateView(),
                    expandableView.getContainer().getMeasuredHeight(),
                    expandableView.getCollapsedStateView().getMeasuredHeight());
            mExpandedView = null;
            mExpandedPosition = -1;
            mOnToggleListener.onToggle(position, v);
        } else {
            expandableView.bindExpandedState(mMeasurableView);
            mMeasurableView.post(new Runnable() {
                @Override
                public void run() {
                    if (mExpandedView != null) {
                        mExpandedView.bindViews(false);
                        AnimatorHelper.crossfadeViews(mExpandedView.getContainer(),
                                mExpandedView.getCollapsedStateView(),
                                mExpandedView.getExpandedStateView(),
                                mExpandedHeight, mCollapsedHeight);
                    }
                    mExpandedHeight = mMeasurableView.getMeasuredHeight();
                    mCollapsedHeight = expandableView.getCollapsedStateView().getMeasuredHeight();
                    Log.d("Expand", "View after measure expanded: " + mExpandedHeight +

                            " collapsed height: " + mCollapsedHeight);
                    expandableView.bindViews(true);
                    AnimatorHelper.crossfadeViews(expandableView.getContainer(),
                            expandableView.getExpandedStateView(),
                            expandableView.getCollapsedStateView(),
                            mCollapsedHeight, mExpandedHeight);
                    mExpandedView = expandableView;
                    mExpandedPosition = position;
                    mOnToggleListener.onToggle(mExpandedPosition, v);
                }
            });

        }
    }
}
