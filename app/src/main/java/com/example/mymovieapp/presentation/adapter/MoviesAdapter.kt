package com.example.mymovieapp.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymovieapp.Constants.IMAGE_PATH
import com.example.mymovieapp.R
import com.example.mymovieapp.databinding.MovieItemBinding
import com.example.mymovieapp.domain.entities.Movie
import com.example.mymovieapp.domain.enum.ViewHolderType


class MoviesAdapter(
    private val context: Context,
    private val movieList: MutableList<Movie>,
    val onItemClickListener: (Movie) -> Unit,
    val onFavoriteClickListener: (Movie) -> Unit,
    val tabLayoutPosition: Int
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var isLoading = false

    override fun getItemCount(): Int {
        return movieList.size + if (isLoading) 1 else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ViewHolderType.VIEW_TYPE_MOVIE.type) {
            MovieItemViewHolder(
                MovieItemBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        } else {
            LoadingViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.loading_item, parent, false)
            )
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieItemViewHolder)
            holder.bind(movieList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoading && position == movieList.size) ViewHolderType.VIEW_TYPE_LOADING.type else ViewHolderType.VIEW_TYPE_MOVIE.type
    }

    inner class LoadingViewHolder(view: android.view.View) : RecyclerView.ViewHolder(view)

    inner class MovieItemViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            binding.movieTitle.text = item.title
            binding.rateStar.text = item.vote_average.toString()
            changeBookmarkColour(item.isFavorite)
            Glide.with(binding.poster.rootView)
                .load(IMAGE_PATH + item.poster_path)
                .placeholder(R.drawable.loading1)
                .into(binding.poster)

            binding.root.setOnClickListener {
                onItemClickListener.invoke(item)
            }

            if (tabLayoutPosition == 3) {
                binding.bookmark.setImageResource(R.drawable.ic_delete_24)
                binding.bookmark.setOnClickListener {
                    onFavoriteClickListener.invoke(item)
                }
            } else {
                binding.bookmark.setOnClickListener {
                    item.isFavorite = !item.isFavorite
                    changeBookmarkColour(item.isFavorite)
                    onFavoriteClickListener.invoke(item)
                }
            }
        }

        private fun changeBookmarkColour(isFavorite: Boolean) {
            if (isFavorite) {
                val color = ContextCompat.getColor(context, R.color.yellow)
                binding.bookmark.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            } else {
                val color = ContextCompat.getColor(context, R.color.white)
                binding.bookmark.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            }
        }
    }

    fun showLoading(isLoading: Boolean) {
        if (this.isLoading == isLoading) return
        this.isLoading = isLoading
        if (isLoading) {
            notifyItemInserted(movieList.size)
        } else {
            notifyItemRemoved(movieList.size)
        }
    }

    fun addMovies(newMovies: List<Movie>) {
        val currentSize = movieList.size
        movieList.addAll(newMovies)
        notifyItemRangeInserted(currentSize, newMovies.size)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun resetMovies(newMovies: List<Movie>) {
        movieList.clear()
        movieList.addAll(newMovies)
        notifyDataSetChanged()
    }
}