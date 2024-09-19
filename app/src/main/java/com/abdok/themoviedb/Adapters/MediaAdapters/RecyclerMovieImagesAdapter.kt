package com.abdok.themoviedb.Adapters.MediaAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdok.themoviedb.Models.Backdrop
import com.abdok.themoviedb.Utils.Consts
import com.abdok.themoviedb.databinding.ItemMovieImageBinding
import com.bumptech.glide.Glide

class RecyclerMovieImagesAdapter : RecyclerView.Adapter<RecyclerMovieImagesAdapter.Holder>() {


    var list : List<Backdrop> ?= null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemMovieImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int = list?.size ?: 0

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val model = list?.get(position)
        holder.binding.apply {
            Glide.with(holder.itemView.context).
            load(Consts.IMAGE_URL + model?.file_path)
                .into(MovieImage)

        }
    }

    inner class Holder(val binding: ItemMovieImageBinding) : RecyclerView.ViewHolder(binding.root) {


    }
}