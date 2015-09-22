package com.andriipanasiuk.expandablerecyclerview.sample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.andriipanasiuk.expandablerecyclerview.R;
import com.andriipanasiuk.expandablerecyclerview.core.ExpandableAdapter;

import java.util.List;

/**
 * Created by andriipanasiuk on 19.09.15.
 */
public class SimpleAdapter extends ExpandableAdapter<SimpleItemView, SimpleItemView, SimpleItemHolder> {

    private List<SimpleItemView.Data> mData;

    private Context mContext;

    public SimpleAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<SimpleItemView.Data> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    protected boolean isExpandable(int viewType) {
        return true;
    }

    @Override
    protected SimpleItemHolder onCreateExpandableViewHolder(ViewGroup parent, int viewType) {
        SimpleItemView itemView = (SimpleItemView) LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        return new SimpleItemHolder(itemView);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateUnexpandableViewHolder(ViewGroup parent, int viewType) {
        throw new RuntimeException("Unreachable cause we don't have nonexpandableitems");
    }

    @Override
    protected void onBindExpandableViewHolder(SimpleItemHolder holder, boolean isExpanded, int height, int position) {
        holder.getView().bind(mData.get(position), isExpanded, height);
    }

    @Override
    protected void onBindUnexpandableViewHolder(RecyclerView.ViewHolder holder, int position) {
        throw new RuntimeException("Unreachable cause we don't have nonexpandableitems");
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
