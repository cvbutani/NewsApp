package com.example.chirag.newsapp;

import android.text.TextUtils;
import android.util.Log;

import com.example.chirag.newsapp.Constants.ApiRequestConstant;
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

            JSONObject responseObject;
            JSONArray resultsArray = null;
            if (jsonObject.has(ApiRequestConstant.JSON_PARSE_KEY_RESPONSE)) {
                responseObject = jsonObject.getJSONObject(ApiRequestConstant.JSON_PARSE_KEY_RESPONSE);
                if (responseObject != null && responseObject.has(ApiRequestConstant.JSON_PARSE_KEY_RESULTS)) {
                    resultsArray = responseObject.getJSONArray(ApiRequestConstant.JSON_PARSE_KEY_RESULTS);
                }
            }

            if (resultsArray != null) {
                for (int i = 0; i < resultsArray.length(); i++) {

                    JSONObject elementsInItem = resultsArray.getJSONObject(i);

                    String sectionName = newsParse(elementsInItem, null, ApiRequestConstant.JSON_PARSE_KEY_SECTION_NAME);
                    String webTitle = newsParse(elementsInItem, null, ApiRequestConstant.JSON_PARSE_KEY_TITLE);
                    String webUrl = newsParse(elementsInItem, null, ApiRequestConstant.JSON_PARSE_KEY_URL);
                    String webPublicationDate = newsParse(elementsInItem, null, ApiRequestConstant.JSON_PARSE_KEY_PUBLICATION_DATE);
                    String thumbnailImage = newsParse(elementsInItem, ApiRequestConstant.JSON_PARSE_KEY_FIELDS, ApiRequestConstant.JSON_PARSE_KEY_THUMBNAIL);
                    String productionOffice = newsParse(elementsInItem, ApiRequestConstant.JSON_PARSE_KEY_FIELDS, ApiRequestConstant.JSON_PARSE_KEY_PRODUCTION_OFFICE);
                    String bodyText = newsParse(elementsInItem, ApiRequestConstant.JSON_PARSE_KEY_FIELDS, ApiRequestConstant.JSON_PARSE_KEY_BODY_TEXT);

                    newsDetails.add(new NewsInfo(productionOffice, webTitle, bodyText, webPublicationDate, thumbnailImage, webUrl, sectionName));
                }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "PROBLEM PARSING DATA FROM JSON", e);
        }
        return newsDetails;
    }

    private static String newsParse(JSONObject objectOne, String objectTwo, String key) throws JSONException {
        JSONObject jsonObject;
        String value;

        if (objectOne != null && objectOne.has(key)) {
            value = objectOne.getString(key);
            return value;
        } else if (objectOne != null && objectOne.has(objectTwo)) {
            jsonObject = objectOne.getJSONObject(objectTwo);
            if (jsonObject != null && jsonObject.has(key)) {
                value = jsonObject.getString(key);
                return value;
            }
        }
        return null;
    }

}
