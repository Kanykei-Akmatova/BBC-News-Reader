package com.example.bbcnewsreader;

import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends BaseActivity {

    private static final String TAG = "NewsActivity";
    private static final String BASE_BBC_URL = "http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml";
    private List<RssItem> bbcNews;

    class NewsLoader extends AsyncTask<String, String, List<RssItem>> {

        @Override
        protected List<RssItem> doInBackground(String... strings) {
            while (true) {
                HttpURLConnection urlConnection = null;
                String bbcURL = strings[0];
                try {
                    Log.d(TAG, "Loading news from internet.");
                    return RssFeedProvider.get(bbcURL);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(List<RssItem> news) {
            super.onPostExecute(news);
            bbcNews = news;
            Bundle dataToPass = new Bundle();
            dataToPass.putSerializable(NewsConstant.NEWS_LIST, (ArrayList<RssItem>) news);

            NewsFragment fragment = new NewsFragment();
            fragment.setArguments(dataToPass);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, fragment)
                    .commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Loading news
        NewsLoader newsLoader = new NewsLoader();
        newsLoader.execute(BASE_BBC_URL);
    }

    public void filter(String filter) {
        List<RssItem> filteredNews = new ArrayList<>();

        if(filter.trim().length() > 0) {
            for (RssItem rssItem : bbcNews) {
                int index = rssItem.getTitle().toLowerCase().indexOf(filter);
                if(index > 0) {
                    filteredNews.add(rssItem);
                }
            }
        } else {
            filteredNews = bbcNews;
        }

        Bundle dataToPass = new Bundle();
        dataToPass.putSerializable(NewsConstant.NEWS_LIST, (ArrayList<RssItem>) filteredNews);

        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(dataToPass);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }
}