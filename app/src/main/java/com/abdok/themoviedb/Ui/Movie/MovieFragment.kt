package com.abdok.themoviedb.Ui.Movie

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.abdok.themoviedb.Adapters.PersonsAdapters.RecyclerCastPersonAdapter
import com.abdok.themoviedb.Adapters.PersonsAdapters.RecyclerCrewPersonAdapter
import com.abdok.themoviedb.Adapters.MoviesAdapters.RecyclerMovieAdapter
import com.abdok.themoviedb.Adapters.MediaAdapters.RecyclerMovieImagesAdapter
import com.abdok.themoviedb.Adapters.MediaAdapters.RecyclerMovieTrailersAdapter
import com.abdok.themoviedb.Enums.StateEnum
import com.abdok.themoviedb.Models.MovieDetailsModel
import com.abdok.themoviedb.R
import com.abdok.themoviedb.Ui.Person.PersonFragment
import com.abdok.themoviedb.Utils.Consts
import com.abdok.themoviedb.Utils.SharedModel
import com.abdok.themoviedb.databinding.FragmentMovieBinding
import com.bumptech.glide.Glide
import java.text.DecimalFormat

class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding?= null
    private val binding get() = _binding!!
    private var checked = false


    private val viewModel: MovieViewModel by viewModels()

    private val movieImagesAdapter by lazy { RecyclerMovieImagesAdapter() }
    private val movieTrailersAdapter by lazy { RecyclerMovieTrailersAdapter() }
    private val movieCastAdapter by lazy { RecyclerCastPersonAdapter() }
    private val movieCrewAdapter by lazy { RecyclerCrewPersonAdapter() }
    private val similarMoviesAdapter by lazy { RecyclerMovieAdapter() }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_movie, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMovieBinding.bind(view)

        if (SharedModel.selectedLocalMovie != null){
            getLocalData()
        }
        else{
            getOnlineData(SharedModel.selectedMovieId!!)
        }


        onClicks()
    }

    private fun getLocalData(){
        checked = true

        binding.bar.visibility = View.GONE
        binding.mainLayout.visibility = View.VISIBLE
        showData(SharedModel.selectedLocalMovie!!)
        binding.book.setBackgroundResource(R.drawable.bookadded)
        completeOnlineData()

    }

    private fun getOnlineData(movieId: Int){

        viewModel.getMovieDetail(movieId)
        viewModel.getMovieLocalState(movieId)

        // check if movie is booked
        viewModel.movieState.observe(viewLifecycleOwner){
            when (it) {
                StateEnum.Exist ->{
                    binding.book.setBackgroundResource(R.drawable.bookadded)
                    checked = true
                }
                else -> {
                    binding.book.setBackgroundResource(R.drawable.unbooked)
                    checked = false
                }
            }
        }

        // get movie data
        viewModel.movieDetail.observe(viewLifecycleOwner){
            showData(it)
        }
        completeOnlineData()
        



    }
    
    
    
    private fun showData(it : MovieDetailsModel){
        binding.apply {
            title.text = it.title
            orginal.text = it.original_title
            language.text = it.original_language
            status.text = it.status
            revenue.text = "$${String.format("%,.2f", it.revenue.toDouble())}"
            votes.text = it.vote_count.toString()


            // date
            var countries = ""
            for (i in it.production_countries){
                countries += i.iso_3166_1 + " , "
            }
            countries = countries.substring(0, countries.length - 2)
            date.text = "${ it.release_date.substring(0, 4)} | $countries"

            // genres
            var genres = ""
            for (i in it.genres){
                genres += i.name + " , "
            }
            genres = genres.substring(0, genres.length - 2)
            information.text = genres


            // overview & tagline
            if (it.overview.isEmpty()) {
                overview.visibility = View.GONE
                binding.txt1.visibility = View.GONE
            }
            else {
                overview.text =it.overview
            }
            if (it.tagline.isEmpty()) {
                binding.tagline.visibility = View.GONE
            } else {
                binding.tagline.setText(it.tagline)
            }
            //vote rate
            val rateNo = it.vote_average.toString()
            rate.text = if (rateNo.endsWith(".0")) {
                rateNo
            } else {
                DecimalFormat("0.0").format(it.vote_average)
            }

            when(it.vote_average){
                in 0.0..3.9 -> card1.strokeColor = Color.RED
                in 4.0..5.9 -> card1.strokeColor = Color.YELLOW
                in 6.0..7.9 -> card1.strokeColor = Color.CYAN
                else -> card1.strokeColor = Color.GREEN
            }

            // images

            Glide.with(requireActivity())
                .load(Consts.IMAGE_URL + it.poster_path)
                .into(smallimg)

            Glide.with(requireActivity())
                .load(Consts.IMAGE_URL + it.backdrop_path)
                .into(bigimg)

        }
    }
    
    private fun completeOnlineData() {

        val movieId = SharedModel.selectedMovieId!!
        
        viewModel.getMovieImages(movieId)
        viewModel.getTrailers(movieId)
        viewModel.getCredits(movieId)
        viewModel.getSimilarMovies(movieId)
        
        viewModel.movieImages.observe(viewLifecycleOwner){
            binding.apply {
                val list = it.backdrops
                if (list.isEmpty()){
                    recyclerImages.visibility = View.GONE
                    imgtxt.visibility = View.GONE
                }
                else{
                    movieImagesAdapter.list = list
                    recyclerImages.adapter = movieImagesAdapter
                }

            }
        }
        viewModel.movieVideos.observe(viewLifecycleOwner){
            binding.apply {
                val list = it.results
                if (list.isEmpty()){
                    recyclerTrailers.visibility = View.GONE
                    txt2.visibility = View.GONE
                }
                else{
                    movieTrailersAdapter.list = list
                    recyclerTrailers.adapter = movieTrailersAdapter
                }
            }
        }
        viewModel.movieCredits.observe(viewLifecycleOwner){
            binding.apply {
                val castList = it.cast
                val crewList = it.crew

                if (castList.isEmpty()){
                    recyclerCast.visibility = View.GONE
                    txt3.visibility = View.GONE
                }
                else{
                    movieCastAdapter.list = castList
                    recyclerCast.adapter = movieCastAdapter
                }
                if (crewList.isEmpty()){
                    recyclerCrew.visibility = View.GONE
                    txt5.visibility = View.GONE
                }
                else{
                    movieCrewAdapter.list = crewList
                    recyclerCrew.adapter = movieCrewAdapter
                }
            }
        }
        viewModel.similarMovies.observe(viewLifecycleOwner){
            binding.apply {
                val list = it.results
                if (list.isEmpty()){
                    recyclersimilar.visibility = View.GONE
                    txt4.visibility = View.GONE
                }
                else{
                    similarMoviesAdapter.list = list
                    recyclersimilar.adapter = similarMoviesAdapter
                }
                bar.visibility = View.GONE
                mainLayout.visibility = View.VISIBLE
            }
        }
    }

    private fun onClicks(){
        binding.apply {
            back.setOnClickListener {
                SharedModel.selectedLocalMovie = null
                requireActivity().supportFragmentManager.popBackStack()
            }
            book.setOnClickListener {
                handleBooking()
            }
        }

        // adapters clicks
        similarMoviesAdapter.onItemClicked = {
            navigateToMovieDetail(it)
        }
        movieTrailersAdapter.onItemClicked = {
            navigateToYoutube(Consts.YOUTUBE_URL+it)
        }
        movieCrewAdapter.onItemClicked = {
            navigateToPersonDetail(it)
        }
        movieCastAdapter.onItemClicked = {
            navigateToPersonDetail(it)
        }

    }

    private fun navigateToMovieDetail(movieId : Int) {
        SharedModel.selectedMovieId = movieId
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame, MovieFragment(), "MovieDetailFragment")
            .commit()

    }

    private fun navigateToPersonDetail(personId : Int) {
        SharedModel.selectedPersonId = personId
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame, PersonFragment(), "personFragment")
            .commit()

    }

    private fun navigateToYoutube(url : String){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)

    }

    private fun handleBooking(){

        val movieModel = SharedModel.selectedLocalMovie ?: viewModel.movieDetail.value!!

        if (checked){
            viewModel.deleteMovie(movieModel)
            checked = false
            binding.book.setBackgroundResource(R.drawable.unbooked)
            Toast.makeText(requireContext(), getString(R.string.removedWatch), Toast.LENGTH_SHORT).show()
        }
        else{
            viewModel.insertMovie(movieModel)
            checked = true
            binding.book.setBackgroundResource(R.drawable.bookadded)
            Toast.makeText(requireContext(), getString(R.string.addWatch), Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}