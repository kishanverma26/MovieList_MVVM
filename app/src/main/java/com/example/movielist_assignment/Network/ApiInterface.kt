package com.example.movielist_assignment.Network

import com.example.movielist_assignment.Model.MovieReviewResponse
import com.example.movielist_assignment.Model.MovieTrailerResponse
import com.example.movielist_assignment.Model.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("api_key") apiKey: String?): Call<MoviesResponse>

    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String?): Call<MoviesResponse>

    @GET("movie/{id}/reviews")
    fun getMovieReviewDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String?
    ): Call<MovieReviewResponse>

    @GET("movie/{id}/videos")
    fun getMovieTrailerDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String?
    ): Call<MovieTrailerResponse>

}