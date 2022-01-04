package com.example.movielist_assignment.Repository

import com.example.movielist_assignment.RoomDB.WishListDataBase
import com.example.movielist_assignment.Activity.ViewModel.Detail_ViewModel.DetailMovieViewModel
import com.example.movielist_assignment.RoomDB.MovieEntity

class DetailRepoistory {
    fun isMovieWishlisted(
        id: Int,
        wishListDataBase: WishListDataBase,
        detailMovieViewModel: DetailMovieViewModel
    ) {
        Thread {
            val movie = wishListDataBase.databaseDOA().getMovieForWishListCheck(id)
            if (movie != null) {
                detailMovieViewModel.setIsMovieWishListed(true)
            } else {
                detailMovieViewModel.setIsMovieWishListed(false)
            }
        }.start()
    }

    fun removeMovieFromWishList(
        id: Int,
        wishListDataBase: WishListDataBase,
        detailMovieViewModel: DetailMovieViewModel
    ) {
        Thread {
            wishListDataBase.databaseDOA().deleteMovie(id)
            detailMovieViewModel.setIsMovieWishListed(false)
        }.start()
    }

    fun addMovieToWishList(
        movieEntity: MovieEntity?,
        wishListDataBase: WishListDataBase,
        detailMovieViewModel: DetailMovieViewModel
    ) {
        Thread {
            wishListDataBase.databaseDOA().insertMovie(movieEntity)
            detailMovieViewModel.setIsMovieWishListed(true)
        }.start()
    }

    companion object {
        private const val TAG = "DetailRepoistory"
    }
}