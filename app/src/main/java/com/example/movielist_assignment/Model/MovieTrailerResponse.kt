package com.example.movielist_assignment.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.example.movielist_assignment.Model.MovieTrailer
import java.util.ArrayList

class MovieTrailerResponse {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("results")
    @Expose
    var results: ArrayList<MovieTrailer>? = null
}