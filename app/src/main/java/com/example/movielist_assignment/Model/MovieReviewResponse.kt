package com.example.movielist_assignment.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.example.movielist_assignment.Model.MovieReview
import java.util.ArrayList

class MovieReviewResponse {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("page")
    @Expose
    var page: Int? = null

    @SerializedName("results")
    @Expose
    var results: ArrayList<MovieReview>? = null
}