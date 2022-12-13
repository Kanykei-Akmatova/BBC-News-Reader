package com.example.bbcnewsreader;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * FavoriteNewsActivity provide the list of favorite news.
 * @author  Kanykei Akmatova
 * @version 1.0
 * @since   2022-12-12
 */
public class FavoriteNewsActivity extends BaseActivity {
    private static final String TAG = "FavoriteNewsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initializing the sql helper
        SQLHelper sqlHelper = new SQLHelper(this);
        sqlHelper.getWritableDatabase();
        List<RssItem> news = new ArrayList<>();

        Cursor cursor = sqlHelper.getAll();
        Log.d(TAG, "Loading Favorite News");

        if (cursor.moveToFirst()) {
            do {
                news.add(new RssItem(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5))
                );
            } while (cursor.moveToNext());
        }
        Bundle dataToPass = new Bundle();
        dataToPass.putSerializable(NewsConstant.NEWS_FAVORITE_LIST, (ArrayList<RssItem>) news);

        FavoriteNewsFragment fragment = new FavoriteNewsFragment();
        fragment.setArguments(dataToPass);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }

    /**
     * Return the help message
     */
    @Override
    public String getHelpMessage(){
        return getString(R.string.favourite_list_help_message);
    }
}