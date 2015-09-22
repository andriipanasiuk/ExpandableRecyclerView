package com.andriipanasiuk.expandablerecyclerview.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.andriipanasiuk.expandablerecyclerview.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends ActionBarActivity {

    private SimpleExpandableRecyclerView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mList = (SimpleExpandableRecyclerView) findViewById(R.id.list);
        SimpleAdapter adapter = new SimpleAdapter(this);
        adapter.setData(buildData());
        mList.setAdapter(adapter);
    }

    private List<SimpleItemView.Data> buildData() {
        List<SimpleItemView.Data> result = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            SimpleItemView.Data item = new SimpleItemView.Data();
            item.name = "Developer " + NAMES[random.nextInt(NAMES.length)];
            item.repositories = new ArrayList<>();
            int repositoriesSize = random.nextInt(REPOSITORIES.length) + 1;
            for (int j = 1; j <= repositoriesSize; j++) {
                item.repositories.add(REPOSITORIES[(repositoriesSize + j) % REPOSITORIES.length]);
            }
            result.add(item);
        }
        return result;
    }

    private static final Random random = new Random(System.currentTimeMillis());
    private static final String[] NAMES = new String[]{"Andrew", "Mike", "Arnold",
            "Thomas", "Francis", "Kate", "Albert", "Nickolas", "Christian"};
    private static final String[] REPOSITORIES = new String[]{"Retrofit", "Picasso",
            "Robospice", "RxJava", "Lombok", "Hrisey", "TimesSquare", "AOSP"};
}
