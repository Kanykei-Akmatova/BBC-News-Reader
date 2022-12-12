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
    private final ArrayList<RssItem> savedNewsList = new ArrayList<>();
    private SQLHelper sqlHelper;
    private List<RssItem> bbcNews = new ArrayList<>();

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

        //initializing the sql helper
        sqlHelper = new SQLHelper(this);
        sqlHelper.getWritableDatabase();

        // Loading news
        NewsLoader newsLoader = new NewsLoader();
        newsLoader.execute(BASE_BBC_URL);

//
//        Cursor cursor = sqlHelper.getAll();
//        Log.d(TAG, "Loading saved news");
//
//        if (cursor.moveToFirst()) {
//            do {
//                savedNewsList.add(new RssItem(
//                        cursor.getString(1),
//                        cursor.getString(2),
//                        cursor.getString(3),
//                        cursor.getString(4))
//                );
//            } while (cursor.moveToNext());
//
//            //adapter.notifyDataSetChanged();
//        }

    }
}