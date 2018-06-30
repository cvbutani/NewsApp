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
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.chirag.newsapp.NewsDataAdapter;
import com.example.chirag.newsapp.NewsDisplayActivity;
import com.example.chirag.newsapp.NewsInfo;
import com.example.chirag.newsapp.NewsLoader;
import com.example.chirag.newsapp.R;

import java.util.ArrayList;
import java.util.List;

public class BusinessFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<NewsInfo>> {

    private static final int NEWS_LOADER_ID = 0;
    String BUSINESS_URL;
    private NewsDataAdapter mNewsDataAdapter;
    private LoaderManager mLoadManager;
    private static SwipeRefreshLayout mSwipeRefreshLayout;
    ListView listView;

    public BusinessFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BUSINESS_URL = getArguments().getString("text");
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.list_view, container, false);

        final ArrayList<NewsInfo> newsInfoArrayList = new ArrayList<>();
        if (getActivity() != null) {
            mNewsDataAdapter = new NewsDataAdapter(getActivity(), newsInfoArrayList);
        }
        listView = rootView.findViewById(R.id.list);
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkInternetConnectionRestartLoader();

            }
        });

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
        checkInternetConnectionInitLoader();
        return rootView;
    }

    @Override
    public void onResume() {
        checkInternetConnectionRestartLoader();
        super.onResume();
    }

    @Override
    public Loader<List<NewsInfo>> onCreateLoader(int id, Bundle args) {
        Log.i("Fragment", "URL: " + BUSINESS_URL);
        return new NewsLoader(getContext(), BUSINESS_URL);
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

    public void checkInternetConnectionInitLoader() {

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

    public void checkInternetConnectionRestartLoader() {

        ConnectivityManager mConnectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (mConnectivityManager != null) {
            activeNetwork = mConnectivityManager.getActiveNetworkInfo();
        }

        boolean isConnected = (activeNetwork != null) && (activeNetwork.isConnectedOrConnecting());

        if (isConnected) {
            mLoadManager = getActivity().getLoaderManager();
            mLoadManager.restartLoader(NEWS_LOADER_ID, null, this);
        }
    }


}
