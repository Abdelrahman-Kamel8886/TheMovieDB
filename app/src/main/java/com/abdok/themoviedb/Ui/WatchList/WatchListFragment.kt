package com.abdok.themoviedb.Ui.WatchList

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abdok.themoviedb.Adapters.MoviesAdapters.RecyclerLocalMovieAdapter
import com.abdok.themoviedb.Interfaces.BackListener
import com.abdok.themoviedb.Models.MovieDetailsModel
import com.abdok.themoviedb.R
import com.abdok.themoviedb.Ui.Home.HomeFragment
import com.abdok.themoviedb.Ui.Movie.MovieFragment
import com.abdok.themoviedb.Ui.MyHome.MyHomeFragment
import com.abdok.themoviedb.Utils.SharedModel
import com.abdok.themoviedb.databinding.FragmentWatchListBinding
import np.com.susanthapa.curved_bottom_navigation.CurvedBottomNavigationView

class WatchListFragment : Fragment() , BackListener {

    companion object{
        var listener : BackListener? = null
    }

    private val viewModel: WatchListViewModel by viewModels()
    private var _binding: FragmentWatchListBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy {RecyclerLocalMovieAdapter()}


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_watch_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWatchListBinding.bind(view)
        SharedModel.startIndex = 2
        getData()
        onClicks()

    }

    private fun getData(){
        viewModel.getLocalMovies()
        viewModel.localMovies.observe(viewLifecycleOwner){

            if (it.isEmpty()){
                showEmpty()
            }
            else{
                showData(it)
            }

        }
    }

    private fun onClicks(){
        binding.apply {
            back.setOnClickListener {
              onBackPressed()
            }
        }
        adapter.onItemClicked = {
            navigateToMovieDetail(it)
        }

    }

    private fun showData(list : List<MovieDetailsModel>){
        adapter.list = list
        binding.recycler.adapter = adapter
        binding.apply {
            empty.visibility = View.GONE
            recycler.visibility = View.VISIBLE
        }
    }
    private fun showEmpty(){
        binding.apply {
            empty.visibility = View.VISIBLE
            recycler.visibility = View.GONE
        }
    }
    private fun navigateToMovieDetail(movie : MovieDetailsModel) {
        SharedModel.selectedMovieId = movie.id
        SharedModel.selectedLocalMovie = movie
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame, MovieFragment(), "MovieDetailFragment")
            .addToBackStack("MovieDetailFragment")
            .commit()

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
        SharedModel.startIndex = 1
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.frame, HomeFragment()).commit()
    }
}