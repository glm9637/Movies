package com.example.android.movies.fragments.overview;

import com.example.android.movies.utils.Constants;

/**
 * Created by glm9637 on 12.03.2018 17:54.
 */

public class PopularMoviesFragment extends MoviePosterFragment {

    @Override
    protected String getUrlEndpoint() {
        return "/movie/top_rated";
    }

    @Override
    protected int getLoaderID() {
        return Constants.LOADER_ID_TOP_RATED;
    }
}
