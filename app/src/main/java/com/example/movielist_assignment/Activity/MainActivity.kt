package com.example.movielist_assignment.Activity

import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.example.movielist_assignment.Activity.Adapter.CustomMoviesListAdapter
import com.example.movielist_assignment.Activity.ViewModel.MoviesViewModel
import com.example.movielist_assignment.Model.Movie
import com.example.movielist_assignment.Network.ApiClient
import com.example.movielist_assignment.Network.ApiInterface
import com.example.movielist_assignment.R
import com.example.movielist_assignment.Repository.MovieRepository
import com.example.movielist_assignment.RoomDB.WishListDataBase
import com.example.movielist_assignment.Utils.Constants
import com.example.movielist_assignment.databinding.ActivityMainBinding
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private var moviesViewModel: MoviesViewModel? = null
   lateinit var activityMainBinding: ActivityMainBinding
   lateinit var apiService: ApiInterface
    private var moviesLiveData: LiveData<ArrayList<Movie>>? = null
    private var errorMessage: MutableLiveData<String>? = null
    private var currentMoviesTypeValue = Constants.POPULAR_MOVIES_VALUE
    lateinit var wishListDataBase: WishListDataBase
    lateinit var moviesListAdapter: CustomMoviesListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        apiService = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val gridLayoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
        wishListDataBase = Room.databaseBuilder(
            this,
            WishListDataBase::class.java, Constants.DATABASE_NAME
        ).build()
        activityMainBinding!!.moviesRv.setLayoutManager(gridLayoutManager)

        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        updateUi()
        if (moviesLiveData!!.getValue() == null) {
            makeApiCall()
        }
    }


    override fun onRestart() {
        super.onRestart()
        if (currentMoviesTypeValue == Constants.FAVOURITE_MOVIES_VALUE) {
            moviesViewModel!!.getFavouriteMovies(wishListDataBase, MovieRepository())
        }
    }

    fun makeApiCall() {
        activityMainBinding.progressBar.setVisibility(View.VISIBLE)
        activityMainBinding.moviesRv.setVisibility(View.GONE)
        activityMainBinding.errorMessageTv.setVisibility(View.GONE)
        if (currentMoviesTypeValue == Constants.FAVOURITE_MOVIES_VALUE) {
            moviesViewModel!!.getFavouriteMovies(wishListDataBase, MovieRepository())
        } else {
            if (isNetworkAvailable()) {
                moviesViewModel!!.getMoviesFromServer(
                    apiService,
                    MovieRepository(),
                    currentMoviesTypeValue
                )
            } else {
                errorMessage!!.setValue(getString(R.string.please_check_internet_connection))
            }
        }
    }

    private fun updateUi() {
        moviesLiveData = moviesViewModel!!.getMovieLiveData()
        moviesLiveData!!.observe(this, object : Observer<ArrayList<Movie>?> {
            override fun onChanged(t: ArrayList<Movie>?) {
                activityMainBinding.progressBar.setVisibility(View.GONE)
                moviesListAdapter =
                    CustomMoviesListAdapter(t!!, object : CustomMoviesListAdapter.ListItemClickListener{
                        override fun onItemClicked(movie: Movie?) {
                            val intent = Intent(this@MainActivity, DetailActivity::class.java)
                            intent.putExtra(getString(R.string.extra_movie_key), movie)
                            startActivity(intent)
                        }
                    })
                activityMainBinding.moviesRv.setAdapter(moviesListAdapter)
                activityMainBinding.moviesRv.setVisibility(View.VISIBLE)
                activityMainBinding.errorMessageTv.setVisibility(View.GONE)
            }
        })
        errorMessage = moviesViewModel!!.getErrorMessage()
        errorMessage!!.observe(this, object : Observer<String?> {
            override fun onChanged(@Nullable s: String?) {
                activityMainBinding.progressBar.setVisibility(View.GONE)
                activityMainBinding.moviesRv.setVisibility(View.GONE)
                activityMainBinding.errorMessageTv.setText(s)
                activityMainBinding.errorMessageTv.setVisibility(View.VISIBLE)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_list, menu)
        return true
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.popular_movies -> {
                if (currentMoviesTypeValue != Constants.POPULAR_MOVIES_VALUE) {
                    currentMoviesTypeValue = Constants.POPULAR_MOVIES_VALUE
                    makeApiCall()
                } else {
                    showToastMessage(getString(R.string.showing_popular_movies))
                }
                true
            }
            R.id.top_rated_movies -> {
                if (currentMoviesTypeValue != Constants.TOP_RATED_MOVIES_VALUE) {
                    currentMoviesTypeValue = Constants.TOP_RATED_MOVIES_VALUE
                    makeApiCall()
                } else {
                    showToastMessage(getString(R.string.showing_top_rated_movies))
                }
                true
            }
            R.id.favourite_movies -> {
                if (currentMoviesTypeValue != Constants.FAVOURITE_MOVIES_VALUE) {
                    currentMoviesTypeValue = Constants.FAVOURITE_MOVIES_VALUE
                    makeApiCall()
                } else {
                    showToastMessage(getString(R.string.showing_favourite_movies))
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}