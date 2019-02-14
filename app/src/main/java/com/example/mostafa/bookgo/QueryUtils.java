package com.example.mostafa.bookgo;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();



    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }
// data set url to list we need
    public static List<Book> fetchBookData (String requestUrl ){

        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        List<Book> books = extractFeatureFromJson(jsonResponse);
        return books;
    }
    // hn3rf url w nrg3o
    private static URL createUrl(String stringurl) {
        URL url = null;
        try {
            url = new URL(stringurl);

        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "ERROR IN CREATE URL ", e);
        }
        return url;
    }


    //make http hna5od url w hnrg3 string jsonresponse
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonresponse = "";
        if (url == null) {
            return jsonresponse;
        }
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.connect();
            // if connection  = 200 read
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonresponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "ERROR WITH RESPONSE " + httpURLConnection.getResponseCode());
            }


        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonresponse;
    }


    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }

        }
        return output.toString();
    }


    private static List<Book> extractFeatureFromJson(String bookJSON) {
        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }
        List<Book> books = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(bookJSON);
            JSONArray bookArray = baseJsonResponse.getJSONArray("items");
            for (int i = 0; i < bookArray.length(); i++) {
                JSONObject currentBook = bookArray.getJSONObject(i);
                JSONObject volumeinfo = currentBook.getJSONObject("volumeInfo");
                String title = volumeinfo.getString("title");

                String subtitle = "" ;
                try {
                 subtitle = volumeinfo.getString("subtitle");
               }catch (JSONException e){}
                // Extract the value for the key called "time"
                JSONArray authors = null;
                String[] authorStrings = null;
                String allAuthors = "";
             try{ authors = volumeinfo.getJSONArray("authors");
                authorStrings = new String[authors.length()];

                 for (int x = 0; x <authors.length(); ++x) {
                     authorStrings[x] = authors.getString(x);
                 }} catch (JSONException e){}
                 if (authors!=null){
                      allAuthors = TextUtils.join(", ", authorStrings);
                 }else{allAuthors="";}

                String publisher = "";
                try {
                     publisher = volumeinfo.getString("publisher");
                }catch (JSONException e){}

                String publishedDate = volumeinfo.getString("publishedDate");
                String description = "";
try {
     description = volumeinfo.getString("description");

}catch (JSONException e){}
                int pageCount = 0 ;
               try {
                    pageCount = volumeinfo.getInt("pageCount");
               }catch (JSONException e){}


                String averageRating = "";
                try{
                   averageRating = String.valueOf(volumeinfo.getDouble("averageRating"));
               }catch (JSONException e){
                    }
                    if (averageRating==null){averageRating="";}
                String smallThumbnail = null;
                  try {
                    JSONObject imagelink = volumeinfo.getJSONObject("imageLinks");
                  smallThumbnail = imagelink.getString("smallThumbnail");
                }catch (JSONException e){}
                JSONObject saleInfo  = currentBook.getJSONObject("saleInfo");
                JSONObject listPrice = null;
                String amount = "" ;
                String currencyCode="";
                try {
                   listPrice = saleInfo.getJSONObject("listPrice");
                   amount = String.valueOf(listPrice.getDouble("amount"));
                   currencyCode = listPrice.getString("currencyCode");
              }catch (JSONException e){}
              if (listPrice==null){
                  amount = "Not for sale";
                  currencyCode="";
              }







                 Book book = new Book(smallThumbnail,allAuthors,title,amount,description,currencyCode,subtitle,averageRating);
                books.add(book);
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the book JSON results", e);


        }
        return books;

    }
}