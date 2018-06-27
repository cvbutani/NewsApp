package com.example.chirag.newsapp;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.picasso.Picasso;

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

            JSONObject responseObject = null;
            JSONArray resultsArray = null;
            if (jsonObject.has("response")) {
                responseObject = jsonObject.getJSONObject("response");
                if (responseObject != null && responseObject.has("results")){
                    resultsArray = responseObject.getJSONArray("results");
                }
            }

            if (resultsArray != null) {
                for (int i = 0; i < resultsArray.length(); i++) {

                    JSONObject elementsInItem = resultsArray.getJSONObject(i);
                    String source = null;
                    if (elementsInItem.has("sectionName")) {
                        source = elementsInItem.getString("sectionName");
                        Log.i(LOG_TAG, "SECTION NAME: " + source);
                    }
//                    String id = "";
                    String title = "";
                    String description = "";
                    String publishedDate = "";
                    JSONObject imageObject = null;
                    String thumbnail = "";
//                    if (source != null && source.has("id")) {
//                        id = source.getString("id");
//                        Log.i(LOG_TAG, "ID: " + id);
//                        if (id != null && id.equalsIgnoreCase("the-times-of-india")) {
                    if (elementsInItem.has("webTitle")) {
                        title = elementsInItem.getString("webTitle");
                    }
                    if (elementsInItem.has("webUrl")) {
                        description = elementsInItem.getString("webUrl");
                    }
                    if (elementsInItem.has("webPublicationDate")) {
                        publishedDate = elementsInItem.getString("webPublicationDate");
                    }
                    if (elementsInItem.has("fields")) {
                        imageObject = elementsInItem.getJSONObject("fields");
                        if (imageObject.has("thumbnail")) {
                            thumbnail = imageObject.getString("thumbnail");
                        }
                    }
                    Log.i(LOG_TAG, "TITLE: " + title);
                    Log.i(LOG_TAG, "DESCRIPTION: " + description);
                    Log.i(LOG_TAG, "PUBLISHED DATE: " + publishedDate);
                    Log.i(LOG_TAG, "THUMBNAIL URL: " + thumbnail);
                    newsDetails.add(new NewsInfo(title, description, publishedDate, thumbnail));
                }
            }
//                }
//            }
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
