package com.example.movielist_assignment.Activity.Adapter

import com.example.movielist_assignment.Model.MovieTrailer
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.movielist_assignment.R
import com.example.movielist_assignment.databinding.TrailerRowItemBinding
import java.util.ArrayList

class CustomMoviesTrailerListAdapter(
    private val movieArrayList: ArrayList<MovieTrailer>,
    private val listItemClickListener: ListItemClickListener
) : RecyclerView.Adapter<CustomMoviesTrailerListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val trailerRowItemBinding: TrailerRowItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.trailer_row_item, parent, false)
        return ViewHolder(trailerRowItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movieTrailer = movieArrayList[position]
        holder.bindImage(movieTrailer)
    }

    override fun getItemCount(): Int {
        return movieArrayList.size
    }

     interface ListItemClickListener {
        fun onItemClicked(movieTrailer: MovieTrailer?)
    }

    inner class ViewHolder(val trailerRowItemBinding: TrailerRowItemBinding) :
        RecyclerView.ViewHolder(
            trailerRowItemBinding.root
        ), View.OnClickListener {
        fun bindImage(movieTrailer: MovieTrailer) {
            trailerRowItemBinding.trailerNameTextView.text = movieTrailer.name
        }

        override fun onClick(v: View) {
            val clickedPosition = adapterPosition
            val movieTrailer = movieArrayList[clickedPosition]
            listItemClickListener.onItemClicked(movieTrailer)
        }

        init {
            trailerRowItemBinding.root.setOnClickListener(this)
        }
    }
}