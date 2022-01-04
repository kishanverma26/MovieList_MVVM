package com.example.movielist_assignment.Repository

import android.util.Log
import com.example.movielist_assignment.Network.ApiInterface
import com.example.movielist_assignment.Activity.ViewModel.MoviesViewModel
import com.example.movielist_assignment.Model.Movie
import com.example.movielist_assignment.Model.MoviesResponse
import com.example.movielist_assignment.RoomDB.WishListDataBase
import com.example.movielist_assignment.RoomDB.MovieEntity
import com.example.movielist_assignment.Utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class MovieRepository {
    fun getMovies(apiService: ApiInterface, onResponseListener: MoviesViewModel, moviesType: Int) {
        var call: Call<MoviesResponse>? = null
        if (moviesType == Constants.POPULAR_MOVIES_VALUE) {
            call = apiService.getPopularMovies(Constants.API_KEY)
        } else if (moviesType == Constants.TOP_RATED_MOVIES_VALUE) {
            call = apiService.getTopRatedMovies(Constants.API_KEY)
        }
        call!!.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                val statusCode = response.code()
                if (statusCode == 200) {
                    val movies = response.body()!!
                        .results
                    onResponseListener.setMovieLiveData(movies)
                } else {
                    onResponseListener.setErrorMessage(response.message())
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                // Log error here since request failed
                Log.e(TAG, t.toString())
            }
        })
    }

    fun getFavouriteMovies(wishListDataBase: WishListDataBase, moviesViewModel: MoviesViewModel) {
        Thread {
            val moviesEntitiesList = wishListDataBase.databaseDOA().allMovies
            if (moviesEntitiesList != null && moviesEntitiesList.size > 0) {
                val movieArrayList = ArrayList<Movie>()
                for (movieEntity in moviesEntitiesList) {
                    val movie = generateMovieObjectFromMovieEntity(movieEntity!!)
                    movieArrayList.add(movie)
                }
                moviesViewModel.setMovieLiveData(movieArrayList)
            } else {
                moviesViewModel.setErrorMessage(Constants.NO_FAVOURITE_MOVIES_STRING)
            }
        }.start()
    }

    private fun generateMovieObjectFromMovieEntity(movieEntity: MovieEntity): Movie {
        val movie = Movie()
        movie.id = movieEntity.id
        movie.voteAverage = movieEntity.vote_average
        movie.title = movieEntity.title
        movie.popularity = movieEntity.popularity
        movie.posterPath = movieEntity.poster_path
        movie.originalLanguage = movieEntity.original_language
        movie.originalTitle = movieEntity.original_title
        movie.backdropPath = movieEntity.backdrop_path
        movie.overview = movieEntity.overview
        movie.releaseDate = movieEntity.release_date
        movie.voteCount = movieEntity.vote_count
        return movie
    }

    companion object {
        private const val TAG = "TrailerRepository"
    }
}