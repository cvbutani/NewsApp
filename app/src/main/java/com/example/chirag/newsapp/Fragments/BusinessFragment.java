package com.example.chirag.newsapp.Fragments;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.chirag.newsapp.Constants.ApiRequestConstant;
import com.example.chirag.newsapp.NewsDataAdapter;
import com.example.chirag.newsapp.NewsDisplayActivity;
import com.example.chirag.newsapp.NewsInfo;
import com.example.chirag.newsapp.NewsLoader;
import com.example.chirag.newsapp.R;

import java.util.ArrayList;
import java.util.List;

public class BusinessFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<NewsInfo>> {

    private static final int NEWS_LOADER_ID = 0;
    private static String NEWS_URL;
    private NewsDataAdapter mNewsDataAdapter;
    private LoaderManager mLoadManager;
    private ConnectivityManager mConnectivityManager;
    private NetworkInfo mActiveNetwork;

    public BusinessFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            NEWS_URL = getArguments().getString(ApiRequestConstant.BUNDLE_STRING_EXTRA);
        }
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
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
                intent.putExtra(ApiRequestConstant.LISTVIEW_EXTRA_INFO, info);
                intent.putExtra(ApiRequestConstant.LISTVIEW_EXTRA_HEADER, sectionName);
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
        return new NewsLoader(getContext(), NEWS_URL);
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

        if (getActivity() != null) {
            mConnectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        if (mConnectivityManager != null) {
            mActiveNetwork = mConnectivityManager.getActiveNetworkInfo();
        }

        boolean isConnected = (mActiveNetwork != null) && (mActiveNetwork.isConnectedOrConnecting());
        if (isConnected) {
            mLoadManager = getActivity().getLoaderManager();
            mLoadManager.initLoader(NEWS_LOADER_ID, null, this);
        }
    }

    public void checkInternetConnectionRestartLoader() {

        if (getActivity() != null) {
            mConnectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        if (mConnectivityManager != null) {
            mActiveNetwork = mConnectivityManager.getActiveNetworkInfo();
        }

        boolean isConnected = (mActiveNetwork != null) && (mActiveNetwork.isConnectedOrConnecting());
        if (isConnected) {
            mLoadManager = getActivity().getLoaderManager();
            mLoadManager.restartLoader(NEWS_LOADER_ID, null, this);
        }
    }



}
