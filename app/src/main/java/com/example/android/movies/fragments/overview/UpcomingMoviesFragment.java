package com.example.android.movies.fragments.overview;

import com.example.android.movies.utils.Constants;

/**
 * Created by glm9637 on 12.03.2018 21:18.
 */

public class UpcomingMoviesFragment extends MoviePosterFragment {
    @Override
    protected String getUrlEndpoint() {
        return "/movie/upcoming";
    }

    @Override
    protected int getLoaderID() {
        return Constants.LOADER_ID_UPCOMING;
    }
}
