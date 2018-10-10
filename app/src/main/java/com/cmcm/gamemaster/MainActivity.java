package com.cmcm.gamemaster;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import com.cmcm.glide370.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {
    private BaseAdapter adapter;
    private List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<File> samples = Utils.extractSampleImages(this);

        items = new ArrayList<>(samples.size());
        for (File sample : samples) {
            Item item = new Item();
            item.icon = BitmapFactory.decodeFile(sample.getAbsolutePath());
            items.add(item);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final List<Item> newItems = new ArrayList<>(samples.size());
                for (int i = samples.size() - 1; i >= 0; i--) {
                    File sample = samples.get(i);
                    Item item = new Item();
                    item.file = sample;
                    newItems.add(item);
                }
                items = newItems;
                adapter.notifyDataSetChanged();
            }
        }, TimeUnit.SECONDS.toMillis(2));

        GridView gridView = (GridView) findViewById(R.id.gridview);
        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return items.size();
            }

            @Override
            public Item getItem(int position) {
                return items.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final ViewHolder holder;
                if (isNotConvertView(convertView)) {
                    convertView = View.inflate(MainActivity.this, R.layout.img_item, null);

                    holder = new ViewHolder();
                    holder.bindView(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                Item sample = getItem(position);
                holder.bindData(MainActivity.this, sample);

                return convertView;
            }

            private boolean isNotConvertView(View convertView) {
                return convertView == null || convertView.getTag() == null;
            }
        };
        gridView.setAdapter(adapter);
    }
}
