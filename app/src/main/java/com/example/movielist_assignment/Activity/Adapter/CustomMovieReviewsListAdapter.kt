package com.example.movielist_assignment.Activity.Adapter

import com.example.movielist_assignment.Model.MovieReview
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.example.movielist_assignment.R
import com.example.movielist_assignment.databinding.ReviewRowItemBinding
import java.util.ArrayList

class CustomMovieReviewsListAdapter(private val movieReviews: ArrayList<MovieReview>) :
    RecyclerView.Adapter<CustomMovieReviewsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val reviewRowItemBinding: ReviewRowItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.review_row_item, parent, false)
        return ViewHolder(reviewRowItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movieReview = movieReviews[position]
        holder.bindData(movieReview)
    }

    override fun getItemCount(): Int {
        return movieReviews.size
    }

    inner class ViewHolder(val reviewRowItemBinding: ReviewRowItemBinding) :
        RecyclerView.ViewHolder(
            reviewRowItemBinding.root
        ) {
        fun bindData(movieReview: MovieReview) {
            reviewRowItemBinding.reviewAuthorTextView.text = movieReview.author
            reviewRowItemBinding.reviewContentTextView.text = movieReview.content
        }
    }
}