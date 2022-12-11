package com.example.bbcnewsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
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

    class NewsLoader extends AsyncTask<String,String, List<RssItem>> {

        @Override
        protected List<RssItem> doInBackground(String... strings) {
            while (true) {
                HttpURLConnection urlConnection = null;
                String bbcURL = strings[0];
                try {
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
                newsTextActivity.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(newsTextActivity);

                if(isTablet)
                {
                    NewsArticleFragment dFragment = new NewsArticleFragment();
                    dFragment.setArguments(dataToPass);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameLayout, dFragment)
                            .commit();
                }
                else //isPhone
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

        NewsLoader newsLoader = new NewsLoader();
        newsLoader.execute(BASE_BBC_URL);

        listView = findViewById(R.id.listView);
    }
}