package com.example.movielist_assignment.Activity.ViewModel.Detail_ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.example.movielist_assignment.Model.MovieReview
import com.example.movielist_assignment.Network.ApiInterface
import com.example.movielist_assignment.Repository.ReviewRepository
import java.util.ArrayList

class MovieReviewsViewModel : ViewModel() {
    var movieLiveDatas: MutableLiveData<ArrayList<MovieReview>>? = null
    var errorMessages: MutableLiveData<String>? = null
    fun setMovieLiveData(movieLiveData: ArrayList<MovieReview>) {
        this.movieLiveDatas!!.value = movieLiveData
    }

    fun setErrorMessage(errorMessage: String) {
        this.errorMessages!!.value = errorMessage
    }

    fun getMovieLiveData(): MutableLiveData<ArrayList<MovieReview>> {
        if (movieLiveDatas == null) {
            movieLiveDatas = MutableLiveData()
        }
        return movieLiveDatas!!
    }

    fun getErrorMessage(): MutableLiveData<String> {
        if (errorMessages == null) {
            errorMessages = MutableLiveData()
        }
        return errorMessages!!
    }

    fun getMovieReviewsFromServer(
        apiService: ApiInterface?,
        movieId: Int,
        reviewRepository: ReviewRepository
    ) {
        reviewRepository.getMovieReviews(apiService!!, movieId, this)
    }
}