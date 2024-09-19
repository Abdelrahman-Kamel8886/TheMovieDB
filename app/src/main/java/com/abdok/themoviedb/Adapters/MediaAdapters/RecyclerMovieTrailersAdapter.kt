package com.abdok.themoviedb.Adapters.MediaAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdok.themoviedb.Models.VideoResult
import com.abdok.themoviedb.Utils.Consts
import com.abdok.themoviedb.databinding.ItemTrailerBinding
import com.bumptech.glide.Glide

class RecyclerMovieTrailersAdapter : RecyclerView.Adapter<RecyclerMovieTrailersAdapter.Holder>() {


    var list : List<VideoResult> ?= null
    var onItemClicked : ((String) -> Unit) ?= null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemTrailerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int = list?.size ?: 0

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val model = list?.get(position)
        holder.binding.apply {

            val thumbnail =Consts.YOUTUBE_THUMBNAIL_FIRST_URL+model?.key+Consts.YOUTUBE_THUMBNAIL_SECOND_URL
            Glide.with(holder.itemView.context)
                .load(thumbnail)
                .into(TrailerImage)

            root.setOnClickListener {
                onItemClicked?.invoke(model?.key!!)
            }


        }
    }

    inner class Holder(val binding: ItemTrailerBinding) : RecyclerView.ViewHolder(binding.root) {


    }
}