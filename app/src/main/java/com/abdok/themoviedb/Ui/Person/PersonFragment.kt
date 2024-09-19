package com.abdok.themoviedb.Ui.Person

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.abdok.themoviedb.Adapters.MoviesAdapters.RecyclerCastMoviesAdapter
import com.abdok.themoviedb.Adapters.MoviesAdapters.RecyclerCrewMoviesAdapter
import com.abdok.themoviedb.Adapters.PersonsAdapters.RecyclerNamesAdapter
import com.abdok.themoviedb.Adapters.PersonsAdapters.RecyclerPersonImagesAdapter
import com.abdok.themoviedb.Enums.PersonWorkEnum
import com.abdok.themoviedb.Models.PersonMoviesCast
import com.abdok.themoviedb.Models.PersonMoviesCrew
import com.abdok.themoviedb.R
import com.abdok.themoviedb.Ui.Movie.MovieFragment
import com.abdok.themoviedb.Ui.PersonWork.PersonWorkFragment
import com.abdok.themoviedb.Utils.Consts
import com.abdok.themoviedb.Utils.SharedModel
import com.abdok.themoviedb.databinding.FragmentPersonBinding
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel

class PersonFragment : Fragment() {

    private var _binding: FragmentPersonBinding?= null
    private val binding get() = _binding!!
    private val viewModel: PersonViewModel by viewModels()

    private val namesAdapter by  lazy { RecyclerNamesAdapter() }
    private val imagesAdapter by  lazy { RecyclerPersonImagesAdapter() }
    private val castMoviesAdapter by  lazy { RecyclerCastMoviesAdapter() }
    private val crewMoviesAdapter by  lazy { RecyclerCrewMoviesAdapter() }

    private var castList : List<PersonMoviesCast>? = null
    private var crewList : List<PersonMoviesCrew>?= null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_person, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPersonBinding.bind(view)
        getData(SharedModel.selectedPersonId!!)
        onClicks()

    }

    @SuppressLint("SetTextI18n")
    private fun getData(id : Int){
        viewModel.getPersonDetail(id)
        viewModel.getPersonImages(id)
        viewModel.getPersonMovies(id)

        viewModel.personDetail.observe(viewLifecycleOwner){
            binding.apply {
                SharedModel.selectedPersonName = it.name
                name.text =it.name
                rule.text = it.known_for_department
                imdb.text = it.imdb_id
                place.text = it.place_of_birth
                Popularity.text = it.popularity.toString()
                Country.text = it.place_of_birth.split(",").last()


                if (it.biography.isEmpty()) {
                    biography.visibility = View.GONE
                    txt1.visibility = View.GONE
                } else {
                    biography.text = it.biography

                }

                date.text = if (it.deathday != null) {
                    "${it.birthday} to ${it.deathday}"
                } else {
                    it.birthday
                }

                Glide.with(requireActivity())
                    .load(Consts.IMAGE_URL +it.profile_path)
                    .into(binding.profile);

                namesAdapter.list = it.also_known_as
                recyclerknown.adapter = namesAdapter

            }
        }
        viewModel.personImages.observe(viewLifecycleOwner){
            binding.apply {
                val list = it.profiles
                val slideListImage = ArrayList<SlideModel>()

                for (i in list){
                    slideListImage.add(SlideModel(Consts.IMAGE_URL+i.file_path ,ScaleTypes.FIT ))
                }
                imageSlider.setImageList(slideListImage)

                if (list.isEmpty()){
                    recyclerimages.visibility = View.GONE
                    txt2.visibility = View.GONE
                }

                imagesAdapter.list = list
                recyclerimages.adapter = imagesAdapter


            }
        }
        viewModel.personMovies.observe(viewLifecycleOwner){
            binding.apply {

                castList = it.cast
                crewList = it.crew

                if (castList?.isEmpty()!!){
                    recyclermovies1.visibility = View.GONE
                    txt3.visibility = View.GONE
                    seeallcast.visibility = View.GONE
                }
                else{
                    castMoviesAdapter.list = if (castList?.size!! > 5){
                         castList?.subList(0,5)
                    }
                    else{
                        castList
                    }

                    recyclermovies1.adapter = castMoviesAdapter
                }

                if (crewList?.isEmpty()!!){
                    recyclermovies2.visibility = View.GONE
                    txt4.visibility = View.GONE
                    seeallcrew.visibility = View.GONE
                }
                else{

                    crewMoviesAdapter.list = if (crewList?.size!! > 5){
                        crewList?.subList(0,5)
                    }
                    else{
                        crewList
                    }
                    recyclermovies2.adapter = crewMoviesAdapter
                }

                bar.visibility = View.GONE
                mainLayout.visibility = View.VISIBLE

            }
        }

    }

    private fun onClicks() {
        binding.apply {
            back.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
            seeallcast.setOnClickListener {
                SharedModel.selectedWork = PersonWorkEnum.CAST
                SharedModel.selectedPersonCastMovies = castList
                navigateToWork()
            }
            seeallcrew.setOnClickListener {
                SharedModel.selectedWork = PersonWorkEnum.CREW
                SharedModel.selectedPersonCrewMovies = crewList
                navigateToWork()
            }

        }
        // adapters clicks
        castMoviesAdapter.onItemClicked = {
            navigateToMovieDetail(it)
        }
        crewMoviesAdapter.onItemClicked = {
            navigateToMovieDetail(it)
        }
    }

    private fun navigateToWork(){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame, PersonWorkFragment(),"workFragment")
            .commit()

    }private fun navigateToMovieDetail(movieId : Int) {
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