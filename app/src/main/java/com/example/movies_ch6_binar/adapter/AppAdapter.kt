package com.example.movies_ch6_binar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movies_ch6_binar.Models.model.Movie
import com.example.movies_ch6_binar.databinding.ItemViewBinding
import com.example.movies_ch6_binar.utils.Constant
import com.example.movies_ch6_binar.utils.Extension.loadImage

class AppAdapter (private val onClick: (Movie) -> Unit) :
    ListAdapter<Movie, AppAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private val binding: ItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.apply {
                titleTextView.text = movie.title
                ratingTextView.text = movie.voteAverage.toString()
                popularityTextView.text = movie.popularity.toString()
                itemView.context.loadImage(
                    Constant.BASE_IMAGE_URL + movie.posterPath,
                    binding.posterImageView
                )
                root.setOnClickListener { onClick(movie) }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppAdapter.ViewHolder {
        val binding =
            ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AppAdapter.ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int = currentList.size

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}