package com.example.movielist_assignment.RoomDB

import androidx.room.Database
import com.example.movielist_assignment.RoomDB.MovieEntity
import androidx.room.RoomDatabase
import com.example.movielist_assignment.RoomDB.DatabaseDOA

@Database(entities = [MovieEntity::class], version = 1)
abstract class WishListDataBase : RoomDatabase() {
    abstract fun databaseDOA(): DatabaseDOA
}