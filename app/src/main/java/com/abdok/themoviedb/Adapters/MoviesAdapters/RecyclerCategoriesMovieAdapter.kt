package com.abdok.themoviedb.Adapters.MoviesAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdok.themoviedb.Models.MovieResult
import com.abdok.themoviedb.Utils.Consts
import com.abdok.themoviedb.databinding.ItemMoviebigBinding
import com.bumptech.glide.Glide

class RecyclerCategoriesMovieAdapter : RecyclerView.Adapter<RecyclerCategoriesMovieAdapter.Holder>() {


    var list : List<MovieResult> ?= null
    var onItemClicked : ((Int) -> Unit) ?= null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemMoviebigBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int = list?.size ?: 0

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val model = list?.get(position)
        holder.binding.apply {
            movieTitle.text = model?.title
            Glide.with(holder.itemView.context).
            load(Consts.IMAGE_URL + model?.poster_path)
                .into(imageMovie)

            if (position == list?.size?.minus(1) || position == list?.size?.minus(2)) {
                mainLayout.visibility = View.VISIBLE
            }
            else {
                mainLayout.visibility = View.GONE
            }
            root.setOnClickListener {
                onItemClicked?.invoke(model?.id!!)

            }

        }
    }

    inner class Holder(val binding: ItemMoviebigBinding) : RecyclerView.ViewHolder(binding.root) {


    }
}