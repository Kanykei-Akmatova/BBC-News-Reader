package com.example.bbcnewsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "BBCNewsReader";
    private static final String BASE_BBC_URL = "http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml";
    private ListView listView;
    private final ArrayList<RssItem> savedNewsList = new ArrayList<>();
    private SQLHelper sqlHelper;

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
        protected void onPostExecute(List<RssItem> bbcNews) {
            super.onPostExecute(bbcNews);
            ItemListAdapter adapter = new ItemListAdapter(getApplicationContext(), bbcNews);

            boolean isTablet = findViewById(R.id.frameLayout) != null;

            listView.setAdapter(adapter);
            listView.setOnItemClickListener((list, item, position, id) -> {
                Bundle dataToPass = new Bundle();
                dataToPass.putString(BBCNewsConstant.NEWS_TITLE, bbcNews.get(position).getTitle());
                dataToPass.putString(BBCNewsConstant.NEWS_DESCRIPTION, bbcNews.get(position).getDescription());
                dataToPass.putString(BBCNewsConstant.NEWS_DATE, bbcNews.get(position).getPubDate());
                dataToPass.putString(BBCNewsConstant.NEWS_LINK, bbcNews.get(position).getLink());

                Intent newsTextActivity = new Intent(MainActivity.this, NewsTextActivity.class);
                newsTextActivity.putExtras(dataToPass);
                startActivity(newsTextActivity);

                if (isTablet) {
                    NewsArticleFragment dFragment = new NewsArticleFragment();
                    dFragment.setArguments(dataToPass);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameLayout, dFragment)
                            .commit();
                } else //isPhone
                {
                    Intent nextActivity = new Intent(MainActivity.this, NewsTextActivity.class);
                    nextActivity.putExtras(dataToPass);
                    startActivity(nextActivity);
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing the sql helper
        sqlHelper = new SQLHelper(this);
        sqlHelper.getWritableDatabase();

        // Loading news
        NewsLoader newsLoader = new NewsLoader();
        newsLoader.execute(BASE_BBC_URL);

        listView = findViewById(R.id.listView);

        Cursor cursor = sqlHelper.getAll();
        Log.d(TAG, "Loading saved news");

        if (cursor.moveToFirst()) {
            do {
                savedNewsList.add(new RssItem(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4))
                );
            } while (cursor.moveToNext());

            //adapter.notifyDataSetChanged();
        }
    }
}