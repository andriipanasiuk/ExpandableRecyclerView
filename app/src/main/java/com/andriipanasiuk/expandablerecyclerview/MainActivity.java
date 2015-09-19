package com.andriipanasiuk.expandablerecyclerview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    private RecyclerView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mList = (RecyclerView) findViewById(R.id.list);
        mList.setLayoutManager(new LinearLayoutManager(this));
        mList.setAdapter(new Adapter(this));
    }

    public static class Holder extends RecyclerView.ViewHolder {
        private TextView mText;

        public Holder(View itemView) {
            super(itemView);
            mText = (TextView) itemView.findViewById(R.id.text);
        }
    }

    public static class Adapter extends RecyclerView.Adapter<Holder> {

        private Context mContext;

        public Adapter(Context context) {
            this.mContext = context;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new Holder(LayoutInflater.from(mContext).inflate(R.layout.item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(Holder holder, int i) {
            holder.mText.setText("Position number " + i);
        }

        @Override
        public int getItemCount() {
            return 100;
        }
    }
}
