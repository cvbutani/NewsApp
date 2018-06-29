package com.example.chirag.newsapp.Fragments;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.chirag.newsapp.MainActivity;
import com.example.chirag.newsapp.NewsDataAdapter;
import com.example.chirag.newsapp.NewsDisplayActivity;
import com.example.chirag.newsapp.NewsInfo;
import com.example.chirag.newsapp.NewsLoader;
import com.example.chirag.newsapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusinessFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<NewsInfo>> {

    private static final int NEWS_LOADER_ID = 0;
    private static final String BUSINESS_URL1 = "https://content.guardianapis.com/search?section=business";
    private static final String BUSINESS_URL3 = "show-fields=all&api-key=00d9a257-1ff3-4d33-bff4-b26e08cd141d";
    String BUSINESS_URL2;
    public static final String BUSINESS_URL = "https://content.guardianapis.com/search?section=business&show-fields=all&api-key=00d9a257-1ff3-4d33-bff4-b26e08cd141d";
    private NewsDataAdapter mNewsDataAdapter;
    private LoaderManager mLoadManager;

    public BusinessFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BUSINESS_URL2 = getArguments().getString("text");
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.list_view, container, false);

        final ArrayList<NewsInfo> newsInfoArrayList = new ArrayList<>();
        if (getActivity() != null) {
            mNewsDataAdapter = new NewsDataAdapter(getActivity(), newsInfoArrayList);
        }
        ListView listView = rootView.findViewById(R.id.list);
        listView.setAdapter(mNewsDataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), NewsDisplayActivity.class);
                NewsInfo info = newsInfoArrayList.get(position);
                String sectionName = info.getmSectionName();
                intent.putExtra("info", info);
                intent.putExtra("header", sectionName);
                startActivity(intent);
            }
        });
        checkInternetConnection();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mLoadManager.restartLoader(NEWS_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<NewsInfo>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(getContext(), BUSINESS_URL2);
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

    public void checkInternetConnection() {

        ConnectivityManager mConnectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (mConnectivityManager != null) {
            activeNetwork = mConnectivityManager.getActiveNetworkInfo();
        }

        boolean isConnected = (activeNetwork != null) && (activeNetwork.isConnectedOrConnecting());

        if (isConnected) {
            mLoadManager = getActivity().getLoaderManager();
            mLoadManager.initLoader(NEWS_LOADER_ID, null, this);
        }
    }
}
