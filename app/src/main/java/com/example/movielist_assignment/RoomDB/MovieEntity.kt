package com.example.movielist_assignment.RoomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MovieEntity {
    @PrimaryKey
    var id = 0
    var vote_average = 0.0
    var title: String? = null
    var popularity = 0.0
    var poster_path: String? = null
    var original_language: String? = null
    var original_title: String? = null
    var backdrop_path: String? = null
    var overview: String? = null
    var release_date: String? = null
    var vote_count = 0

    constructor() {}
    constructor(
        id: Int,
        vote_average: Double,
        title: String?,
        popularity: Double,
        poster_path: String?,
        original_language: String?,
        original_title: String?,
        backdrop_path: String?,
        overview: String?,
        release_date: String?,
        vote_count: Int
    ) {
        this.id = id
        this.vote_average = vote_average
        this.title = title
        this.popularity = popularity
        this.poster_path = poster_path
        this.original_language = original_language
        this.original_title = original_title
        this.backdrop_path = backdrop_path
        this.overview = overview
        this.release_date = release_date
        this.vote_count = vote_count
    }

    override fun toString(): String {
        return "MovieEntity{" +
                "id=" + id +
                ", vote_average=" + vote_average +
                ", title='" + title + '\'' +
                ", popularity=" + popularity +
                ", poster_path='" + poster_path + '\'' +
                ", original_language='" + original_language + '\'' +
                ", original_title='" + original_title + '\'' +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", overview='" + overview + '\'' +
                ", release_date='" + release_date + '\'' +
                ", vote_count=" + vote_count +
                '}'
    }
}