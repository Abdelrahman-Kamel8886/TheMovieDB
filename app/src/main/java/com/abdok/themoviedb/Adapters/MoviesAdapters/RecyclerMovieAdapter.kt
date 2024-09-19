package com.abdok.themoviedb.Adapters.MoviesAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdok.themoviedb.Models.MovieResult
import com.abdok.themoviedb.Utils.Consts
import com.abdok.themoviedb.databinding.ItemMovieBinding
import com.bumptech.glide.Glide

class RecyclerMovieAdapter : RecyclerView.Adapter<RecyclerMovieAdapter.Holder>() {


    var list : List<MovieResult> ?= null
    var onItemClicked : ((Int) -> Unit) ?= null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int = list?.size ?: 0

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val model = list?.get(position)
        holder.binding.apply {
            movieTitle.text = model?.title
            if (model?.poster_path != null){
                Glide.with(holder.itemView.context).
                load(Consts.IMAGE_URL + model?.poster_path)
                    .into(imageMovie)
            }

            root.setOnClickListener{
                onItemClicked?.invoke(model?.id!!)
            }


        }



    }

    inner class Holder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {




    }
}