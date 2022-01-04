package com.example.movielist_assignment.Activity.ViewModel.Detail_ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.example.movielist_assignment.Model.MovieTrailer
import com.example.movielist_assignment.Network.ApiInterface
import com.example.movielist_assignment.Repository.TrailerRepository
import java.util.ArrayList

class MovieTrailersViewModel : ViewModel() {
    var movieLive_Data: MutableLiveData<ArrayList<MovieTrailer>>? = null
    var error_Message: MutableLiveData<String>? = null
    fun getMovieLiveData(): MutableLiveData<ArrayList<MovieTrailer>>? {
        if (movieLive_Data == null) {
            movieLive_Data = MutableLiveData()
        }
        return movieLive_Data!!
    }

    fun getErrorMessage(): MutableLiveData<String> {
        if (error_Message == null) {
            error_Message = MutableLiveData()
        }
        return error_Message!!
    }

    fun setMovieLiveData(movieTrailerArrayList: ArrayList<MovieTrailer>) {
        movieLive_Data!!.value = movieTrailerArrayList
    }

    fun setErrorMessage(errorMessageString: String) {
        error_Message!!.value = errorMessageString
    }

    fun getMoviesFromServer(
        apiService: ApiInterface?,
        movieId: Int,
        trailerRepository: TrailerRepository
    ) {
        trailerRepository.getMoviesTrailers(apiService!!, movieId, this)
    }
}