
package com.example.android.apis.app;

import com.considine.letseat.R;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DetailFragment extends Fragment {
    /**
     * Copyright (C) 2013 John Considine. All rights reserved.
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_titleitem_detail, container, false);
        return view;
    }

    public void setText(String item) {
        TextView view = (TextView)getView().findViewById(R.id.detailsText);
        view.setText(item);
    }
}
