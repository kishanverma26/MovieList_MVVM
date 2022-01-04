package com.example.movielist_assignment.Activity


import com.example.movielist_assignment.Utils.Common.Companion.generateImageUrl
import androidx.appcompat.app.AppCompatActivity
import com.example.movielist_assignment.Activity.ViewModel.Detail_ViewModel.MovieTrailersViewModel
import com.example.movielist_assignment.Activity.ViewModel.Detail_ViewModel.MovieReviewsViewModel
import com.example.movielist_assignment.Activity.ViewModel.Detail_ViewModel.DetailMovieViewModel
import androidx.lifecycle.MutableLiveData
import com.example.movielist_assignment.Model.MovieTrailer
import com.example.movielist_assignment.Model.MovieReview
import com.example.movielist_assignment.RoomDB.WishListDataBase
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.movielist_assignment.R
import androidx.room.Room
import androidx.lifecycle.ViewModelProviders
import com.example.movielist_assignment.Activity.Adapter.CustomMoviesTrailerListAdapter
import android.content.Intent
import com.example.movielist_assignment.Activity.Adapter.CustomMovieReviewsListAdapter
import com.squareup.picasso.Picasso
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movielist_assignment.Repository.DetailRepoistory
import com.example.movielist_assignment.Network.ApiInterface
import com.example.movielist_assignment.Network.ApiClient
import com.example.movielist_assignment.Repository.ReviewRepository
import com.example.movielist_assignment.Repository.TrailerRepository
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.view.View
import androidx.lifecycle.Observer
import com.example.movielist_assignment.Activity.Adapter.CustomMoviesTrailerListAdapter.ListItemClickListener
import com.example.movielist_assignment.Model.Movie
import com.example.movielist_assignment.RoomDB.MovieEntity
import com.example.movielist_assignment.Utils.Constants
import com.example.movielist_assignment.databinding.ActivityDetailBinding
import java.util.ArrayList

class DetailActivity : AppCompatActivity(), View.OnClickListener {
    private var movie: Movie? = null
    lateinit var activityDetailBinding: ActivityDetailBinding
    private var movieTrailersViewModel: MovieTrailersViewModel? = null
    private var movieReviewsViewModel: MovieReviewsViewModel? = null
    private var detailMovieViewModel: DetailMovieViewModel? = null
     var mutableMovieTrailersLiveData: MutableLiveData<ArrayList<MovieTrailer>>? = null
     var mutableMovieReviewsLiveData: MutableLiveData<ArrayList<MovieReview>>? =null
    private var isMovieWishlisted: Boolean? = false
    private var wishListDataBase: WishListDataBase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        if (intent.extras != null) {
            movie = intent.extras!!.getParcelable(getString(R.string.extra_movie_key))
        }
        wishListDataBase = Room.databaseBuilder(
            this,
            WishListDataBase::class.java, Constants.DATABASE_NAME
        ).build()
        updateUi()
        movieTrailersViewModel = ViewModelProviders.of(this).get(
            MovieTrailersViewModel::class.java
        )
        movieReviewsViewModel = ViewModelProviders.of(this).get(
            MovieReviewsViewModel::class.java
        )
        detailMovieViewModel = ViewModelProviders.of(this).get(
            DetailMovieViewModel::class.java
        )
        activityDetailBinding.wishListImageView.setOnClickListener(this)
        observeUiData()
        makeApiCall()
        title = movie!!.title
    }

    private fun observeUiData() {
        mutableMovieTrailersLiveData = movieTrailersViewModel!!.getMovieLiveData()
        mutableMovieReviewsLiveData = movieReviewsViewModel!!.getMovieLiveData()
        mutableMovieTrailersLiveData!!.observe(
            this,
            Observer<ArrayList<MovieTrailer>> { movieTrailers ->
                activityDetailBinding!!.trailerProgressBar.visibility = View.GONE
                activityDetailBinding!!.trailersErrorTv.visibility = View.GONE


                val customMoviesTrailerListAdapter = CustomMoviesTrailerListAdapter(movieTrailers,
                    object :   ListItemClickListener {
                        override fun onItemClicked(movieTrailer: MovieTrailer?) {
                            val url = getString(R.string.youtube_base_url) + movieTrailer!!.key
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                        }
                    })

                activityDetailBinding!!.trailerVideosRecyclerView.adapter =
                    customMoviesTrailerListAdapter
            })
        val trailersErrorMessage = movieTrailersViewModel!!.getErrorMessage()
        trailersErrorMessage!!.observe(this, { s ->
            activityDetailBinding!!.trailersErrorTv.text = s
            activityDetailBinding!!.trailersErrorTv.visibility = View.VISIBLE
            activityDetailBinding!!.trailerVideosRecyclerView.visibility = View.GONE
            activityDetailBinding!!.trailerProgressBar.visibility = View.GONE
        })
        mutableMovieReviewsLiveData!!.observe(
            this,
            Observer<ArrayList<MovieReview>> { movieReviews ->
                activityDetailBinding!!.reviewProgressBar.visibility = View.GONE
                activityDetailBinding!!.reviewsErrorTv.visibility = View.GONE
                val customMovieReviewsListAdapter = CustomMovieReviewsListAdapter(movieReviews)
                activityDetailBinding!!.reviewsRecyclerView.adapter = customMovieReviewsListAdapter
            })
        val reviewsErrorMessage = movieReviewsViewModel!!.getErrorMessage()
        reviewsErrorMessage!!.observe(this, { s ->
            activityDetailBinding!!.reviewsErrorTv.text = s
            activityDetailBinding!!.reviewsErrorTv.visibility = View.VISIBLE
            activityDetailBinding!!.reviewsRecyclerView.visibility = View.GONE
            activityDetailBinding!!.reviewProgressBar.visibility = View.GONE
        })
        val isWishlistedMovie = detailMovieViewModel!!.getIsMovieWishListed()
        isWishlistedMovie!!.observe(this, { aBoolean ->
            isMovieWishlisted = aBoolean
            if (aBoolean!!) {
                activityDetailBinding!!.wishListImageView.setImageResource(R.drawable.ic_favorite)
            } else {
                activityDetailBinding!!.wishListImageView.setImageResource(R.drawable.ic_favorite_border)
            }
        })
    }

    private fun updateUi() {
        Picasso.get()
            .load(generateImageUrl(movie!!.posterPath!!))
            .into(activityDetailBinding!!.coverImageView)
        Picasso.get()
            .load(generateImageUrl(movie!!.backdropPath!!))
            .into(activityDetailBinding!!.backgroundImageView)
        activityDetailBinding!!.descriptionTv.text = movie!!.overview
        activityDetailBinding!!.titleTv.text = movie!!.title
        val voteAverage: String = "${movie!!.voteAverage}/10"
        activityDetailBinding!!.voteAverageTv.text = voteAverage
        activityDetailBinding!!.votesTotalTv.text =
            String.format(getString(R.string.votes_value), movie!!.voteCount)
        activityDetailBinding!!.releaseDateTv.text =
            String.format(getString(R.string.release_date_value), movie!!.releaseDate)
        activityDetailBinding!!.trailerVideosRecyclerView.layoutManager = LinearLayoutManager(this)
        activityDetailBinding!!.reviewsRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun makeApiCall() {
        detailMovieViewModel!!.getDetailsIfMovieIsWishlistedInDb(
            movie!!.id,
            DetailRepoistory(),
            wishListDataBase
        )
        val apiService = ApiClient.getClient()!!.create(ApiInterface::class.java)
        if (isNetworkAvailable) {
            if (mutableMovieReviewsLiveData!!.value == null) {
                activityDetailBinding!!.reviewProgressBar.visibility = View.VISIBLE
                movieReviewsViewModel!!.getMovieReviewsFromServer(
                    apiService,
                    movie!!.id,
                    ReviewRepository()
                )
            }
            if (mutableMovieTrailersLiveData!!.value == null) {
                activityDetailBinding!!.trailerProgressBar.visibility = View.VISIBLE
                movieTrailersViewModel!!.getMoviesFromServer(
                    apiService,
                    movie!!.id,
                    TrailerRepository()
                )
            }
        } else {
            activityDetailBinding!!.reviewsErrorTv.text =
                getString(R.string.please_check_internet_connection)
            activityDetailBinding!!.trailersErrorTv.text =
                getString(R.string.please_check_internet_connection)
        }
    }

    private val isNetworkAvailable: Boolean
        private get() {
            val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.wishListImageView ->
                if (isMovieWishlisted!!) {
                detailMovieViewModel!!.removeMovieFromWishList(
                    movie!!.id,
                    DetailRepoistory(),
                    wishListDataBase
                )
            } else {
                val movieEntity = MovieEntity(
                    movie!!.id,
                    movie!!.voteAverage,
                    movie!!.title,
                    movie!!.popularity,
                    movie!!.posterPath,
                    movie!!.originalLanguage,
                    movie!!.originalTitle,
                    movie!!.backdropPath,
                    movie!!.overview,
                    movie!!.releaseDate,
                    movie!!.voteCount
                )
                detailMovieViewModel!!.addMovieToWishList(
                    movieEntity,
                    DetailRepoistory(),
                    wishListDataBase!!
                )
            }
        }
    }

    companion object {
        private const val TAG = "DetailActivity"
    }
}