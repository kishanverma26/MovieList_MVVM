package com.example.movielist_assignment.Model

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class MoviesResponse {
    @SerializedName("page")
    private val page = 0

    @SerializedName("results")
    val results: ArrayList<Movie>? = null

    @SerializedName("total_results")
    private val totalResults = 0

    @SerializedName("total_pages")
    private val totalPages = 0
}