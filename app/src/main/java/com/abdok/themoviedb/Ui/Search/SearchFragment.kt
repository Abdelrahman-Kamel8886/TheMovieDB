package com.abdok.themoviedb.Ui.Search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.abdok.themoviedb.Adapters.MoviesAdapters.RecyclerCategoriesMovieAdapter
import com.abdok.themoviedb.Interfaces.BackListener
import com.abdok.themoviedb.R
import com.abdok.themoviedb.Ui.Home.HomeFragment
import com.abdok.themoviedb.Ui.Movie.MovieFragment
import com.abdok.themoviedb.Utils.SharedModel
import com.abdok.themoviedb.databinding.FragmentSearchBinding

class SearchFragment : Fragment() , BackListener {

    companion object{
        var listener : BackListener? = null
    }

    private val viewModel: SearchViewModel by viewModels()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val moviesAdapter by lazy { RecyclerCategoriesMovieAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        SharedModel.startIndex = 0
        onClicks()
    }

    private fun onClicks(){
        binding.apply {
            search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    hideKeyboardAndClearFocus()
                    return true
            }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        viewModel.searchMovies(newText)
                        viewModel.searchedMovies.observe(viewLifecycleOwner) {
                            moviesAdapter.list = it.results
                            recycler.adapter = moviesAdapter
                            moviesAdapter.onItemClicked = {
                                navigateToMovieDetail(it)
                            }
                        }
                    }
                    return true

                }
            })
        }
    }
    private fun navigateToMovieDetail(movieId : Int) {
        SharedModel.selectedMovieId = movieId
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame, MovieFragment(), "MovieDetailFragment")
            .addToBackStack("MovieDetailFragment")
            .commit()

    }

    private fun hideKeyboardAndClearFocus() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.hideSoftInputFromWindow(binding.search.getWindowToken(), 0)
        binding.search.clearFocus()
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