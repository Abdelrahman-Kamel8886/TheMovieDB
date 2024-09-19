package com.abdok.themoviedb.Ui.MyHome

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.abdok.themoviedb.Adapters.MoviesAdapters.RecyclerMovieAdapter
import com.abdok.themoviedb.Adapters.MoviesAdapters.RecyclerNowPlayingMovieAdapter
import com.abdok.themoviedb.Enums.CategoriesEnum
import com.abdok.themoviedb.Interfaces.BackListener
import com.abdok.themoviedb.Models.MovieResult
import com.abdok.themoviedb.R
import com.abdok.themoviedb.Ui.Category.CategoryFragment
import com.abdok.themoviedb.Ui.Movie.MovieFragment
import com.abdok.themoviedb.Utils.SharedModel
import com.abdok.themoviedb.databinding.FragmentMyHomeBinding

class MyHomeFragment : Fragment() , BackListener {

    companion object{
        var listener : BackListener? = null
    }

    private var _binding: FragmentMyHomeBinding ?= null
    private val binding get() = _binding!!

    private val viewModel: MyHomeViewModel by  viewModels()

    private val popularMovieAdapter by  lazy { RecyclerMovieAdapter() }
    private val topRatedMovieAdapter by  lazy { RecyclerMovieAdapter() }
    private val upComingMovieAdapter by  lazy { RecyclerMovieAdapter() }
    private val nowPlayingMovieAdapter by  lazy { RecyclerNowPlayingMovieAdapter() }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_my_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMyHomeBinding.bind(view)
        SharedModel.startIndex = 1


        if (SharedModel.popularMovies == null || SharedModel.topRatedMovies == null ||
            SharedModel.upcomingMovies == null || SharedModel.nowPlayingMovies == null){
            getData()
        }
        else{
            binding.apply {
                bar.visibility = View.GONE
                mainLayout.visibility = View.VISIBLE
                getLocalData()
            }
        }



    }

    private fun getLocalData(){
        popularMovieAdapter.list = SharedModel.popularMovies?.subList(0,6)
        topRatedMovieAdapter.list = SharedModel.topRatedMovies?.subList(0,6)
        upComingMovieAdapter.list = SharedModel.upcomingMovies?.subList(0,6)
        nowPlayingMovieAdapter.list = SharedModel.nowPlayingMovies

        binding.recycler1.adapter = popularMovieAdapter
        binding.recycler2.adapter = topRatedMovieAdapter
        binding.recycler3.adapter = nowPlayingMovieAdapter
        binding.recycler4.adapter = upComingMovieAdapter

        onClicks()
    }

    private fun getData() {
        viewModel.getPopularMovies()
        viewModel.getTopRatedMovies()
        viewModel.getUpcomingMovies()
        viewModel.getNowPlayingMovies()

        viewModel.popularMovies.observe(viewLifecycleOwner) {
            SharedModel.popularMovies = it.results as? MutableList<MovieResult>
            popularMovieAdapter.list = it.results.subList(0,6) as? MutableList<MovieResult>
            binding.recycler1.adapter = popularMovieAdapter
        }
        viewModel.topRatedMovies.observe(viewLifecycleOwner) {
            SharedModel.topRatedMovies = it.results as? MutableList<MovieResult>
            topRatedMovieAdapter.list = it.results.subList(0,6) as? MutableList<MovieResult>
            binding.recycler2.adapter = topRatedMovieAdapter
        }
        viewModel.nowPlayingMovies.observe(viewLifecycleOwner) {
            SharedModel.nowPlayingMovies = it.results as? MutableList<MovieResult>
            nowPlayingMovieAdapter.list = it.results as? MutableList<MovieResult>
            binding.recycler3.adapter = nowPlayingMovieAdapter
        }
        viewModel.upcomingMovies.observe(viewLifecycleOwner) {
            SharedModel.upcomingMovies = it.results as? MutableList<MovieResult>
            upComingMovieAdapter.list = it.results.subList(0,6) as? MutableList<MovieResult>
            binding.recycler4.adapter = upComingMovieAdapter
            binding.apply {
                bar.visibility = View.GONE
                mainLayout.visibility = View.VISIBLE
            }
        }
        onClicks()
    }

    private fun onClicks(){
        binding.apply {
            seeallpopular.setOnClickListener {
               SharedModel.selectedCategory = CategoriesEnum.POPULAR
                navigateToCategory()
            }
            seealltop.setOnClickListener {
                SharedModel.selectedCategory = CategoriesEnum.TOP_RATED
                navigateToCategory()
            }
            seeallupcoming.setOnClickListener {
                SharedModel.selectedCategory = CategoriesEnum.UPCOMING
                navigateToCategory()
            }

        }

        // adapters clicks

        popularMovieAdapter.onItemClicked ={
            navigateToMovieDetail(it)
        }
        topRatedMovieAdapter.onItemClicked ={
            navigateToMovieDetail(it)
        }
        upComingMovieAdapter.onItemClicked = {
            navigateToMovieDetail(it)
        }
        nowPlayingMovieAdapter.onItemClicked = {
            navigateToMovieDetail(it)
        }

    }

    private fun navigateToCategory(){
        listener = null
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame, CategoryFragment(),"CategoryFragment")
            .addToBackStack("CategoryFragment").commit()

    }
    private fun navigateToMovieDetail(movieId : Int) {
        listener = null
        SharedModel.selectedMovieId = movieId
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame, MovieFragment(), "MovieDetailFragment")
            .addToBackStack("MovieDetailFragment").commit()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        listener = null
    }
    override fun onPause() {
        super.onPause()
        listener = null
    }

    override fun onResume() {
        super.onResume()
        listener = this

    }

    override fun onBackPressed() {
        AlertDialog.Builder(requireActivity()).setMessage(R.string.exit).setCancelable(true).setPositiveButton(R.string.yes)
        { dialogInterface, i ->requireActivity().finish() }
            .setNegativeButton(R.string.no, null)
            .show()
    }
}



