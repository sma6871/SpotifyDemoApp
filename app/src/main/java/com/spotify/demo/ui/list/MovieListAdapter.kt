package com.spotify.demo.ui.list

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.spotify.demo.R
import com.spotify.demo.data.models.MovieItem
import com.spotify.demo.extensions.click
import com.spotify.demo.extensions.inflate
import com.spotify.demo.extensions.toJson
import kotlinx.android.synthetic.main.list_item.view.*

class MovieListAdapter(val itemClickListener: (itemData: String) -> Unit) :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
    private var movies: List<MovieItem> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            parent.inflate(
                R.layout.list_item,
                parent
            )
        )
    }

    override fun getItemCount() = movies.size

    /**
     * call this method to update movies list
     **/
    fun updateData(movies: List<MovieItem>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    /**
     * call this method to append new data to movies list
     **/
    fun appendData(movies: List<MovieItem>) {
        val index = this.movies.size
        this.movies += movies
        notifyItemRangeInserted(index, movies.size)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        movies.getOrNull(position)?.let { movie ->
            holder.txtRate.text = movie.voteAverage.toString()
            holder.txtTitle.text = movie.title.toString()
            holder.txtYear.text = movie.releaseDate.toString()
            holder.itemView.click {
                itemClickListener.invoke(movie.toJson())
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtYear: TextView = itemView.txtYear
        val txtTitle: TextView = itemView.txtTitle
        val txtRate: TextView = itemView.txtRate
    }
}