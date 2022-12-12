package com.example.bbcnewsreader;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NewsFragment extends Fragment {
    private List<RssItem> bbcNews;

    public NewsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bbcNews = (ArrayList<RssItem>) getArguments().getSerializable(NewsConstant.NEWS_LIST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        NewsAdapter adapter = new NewsAdapter(getContext(), bbcNews);

        ListView listView = view.findViewById(R.id.newsListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((list, item, position, id) -> {
            Bundle dataToPass = new Bundle();
            dataToPass.putString(NewsConstant.NEWS_TITLE, bbcNews.get(position).getTitle());
            dataToPass.putString(NewsConstant.NEWS_DESCRIPTION, bbcNews.get(position).getDescription());
            dataToPass.putString(NewsConstant.NEWS_DATE, bbcNews.get(position).getPubDate());
            dataToPass.putString(NewsConstant.NEWS_LINK, bbcNews.get(position).getLink());

            Intent newsTextActivity = new Intent(getActivity(), ArticleActivity.class);
            newsTextActivity.putExtras(dataToPass);
            startActivity(newsTextActivity);
        });

        ImageButton imageButtonSearch = view.findViewById(R.id.imageButtonSearch);
        imageButtonSearch.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Search", Toast.LENGTH_SHORT).show();
            EditText editText = view.findViewById(R.id.SearchText);
            ((NewsActivity) getActivity()).filter(editText.getText().toString().toLowerCase(Locale.ROOT));
        });
    }
}