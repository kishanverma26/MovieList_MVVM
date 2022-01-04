package com.example.movielist_assignment.Model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import android.os.Parcel
import android.os.Parcelable.Creator
import java.util.ArrayList

class Movie : Parcelable {
    @SerializedName("vote_count")
    var voteCount = 0

    @SerializedName("id")
    var id = 0

    @SerializedName("video")
    var isVideo = false

    @SerializedName("vote_average")
    var voteAverage = 0.0

    @SerializedName("title")
    var title: String? = null

    @SerializedName("popularity")
    var popularity = 0.0

    @SerializedName("poster_path")
    var posterPath: String? = null

    @SerializedName("original_language")
    var originalLanguage: String? = null

    @SerializedName("original_title")
    var originalTitle: String? = null

    @SerializedName("backdrop_path")
    var backdropPath: String? = null

    @SerializedName("adult")
    var isAdult = false

    @SerializedName("overview")
    var overview: String? = null

    @SerializedName("release_date")
    var releaseDate: String? = null

    @SerializedName("genre_ids")
    var genreIds: List<Int?>? = null

    constructor() {}
    private constructor(`in`: Parcel) {
        voteCount = `in`.readInt()
        id = `in`.readInt()
        isVideo = `in`.readByte().toInt() != 0x00
        voteAverage = `in`.readDouble()
        title = `in`.readString()
        popularity = `in`.readDouble()
        posterPath = `in`.readString()
        originalLanguage = `in`.readString()
        originalTitle = `in`.readString()
        backdropPath = `in`.readString()
        isAdult = `in`.readByte().toInt() != 0x00
        overview = `in`.readString()
        releaseDate = `in`.readString()
        if (`in`.readByte().toInt() == 0x01) {
            genreIds = ArrayList()
            `in`.readList(genreIds as ArrayList<Int?>, Int::class.java.classLoader)
        } else {
            genreIds = null
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(voteCount)
        dest.writeInt(id)
        dest.writeByte((if (isVideo) 0x01 else 0x00).toByte())
        dest.writeDouble(voteAverage)
        dest.writeString(title)
        dest.writeDouble(popularity)
        dest.writeString(posterPath)
        dest.writeString(originalLanguage)
        dest.writeString(originalTitle)
        dest.writeString(backdropPath)
        dest.writeByte((if (isAdult) 0x01 else 0x00).toByte())
        dest.writeString(overview)
        dest.writeString(releaseDate)
        if (genreIds == null) {
            dest.writeByte(0x00.toByte())
        } else {
            dest.writeByte(0x01.toByte())
            dest.writeList(genreIds)
        }
    }

    companion object {
        @JvmField
        val CREATOR: Creator<Movie> = object : Creator<Movie> {
            override fun createFromParcel(`in`: Parcel): Movie? {
                return Movie(`in`)
            }

            override fun newArray(size: Int): Array<Movie?> {
                return arrayOfNulls(size)
            }
        }
    }
}