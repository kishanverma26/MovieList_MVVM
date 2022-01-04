package com.example.movielist_assignment.Utils

import com.example.movielist_assignment.BuildConfig
import com.example.movielist_assignment.Utils.Constants.Companion.API_KEY

class Constants {

    companion object {

        val API_KEY = BuildConfig.API_KEY
        val IMAGE_BASE_URL = "http://image.tmdb.org/t/p/"
        val IMAGE_BASE_SIZE = "w185"

        val TOP_RATED_MOVIES_VALUE = 1
        val POPULAR_MOVIES_VALUE = 2
        val DATABASE_NAME = "wishlist-database"
        val FAVOURITE_MOVIES_VALUE = 3
        val NO_FAVOURITE_MOVIES_STRING = "No favourite movies"
    }
}