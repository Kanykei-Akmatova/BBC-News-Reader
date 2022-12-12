package com.example.bbcnewsreader;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

public class FavoriteNewsFragment extends Fragment {
    private List<RssItem> bbcNews;

    public FavoriteNewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bbcNews = (ArrayList<RssItem>) getArguments().getSerializable(NewsConstant.NEWS_FAVORITE_LIST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_news, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        NewsAdapter adapter = new NewsAdapter(getContext(), bbcNews);

        ListView listView = view.findViewById(R.id.favoriteNewsListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((list, item, position, id) -> {
            Bundle dataToPass = new Bundle();
            dataToPass.putString(NewsConstant.NEWS_TITLE, bbcNews.get(position).getTitle());
            dataToPass.putString(NewsConstant.NEWS_DESCRIPTION, bbcNews.get(position).getDescription());
            dataToPass.putString(NewsConstant.NEWS_DATE, bbcNews.get(position).getPubDate());
            dataToPass.putString(NewsConstant.NEWS_LINK, bbcNews.get(position).getLink());

            Intent newsTextActivity = new Intent((BaseActivity) getActivity(), ArticleActivity.class);
            newsTextActivity.putExtras(dataToPass);
            startActivity(newsTextActivity);
        });
    }
}