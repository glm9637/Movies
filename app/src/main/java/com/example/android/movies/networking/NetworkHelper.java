package com.example.android.movies.networking;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.os.ConfigurationCompat;
import android.util.Log;

import com.example.android.movies.R;

import java.io.IOException;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by glm9637 on 11.03.2018 14:52.
 */

public class NetworkHelper {

    private static final OkHttpClient mClient = new OkHttpClient();
    private static final String language = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0).getLanguage();

    /**
     * Loads the Json-Data from the desired endpoint with the given queryParameters
     *
     * @param endpoint        the Endpoint to fetch the data to
     * @param context         the Context to access the application ressources
     * @param queryParameters parameters to append in the query
     * @return the returned json or a empty string if a error occured
     */
    public static String loadData(@NonNull String endpoint, @NonNull Context context, Map<String, String> queryParameters) {
        String baseUrl = context.getString(R.string.the_movie_db_url);
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl + endpoint).newBuilder();
        urlBuilder.addQueryParameter("language", language);
        if (queryParameters != null) {
            for (String key : queryParameters.keySet()) {
                urlBuilder.addQueryParameter(key, queryParameters.get(key));
            }
        }
    Log.v("Url",urlBuilder.build().toString());
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .build();

        try {
            Response response = mClient.newCall(request).execute();

            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * returns the full Path to a image
     *
     * @param path      the Path returned by theMovieDb
     * @param imageSize the size of the image which the path should lead to
     * @return the complete Path of the image
     */
    public static String getImageUrl(String path, ImageSize imageSize) {
        String size;
        switch (imageSize) {
            case small:
                size = "w185/";
                break;
            case medium:
                size = "w342/";
                break;
            default:
                size = "original/";
        }
        Log.v("ImageUrl","http://image.tmdb.org/t/p/" + size + path);
        return "http://image.tmdb.org/t/p/" + size + path;

    }

    /**
     * The different image sizes to load, small for a poster inside a column, medium for a title image, and original, well, for the original Image.
     */
    public enum ImageSize {
        small,
        medium,
        original
    }
}
