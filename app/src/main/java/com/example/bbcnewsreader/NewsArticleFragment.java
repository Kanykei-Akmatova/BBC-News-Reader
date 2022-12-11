package com.example.bbcnewsreader;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailsFragment extends Fragment {
    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_details, container, false);

        if (getArguments() != null) {
            Bundle dataFromActivity = getArguments();

            TextView nameText = view.findViewById(R.id.textViewNameValue);
            //nameText.setText(dataFromActivity.getString(StarWarsConstant.NAME));

            TextView heightText = view.findViewById(R.id.textViewHeightValue);
            //heightText.setText(dataFromActivity.getString(StarWarsConstant.HEIGHT));

            TextView massText = view.findViewById(R.id.textViewMassValue);
            //massText.setText(dataFromActivity.getString(StarWarsConstant.MASS));
        }

        return view;
    }
}