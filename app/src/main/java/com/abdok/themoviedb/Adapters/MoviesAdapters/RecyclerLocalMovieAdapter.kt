package com.abdok.themoviedb.Adapters.MoviesAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdok.themoviedb.Models.MovieDetailsModel
import com.abdok.themoviedb.Models.MovieResult
import com.abdok.themoviedb.Utils.Consts
import com.abdok.themoviedb.databinding.ItemLocalmovieBinding
import com.abdok.themoviedb.databinding.ItemMovieBinding
import com.bumptech.glide.Glide
import java.text.DecimalFormat

class RecyclerLocalMovieAdapter : RecyclerView.Adapter<RecyclerLocalMovieAdapter.Holder>() {


    var list : List<MovieDetailsModel> ?= null
    var onItemClicked : ((MovieDetailsModel) -> Unit) ?= null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemLocalmovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int = list?.size ?: 0

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val model = list?.get(position)
        holder.binding.apply {
            movieTitle.text = model?.title
            language.text = model?.original_language
            date.text = "${ model?.release_date!!.substring(0, 4)}"

            val rateNo = model?.vote_average.toString()
            rate.text = if (rateNo.endsWith(".0")) {
                rateNo
            } else {
                DecimalFormat("0.0").format(model?.vote_average)
            }

            if (model?.poster_path != null){
                Glide.with(holder.itemView.context).
                load(Consts.IMAGE_URL + model?.poster_path)
                    .into(localImg)
            }

            root.setOnClickListener{
                onItemClicked?.invoke(model!!)
            }


        }



    }

    inner class Holder(val binding: ItemLocalmovieBinding) : RecyclerView.ViewHolder(binding.root) {




    }
}