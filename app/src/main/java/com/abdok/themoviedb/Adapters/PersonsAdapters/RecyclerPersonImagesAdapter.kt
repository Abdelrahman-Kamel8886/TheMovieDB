package com.abdok.themoviedb.Adapters.PersonsAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdok.themoviedb.Models.PersonCast
import com.abdok.themoviedb.Models.PersonProfile
import com.abdok.themoviedb.Utils.Consts
import com.abdok.themoviedb.databinding.ItemPersonBinding
import com.bumptech.glide.Glide

class RecyclerPersonImagesAdapter : RecyclerView.Adapter<RecyclerPersonImagesAdapter.Holder>() {


    var list : List<PersonProfile> ?= null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int = list?.size ?: 0

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val model = list?.get(position)
        holder.binding.apply {
            personName.visibility = View.GONE
            personcharachter.visibility = View.GONE

            if (model?.file_path != null){
                Glide.with(holder.itemView.context)
                    .load(Consts.IMAGE_URL + model?.file_path)
                    .into(pesronImage)
            }



        }
    }

    inner class Holder(val binding: ItemPersonBinding) : RecyclerView.ViewHolder(binding.root) {


    }
}