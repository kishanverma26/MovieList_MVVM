package com.example.movielist_assignment.Activity.Adapter

import com.example.movielist_assignment.Utils.Common.Companion.generateImageUrl
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.movielist_assignment.Model.Movie
import com.example.movielist_assignment.R
import com.squareup.picasso.Picasso
import com.example.movielist_assignment.Utils.Common
import com.example.movielist_assignment.databinding.MovieGridItemBinding
import java.util.ArrayList

class CustomMoviesListAdapter(
    private val movieArrayList: ArrayList<Movie>,
    private val listItemClickListener: ListItemClickListener
) : RecyclerView.Adapter<CustomMoviesListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val movieGridItemBinding: MovieGridItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.movie_grid_item, parent, false)
        return ViewHolder(movieGridItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieArrayList[position]
        holder.bindImage(movie)
    }

    override fun getItemCount(): Int {
        return movieArrayList.size
    }

    interface ListItemClickListener {
        fun onItemClicked(movie: Movie?)
    }

    inner class ViewHolder(val movieGridItemBinding: MovieGridItemBinding) :
        RecyclerView.ViewHolder(
            movieGridItemBinding.root
        ), View.OnClickListener {
        fun bindImage(movie: Movie) {
            Picasso.get()
                .load(generateImageUrl(movie.posterPath!!))
                .into(movieGridItemBinding.movieImageView)
        }

        override fun onClick(v: View) {
            val clickedPosition = adapterPosition
            val movie = movieArrayList[clickedPosition]
            listItemClickListener.onItemClicked(movie)
        }

        init {
            movieGridItemBinding.root.setOnClickListener(this)
        }
    }
}