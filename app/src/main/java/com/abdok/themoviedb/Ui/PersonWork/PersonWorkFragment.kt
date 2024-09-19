package com.abdok.themoviedb.Ui.PersonWork

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abdok.themoviedb.Adapters.MoviesAdapters.RecyclerCastMoviesAdapter
import com.abdok.themoviedb.Adapters.MoviesAdapters.RecyclerCastWorkAdapter
import com.abdok.themoviedb.Adapters.MoviesAdapters.RecyclerCrewMoviesAdapter
import com.abdok.themoviedb.Adapters.MoviesAdapters.RecyclerCrewWorkAdapter
import com.abdok.themoviedb.Enums.PersonWorkEnum
import com.abdok.themoviedb.R
import com.abdok.themoviedb.Ui.Movie.MovieFragment
import com.abdok.themoviedb.Utils.SharedModel
import com.abdok.themoviedb.databinding.FragmentPersonBinding
import com.abdok.themoviedb.databinding.FragmentPersonWorkBinding


class PersonWorkFragment : Fragment() {

    private var _binding: FragmentPersonWorkBinding?= null
    private val binding get() = _binding!!

    private val castMoviesAdapter by  lazy { RecyclerCastWorkAdapter() }
    private val crewMoviesAdapter by  lazy { RecyclerCrewWorkAdapter() }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_person_work, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPersonWorkBinding.bind(view)
        init()
    }

    private fun init(){

        binding.apply {
            title.text = SharedModel.selectedPersonName

            when(SharedModel.selectedWork!!){
                PersonWorkEnum.CAST -> {
                    castMoviesAdapter.list = SharedModel.selectedPersonCastMovies
                    recycler.adapter = castMoviesAdapter

                }
                PersonWorkEnum.CREW -> {
                    crewMoviesAdapter.list = SharedModel.selectedPersonCrewMovies
                    recycler.adapter = crewMoviesAdapter

                }
            }
        }
        onClicks()

    }
    private fun onClicks(){
        binding.apply {
            back.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
        castMoviesAdapter.onItemClicked = {
            navigateToMovieDetail(it)
        }
        crewMoviesAdapter.onItemClicked = {
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