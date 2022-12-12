package com.example.bbcnewsreader;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class ArticleFragment extends Fragment {
    public ArticleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_article, container, false);

        if (getArguments() != null) {
            Bundle dataFromActivity = getArguments();

            TextView titleText = view.findViewById(R.id.textViewTitleValue);
            titleText.setText(dataFromActivity.getString(NewsConstant.NEWS_TITLE));

            TextView descriptionText = view.findViewById(R.id.textViewDescriptionValue);
            descriptionText.setText(dataFromActivity.getString(NewsConstant.NEWS_DESCRIPTION));

            TextView dateText = view.findViewById(R.id.textViewDateValue);
            dateText.setText(dataFromActivity.getString(NewsConstant.NEWS_DATE));

            TextView linkText = view.findViewById(R.id.textViewLinkValue);
            linkText.setText(dataFromActivity.getString(NewsConstant.NEWS_LINK));

            String linkId = dataFromActivity.getString(NewsConstant.NEWS_LINK_ID);
            Switch sw = view.findViewById(R.id.switchFavorite);
            if(linkId != null && linkId.length() > 0) {
                sw.setChecked(true);
            } else {
                sw.setChecked(false);
            }
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView titleText = view.findViewById(R.id.textViewTitleValue);
        TextView descriptionText = view.findViewById(R.id.textViewDescriptionValue);
        TextView dateText = view.findViewById(R.id.textViewDateValue);
        TextView linkText = view.findViewById(R.id.textViewLinkValue);

        linkText.setOnClickListener(v -> {
            Toast.makeText(getActivity(), linkText.getText(), Toast.LENGTH_SHORT).show();
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkText.getText().toString()));
            startActivity(browserIntent);
        });

        Switch sw = view.findViewById(R.id.switchFavorite);
        sw.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SQLHelper sqlHelper = new SQLHelper(getActivity());
            sqlHelper.getWritableDatabase();

            Snackbar snackbar = Snackbar.make(view, R.string.messsage, Snackbar.LENGTH_SHORT);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(this.getResources().getColor(R.color.purple_500));
            TextView textView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);

            String link = linkText.getText().toString();
            String linkId = link.substring(link.lastIndexOf("-") + 1);

            if (isChecked) {
                // The toggle is enabled
                Integer id = sqlHelper.add(
                        titleText.getText().toString(),
                        linkText.getText().toString(),
                        descriptionText.getText().toString(),
                        dateText.getText().toString(),
                        linkId);
                textView.setText(R.string.the_article_saved);
            } else {
                // The toggle is disabled
                sqlHelper.deleteByLinkId(linkId);
                textView.setText(R.string.the_article_deleted);
            }

            snackbar.show();
        });
    }
}