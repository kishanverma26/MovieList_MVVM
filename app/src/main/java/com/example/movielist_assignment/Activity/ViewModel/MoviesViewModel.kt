package com.example.movielist_assignment.Activity.ViewModel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movielist_assignment.Model.Movie
import com.example.movielist_assignment.Network.ApiInterface
import com.example.movielist_assignment.Repository.MovieRepository
import com.example.movielist_assignment.RoomDB.WishListDataBase
import java.util.ArrayList

class MoviesViewModel : ViewModel() {

    private var movieLiveData: MutableLiveData<ArrayList<Movie>>? = null
    private var errorMessage: MutableLiveData<String>? = null

    fun getMovieLiveData(): MutableLiveData<ArrayList<Movie>>? {
        if (movieLiveData == null) {
            movieLiveData = MutableLiveData()
        }
        return movieLiveData
    }

    fun getErrorMessage(): MutableLiveData<String>? {
        if (errorMessage == null) {
            errorMessage = MutableLiveData()
        }
        return errorMessage
    }

    fun setMovieLiveData(movieArrayList: ArrayList<Movie>?) {
        val handler = Handler(Looper.getMainLooper())
        handler.post { movieLiveData!!.setValue(movieArrayList) }
    }

    fun setErrorMessage(errorString: String?) {
        val handler = Handler(Looper.getMainLooper())
        handler.post { errorMessage!!.setValue(errorString) }
    }

    fun getMoviesFromServer(apiService: ApiInterface, movieRepository: MovieRepository, moviesType: Int) {
        movieRepository.getMovies(apiService, this, moviesType)
    }

    fun getFavouriteMovies(wishListDataBase: WishListDataBase, movieRepository: MovieRepository) {
        movieRepository.getFavouriteMovies(wishListDataBase, this)
    }

}