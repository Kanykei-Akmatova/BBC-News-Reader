package com.example.bbcnewsreader;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * NewsFragment provides presentation for news.
 * @author  Kanykei Akmatova
 * @version 1.0
 * @since   2022-12-12
 */
public class NewsFragment extends Fragment {
    private List<RssItem> bbcNews;
    private static final String MY_PREF = "MyPref";

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
        EditText editText = view.findViewById(R.id.SearchText);
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

        SharedPreferences sharedPref = getActivity().getApplicationContext()
                .getSharedPreferences(MY_PREF, MODE_PRIVATE);
        String name = sharedPref.getString("filter", "");
        editText.setText(name);

        // Search
        ImageButton imageButtonSearch = view.findViewById(R.id.imageButtonSearch);
        imageButtonSearch.setOnClickListener(v -> {
            ProgressBar pb = view.findViewById(R.id.progressBar);
            pb.setProgress(1);

            Toast.makeText(getActivity(), R.string.search, Toast.LENGTH_SHORT).show();

            String filter = editText.getText().toString().toLowerCase(Locale.ROOT);
            ((NewsActivity) getActivity()).filter(filter);

            // Save last search
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("filter", filter);
            editor.commit();

            pb.setProgress(10);
        });
    }
}