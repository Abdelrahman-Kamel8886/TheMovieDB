package com.abdok.themoviedb.Adapters.PersonsAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdok.themoviedb.Models.MovieResult
import com.abdok.themoviedb.Utils.Consts
import com.abdok.themoviedb.databinding.ItemMovieBinding
import com.abdok.themoviedb.databinding.ItemTextBinding
import com.bumptech.glide.Glide

class RecyclerNamesAdapter : RecyclerView.Adapter<RecyclerNamesAdapter.Holder>() {


    var list : List<String> ?= null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int = list?.size ?: 0

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val actorName = list?.get(position)
        holder.binding.apply {
            ttxt.text = actorName


        }



    }

    inner class Holder(val binding: ItemTextBinding) : RecyclerView.ViewHolder(binding.root) {




    }
}