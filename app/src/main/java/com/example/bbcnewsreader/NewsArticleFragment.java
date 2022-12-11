package com.example.bbcnewsreader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class NewsArticleFragment extends Fragment {
    public NewsArticleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news_article, container, false);

        if (getArguments() != null) {
            Bundle dataFromActivity = getArguments();

            TextView titleText = view.findViewById(R.id.textViewTitleValue);
            titleText.setText(dataFromActivity.getString(BBCNewsConstant.NEWS_TITLE));

            TextView descriptionText = view.findViewById(R.id.textViewDescriptionValue);
            descriptionText.setText(dataFromActivity.getString(BBCNewsConstant.NEWS_DESCRIPTION));

            TextView dateText = view.findViewById(R.id.textViewDateValue);
            dateText.setText(dataFromActivity.getString(BBCNewsConstant.NEWS_DATE));

            TextView linkText = view.findViewById(R.id.textViewLinkValue);
            linkText.setText(dataFromActivity.getString(BBCNewsConstant.NEWS_LINK));
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView linkText = view.findViewById(R.id.textViewLinkValue);
        linkText.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Open :" + linkText.getText(), Toast.LENGTH_SHORT).show();
            //getActivity().finish();

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkText.getText().toString()));

            startActivity(browserIntent);
        });
    }
}