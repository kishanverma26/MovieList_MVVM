package com.example.movielist_assignment.RoomDB

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import com.example.movielist_assignment.RoomDB.MovieEntity
import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DatabaseDOA {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movieEntity: MovieEntity?): Long

    @Query("SELECT * FROM MovieEntity WHERE id =:id")
    fun getMovie(id: Int): LiveData<MovieEntity?>?

    @Query("SELECT * FROM MovieEntity WHERE id =:id")
    fun getMovieForWishListCheck(id: Int): MovieEntity?

    @get:Query("SELECT * FROM MovieEntity")
    val allMovies: List<MovieEntity?>?

    @Query("DELETE FROM MovieEntity where id =:id")
    fun deleteMovie(id: Int)
}