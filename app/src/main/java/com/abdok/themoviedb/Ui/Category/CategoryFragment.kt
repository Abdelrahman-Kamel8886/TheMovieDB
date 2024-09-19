package com.abdok.themoviedb.Ui.Category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abdok.themoviedb.Adapters.MoviesAdapters.RecyclerCategoriesMovieAdapter
import com.abdok.themoviedb.Enums.CategoriesEnum
import com.abdok.themoviedb.R
import com.abdok.themoviedb.Ui.Movie.MovieFragment
import com.abdok.themoviedb.Utils.SharedModel
import com.abdok.themoviedb.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {

    private val adapter by lazy { RecyclerCategoriesMovieAdapter() }

    private var _binding: FragmentCategoryBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCategoryBinding.bind(view)
        init()

    }

    private fun init(){
        binding.apply {
            title.text = getString(SharedModel.selectedCategory!!.titleId)
            when(SharedModel.selectedCategory!!){
                CategoriesEnum.POPULAR -> adapter.list = SharedModel.popularMovies
                CategoriesEnum.TOP_RATED -> adapter.list = SharedModel.topRatedMovies
                CategoriesEnum.UPCOMING -> adapter.list = SharedModel.upcomingMovies
            }
            recycler.adapter = adapter

        }
        onClicks()

    }

    private fun onClicks(){
        binding.apply {
            back.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
        adapter.onItemClicked = {
            navigateToMovieDetail(it)
        }

    }

    private fun navigateToMovieDetail(movieId : Int) {
        SharedModel.selectedMovieId = movieId
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame, MovieFragment(), "MovieDetailFragment")
            .commit()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}