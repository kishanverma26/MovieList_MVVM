package com.example.movielist_assignment.Activity.ViewModel.Detail_ViewModel

import android.os.Handler
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import android.os.Looper
import android.util.Log
import com.example.movielist_assignment.Repository.DetailRepoistory
import com.example.movielist_assignment.RoomDB.WishListDataBase
import com.example.movielist_assignment.RoomDB.MovieEntity
import com.example.movielist_assignment.Activity.ViewModel.Detail_ViewModel.DetailMovieViewModel

class DetailMovieViewModel : ViewModel() {
    var isMovieWishListed: MutableLiveData<Boolean>? = null
    fun getIsMovieWishListed(): MutableLiveData<Boolean> {
        if (isMovieWishListed == null) {
            isMovieWishListed = MutableLiveData()
        }
        return isMovieWishListed!!
    }

    fun setIsMovieWishListed(isMovieWishListedValue: Boolean) {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            if (isMovieWishListed == null) {
                isMovieWishListed = MutableLiveData()
            }
            isMovieWishListed!!.value = isMovieWishListedValue
        }
    }

    fun getDetailsIfMovieIsWishlistedInDb(
        id: Int,
        detailRepoistory: DetailRepoistory,
        wishListDataBase: WishListDataBase?
    ) {
        detailRepoistory.isMovieWishlisted(id, wishListDataBase!!, this)
    }

    fun removeMovieFromWishList(
        id: Int,
        detailRepoistory: DetailRepoistory,
        wishListDataBase: WishListDataBase?
    ) {
        detailRepoistory.removeMovieFromWishList(id, wishListDataBase!!, this)
    }

    fun addMovieToWishList(
        movieEntity: MovieEntity,
        detailRepoistory: DetailRepoistory,
        wishListDataBase: WishListDataBase
    ) {
        Log.d(
            TAG,
            "addMovieToWishList() called with: movieEntity = [$movieEntity], detailRepoistory = [$detailRepoistory], wishListDataBase = [$wishListDataBase]"
        )
        detailRepoistory.addMovieToWishList(movieEntity, wishListDataBase, this)
    }

    companion object {
        private const val TAG = "DetailMovieViewModel"
    }
}