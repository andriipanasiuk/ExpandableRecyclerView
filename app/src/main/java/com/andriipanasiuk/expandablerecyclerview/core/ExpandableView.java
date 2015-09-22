package com.andriipanasiuk.expandablerecyclerview.core;

import android.view.View;

/**
 * Created by andriipanasiuk on 19.09.15.
 */
public interface ExpandableView<T extends View> {
    View getContainer();

    View getExpandedStateView();

    View getCollapsedStateView();

    void bindViews(boolean expanded);

    void bindExpandedState(T another);
}
