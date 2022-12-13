package com.example.bbcnewsreader;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * News Adapter to bind news data to a view.
 * @author  Kanykei Akmatova
 * @version 1.0
 * @since   2022-12-12
 */
public class NewsAdapter extends BaseAdapter {
        private Context context;
        private List<RssItem> starWarItems;

        public NewsAdapter(Context context, List<RssItem> items) {
            this.context = context;
            this.starWarItems = items;
        }

        public NewsAdapter() {
        }

        @Override
        public int getCount() {
            return starWarItems.size();
        }

        @Override
        public Object getItem(int index) {
            return starWarItems.get(index);
        }

        @Override
        public long getItemId(int index) {
            return 0;
        }

        @Override
        public View getView(int index, View oldView, ViewGroup parent) {
            View newView = oldView;
            LayoutInflater inflater = LayoutInflater.from(context);

            if (newView == null) {
                newView = inflater.inflate(R.layout.news_row, parent, false);
            }

            RssItem toDoItem = (RssItem) getItem(index);

            TextView textView = newView.findViewById(R.id.newsItemTitle);
            textView.setText(toDoItem.getTitle());

            textView.setTextColor(Color.BLACK);
            textView.setBackgroundColor(Color.WHITE);

            return newView;
        }
    }


