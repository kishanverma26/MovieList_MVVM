package com.example.movielist_assignment.Repository

import android.util.Log
import com.example.movielist_assignment.Network.ApiInterface
import com.example.movielist_assignment.Activity.ViewModel.Detail_ViewModel.MovieTrailersViewModel
import com.example.movielist_assignment.Model.MovieTrailerResponse
import com.example.movielist_assignment.Utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrailerRepository {
    fun getMoviesTrailers(
        apiService: ApiInterface,
        movieId: Int,
        movieTrailersViewModel: MovieTrailersViewModel
    ) {
        val call = apiService.getMovieTrailerDetails(movieId, Constants.API_KEY)
        call.enqueue(object : Callback<MovieTrailerResponse> {
            override fun onResponse(
                call: Call<MovieTrailerResponse>,
                response: Response<MovieTrailerResponse>
            ) {
                val statusCode = response.code()
                if (statusCode == 200) {
                    val movieTrailers = response.body()!!.results
                    movieTrailersViewModel.setMovieLiveData(movieTrailers!!)
                } else {
                    movieTrailersViewModel.setErrorMessage(response.message())
                }
            }

            override fun onFailure(call: Call<MovieTrailerResponse>, t: Throwable) {
                // Log error here since request failed
                Log.e(TAG, t.toString())
                movieTrailersViewModel.setErrorMessage(t.message!!)
            }
        })
    }

    companion object {
        private const val TAG = "TrailerRepository"
    }
}