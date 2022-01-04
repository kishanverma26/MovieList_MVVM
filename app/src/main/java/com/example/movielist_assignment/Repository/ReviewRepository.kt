package com.example.movielist_assignment.Repository

import android.util.Log
import com.example.movielist_assignment.Network.ApiInterface
import com.example.movielist_assignment.Activity.ViewModel.Detail_ViewModel.MovieReviewsViewModel
import com.example.movielist_assignment.Model.MovieReviewResponse
import com.example.movielist_assignment.Utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewRepository {
    fun getMovieReviews(
        apiService: ApiInterface,
        movieId: Int,
        movieReviewsViewModel: MovieReviewsViewModel
    ) {
        val call = apiService.getMovieReviewDetails(movieId, Constants.API_KEY)
        call.enqueue(object : Callback<MovieReviewResponse> {
            override fun onResponse(
                call: Call<MovieReviewResponse>,
                response: Response<MovieReviewResponse>
            ) {
                val statusCode = response.code()
                if (statusCode == 200) {
                    val movieReviews = response.body()!!.results
                    movieReviewsViewModel.setMovieLiveData(movieReviews!!)
                } else {
                    movieReviewsViewModel.setErrorMessage(response.message())
                }
            }

            override fun onFailure(call: Call<MovieReviewResponse>, t: Throwable) {
                // Log error here since request failed
                Log.e(TAG, t.toString())
                movieReviewsViewModel.setErrorMessage(t.message!!)
            }
        })
    }

    companion object {
        private const val TAG = "TrailerRepository"
    }
}