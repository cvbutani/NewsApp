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

    /**
     * It will fetch the news from API.
     * @param requestUrl - URL of the news that needs to be extracted.
     * @return List of NewsInfo Array.
     */
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

    /**
     * Creates a URL object from the String representation.
     *
     * @param findUrl - String value passed to generate URL object.
     * @return URL object.
     */
    private static URL createUrl(String findUrl) {
        URL url = null;

        try {
            url = new URL(findUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "PROBLEM BUILDING URL", e);
        }
        return url;
    }

    /**
     * Obtains new HttpUrlConnection by calling URL.openConnection() and casting result
     * to HttpURLConnection.
     * Prepares the request. The response body may be read from the stream returned by
     * URLConnection.getInputStream().
     * Once the response body has been read, the HttpURLConnection should be closed by
     * calling disconnect().
     *
     * @param url will be passed generated from createURL method.
     * @return response body.
     * @throws IOException - If the HTTP response indicates that an error occurred,
     *                     URLConnection.getInputStream() will throw an IOException.
     */
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

    /**
     * Most callers should wrap the returned streams with BufferedInputStream or
     * BufferedOutputStream. Callers that do only bulk reads or writes may omit
     * buffering.
     * When transferring large amounts of data to or from a server, use streams
     * to limit how much data is in memory at once. Unless you need the entire
     * body to be in memory at once, process it as a stream
     *
     * @param inputStream created in makeHttpRequest method.
     * @return Response in String format.
     */
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

    /**
     * Use this method to extract news updates from JSON String.
     * @param newsJSON - string value of JSON.
     * @return - List of NewsInfo Array.
     */
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

    /**
     * Parse news from JSONObject and save it in String format.
     * @param objectOne - JSONObject/JSONArray value.
     * @param objectTwo - Key that we want to extract.
     * @param key - Key that we want to extract
     * @return - String value using JSONObject.
     * @throws JSONException
     */
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
