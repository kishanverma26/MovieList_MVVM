package com.example.movielist_assignment.Utils

class Common {

    companion object{
        @JvmStatic
    fun generateImageUrl(posterPath: String): String? {
        return Constants.IMAGE_BASE_URL +
                Constants.IMAGE_BASE_SIZE.toString() +
                posterPath
    }
    }
}