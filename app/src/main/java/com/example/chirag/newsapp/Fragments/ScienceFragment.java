package com.example.chirag.newsapp.Fragments;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chirag.newsapp.NewsDataAdapter;
import com.example.chirag.newsapp.NewsInfo;
import com.example.chirag.newsapp.NewsLoader;
import com.example.chirag.newsapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScienceFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<NewsInfo>>{

    private NewsDataAdapter mNewsDataAdapter;

    private static final int NEWS_LOADER_ID = 0;

    private static final String SCIENCE_URL = "https://content.guardianapis.com/search?section=science&api-key=00d9a257-1ff3-4d33-bff4-b26e08cd141d";

    public ScienceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_view, container, false);

        final ArrayList<NewsInfo> newsInfoArrayList = new ArrayList<>();
        if (getActivity() != null) {
            mNewsDataAdapter = new NewsDataAdapter(getActivity(), newsInfoArrayList);
        }
        ListView listView = rootView.findViewById(R.id.list);
        listView.setAdapter(mNewsDataAdapter);

        checkInternetConnection();

        return rootView;
    }

    @Override
    public Loader<List<NewsInfo>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(getContext(), SCIENCE_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsInfo>> loader, List<NewsInfo> data) {
        mNewsDataAdapter.clear();
        if (data != null && !data.isEmpty()) {
            mNewsDataAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsInfo>> loader) {
        mNewsDataAdapter.clear();
    }

    public void checkInternetConnection(){
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }

        boolean isConnected = (activeNetwork != null) && (activeNetwork.isConnectedOrConnecting());

        if (isConnected) {
            LoaderManager loadManager = getActivity().getLoaderManager();
            loadManager.initLoader(NEWS_LOADER_ID, null, this);
        }
    }

}
