package com.example.movielist_assignment.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class MovieReview {
    @SerializedName("author")
    @Expose
    var author: String? = null

    @SerializedName("content")
    @Expose
    var content: String? = null

    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null
}