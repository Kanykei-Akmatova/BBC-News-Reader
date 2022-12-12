package com.example.bbcnewsreader;

import android.os.Bundle;

public class NewsTextActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle dataToPass = getIntent().getExtras();

        NewsArticleFragment aFragment = new NewsArticleFragment();
        aFragment.setArguments(dataToPass);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, aFragment)
                .commit();
    }
}