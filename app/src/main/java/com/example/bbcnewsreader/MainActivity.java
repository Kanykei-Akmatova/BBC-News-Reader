package com.example.bbcnewsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

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
            listView.setAdapter(adapter);
            //boolean isTablet = findViewById(R.id.frameLayout) != null;
            listView.setOnItemClickListener((list, item, position, id) -> {
                Bundle dataToPass = new Bundle();
                dataToPass.putString(BBCNewsConstant.NEWS_TITLE, bbcNews.get(position).getTitle());
                dataToPass.putString(BBCNewsConstant.NEWS_LINK, bbcNews.get(position).getLink());

                Intent newsTextActivity = new Intent(MainActivity.this, NewsTextActivity.class);
                newsTextActivity.putExtras(dataToPass);
                startActivity(newsTextActivity);

//                if(isTablet)
//                {
//                    DetailsFragment dFragment = new DetailsFragment();
//                    dFragment.setArguments(dataToPass);
//                    getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.frameLayout, dFragment)
//                            .commit();
//                }
//                else //isPhone
//                {
//                    Intent nextActivity = new Intent(MainActivity.this, EmptyActivity.class);
//                    nextActivity.putExtras(dataToPass);
//                    startActivity(nextActivity);
//                }

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