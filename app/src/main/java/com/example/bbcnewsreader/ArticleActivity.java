package com.example.bbcnewsreader;

import android.os.Bundle;

public class ArticleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle dataToPass = getIntent().getExtras();

        ArticleFragment aFragment = new ArticleFragment();
        aFragment.setArguments(dataToPass);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, aFragment)
                .commit();
    }
}