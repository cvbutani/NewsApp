package com.example.chirag.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.List;

public class NewsQuery {

    private static String LOG_TAG = NewsQuery.class.getName();

    public NewsQuery() {
    }

    public static List<NewsInfo> fetchNewsUpdates(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "PROBLEM MAKING HTTP REQUEST", e);
        }

        return extractNewsUpdates(jsonResponse);
    }

    private static URL createUrl(String findUrl) {
        URL url = null;

        try {
            url = new URL(findUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "PROBLEM BUILDING URL", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "ERROR RESPONSE CODE: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "PROBLEM RETRIEVING NEWS INFO FROM GIVEN JSON RESULT", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) {
        StringBuilder output = new StringBuilder();
        try {
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = bufferedReader.readLine();
                while (line != null) {
                    output.append(line);
                    line = bufferedReader.readLine();
                }
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "CAN'T READ ANYTHING FROM INPUT STREAM", e);
        }
        return output.toString();
    }

    private static List<NewsInfo> extractNewsUpdates(String newsJSON) {
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }
        List<NewsInfo> newsDetails = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(newsJSON);
            Log.i(LOG_TAG, "JSONObject: " + jsonObject.toString());

            JSONArray itemsArray = null;
            if (jsonObject.has("articles")) {
                itemsArray = jsonObject.getJSONArray("articles");
                Log.i(LOG_TAG, "ARTICLES ARRAY: " + itemsArray);
            }

            if (itemsArray != null) {
                for (int i = 0; i < itemsArray.length(); i++) {

                    JSONObject elementsInItem = itemsArray.getJSONObject(i);
                    JSONObject source = null;
                    if (elementsInItem.has("source")) {
                        source = elementsInItem.getJSONObject("source");
                        Log.i(LOG_TAG, "SOURCE: " + source);
                    }
                    String id = "";
                    String title = "";
                    String description = "";
                    String publishedDate = "";
                    if (source != null && source.has("id")) {
                        id = source.getString("id");
                        Log.i(LOG_TAG, "ID: " + id);
                        if (id != null && id.equalsIgnoreCase("the-times-of-india")) {
                            if (elementsInItem.has("title")) {
                                title = elementsInItem.getString("title");
                            }
                            if (elementsInItem.has("description")) {
                                description = elementsInItem.getString("description");
                            }
                            if (elementsInItem.has("publishedAt")) {
                                publishedDate = elementsInItem.getString("publishedAt");
                            }
                            Log.i(LOG_TAG, "TITLE: " + title);
                            Log.i(LOG_TAG, "DESCRIPTION: " + description);
                            Log.i(LOG_TAG, "PUBLISHED DATE: " + publishedDate);

                            newsDetails.add(new NewsInfo(title, description, publishedDate, R.drawable.asas));
                        }
                    }


                }
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the data JSON results", e);
        }
        return newsDetails;
    }

//    private static String bookInfo(JSONObject object, String key) throws JSONException {
//        JSONObject jsonObject = null;
//        StringBuilder arrayParameters = new StringBuilder();
//        String value = "";
//        JSONArray valueArray = null;
//        if (object.has(JSONParseKey.JSON_VOLUME_INFO_KEY)) {
//            jsonObject = object.getJSONObject(JSONParseKey.JSON_VOLUME_INFO_KEY);
//        }
//        if (jsonObject != null && key.equals(JSONParseKey.JSON_AUTHOR_KEY)) {
//            if (jsonObject.has(key)) {
//                valueArray = jsonObject.getJSONArray(key);
//            }
//            if (valueArray != null) {
//                arrayParameters.append(valueArray.get(0));
//                for (int i = 1; i < valueArray.length(); i++) {
//                    arrayParameters.append(", ");
//                    arrayParameters.append(valueArray.get(i));
//                }
//            }
//            value = arrayParameters.toString();
//            return value;
//        }
//        if (jsonObject != null) {
//            value = jsonObject.getString(key);
//            return value;
//        }
//        return value;
//    }
//
//    private static String bookData(JSONObject object1, String values, String key1, String key2) throws JSONException {
//        JSONObject jsonObjectOut;
//        JSONObject jsonObjectIn;
//        String value = "0";
//        if (object1.has(values)) {
//            jsonObjectOut = object1.getJSONObject(values);
//            if (jsonObjectOut != null && jsonObjectOut.has(key1)) {
//                jsonObjectIn = jsonObjectOut.getJSONObject(key1);
//                if (jsonObjectIn != null && jsonObjectIn.has(key2)) {
//                    value = jsonObjectIn.getString(key2);
//                } else {
//                    value = "0";
//                }
//            }
//        }
//        return value;
//    }

}