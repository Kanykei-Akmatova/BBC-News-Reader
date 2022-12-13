package com.example.bbcnewsreader;

import android.os.Bundle;

/**
 * ArticleActivity process a news article.
 * @author  Kanykei Akmatova
 * @version 1.0
 * @since   2022-12-12
 */
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

    /**
     * Return the help message
     */
    @Override
    public String getHelpMessage(){
        return getString(R.string.article_help_message);
    }
}