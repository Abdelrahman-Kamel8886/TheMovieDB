package com.abdok.themoviedb.Ui.Movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdok.contacts.Local.MyDatabase
import com.abdok.themoviedb.Enums.StateEnum
import com.abdok.themoviedb.Models.MovieDetailsModel
import com.abdok.themoviedb.Models.MovieImagesModel
import com.abdok.themoviedb.Models.MovieModel
import com.abdok.themoviedb.Models.PersonModel
import com.abdok.themoviedb.Models.VideoModel
import com.abdok.themoviedb.Network.RetroConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

class MovieViewModel : ViewModel() {

    var movieDetail = MutableLiveData<MovieDetailsModel>()
    var movieImages = MutableLiveData<MovieImagesModel>()
    var movieVideos = MutableLiveData<VideoModel>()
    var movieCredits = MutableLiveData<PersonModel>()
    var similarMovies = MutableLiveData<MovieModel>()
    var movieState = MutableLiveData<StateEnum>()
    var insertState = MutableLiveData<Int>()
    var deleteState = MutableLiveData<Int>()


    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
           val movieDetailData = RetroConnection.retroServices.getMovieDetails(movieId , Locale.getDefault().language)
            movieDetail.postValue(movieDetailData)

        }
    }

    fun getMovieImages(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val movieImagesData =
                RetroConnection.retroServices.getMovieImages(movieId, Locale.getDefault().language)
            movieImages.postValue(movieImagesData)

        }
    }
    fun getTrailers(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val movieVideosData =
                RetroConnection.retroServices.getMovieVideos(movieId, Locale.getDefault().language)
            movieVideos.postValue(movieVideosData)
        }
    }
    fun getCredits(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val movieCastData =
                RetroConnection.retroServices.getMovieCredits(movieId, Locale.getDefault().language)
            movieCredits.postValue(movieCastData)
        }
    }
    fun getSimilarMovies(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val similarMoviesData =
                RetroConnection.retroServices.getSimilarMovies(movieId, Locale.getDefault().language)
            similarMovies.postValue(similarMoviesData)
        }
    }
    fun getMovieLocalState(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val movieStateData = MyDatabase.getInstance().getDao().isMovieExists(movieId)
            if (movieStateData == 1){
                movieState.postValue(StateEnum.Exist)
            }
            else{
                movieState.postValue(StateEnum.NotExist)
            }

        }
    }
    fun insertMovie(movie: MovieDetailsModel) {
        viewModelScope.launch(Dispatchers.IO) {
            MyDatabase.getInstance().getDao().insertMovie(movie)
            insertState.postValue(1)
        }
    }
    fun deleteMovie(movie: MovieDetailsModel) {
        viewModelScope.launch(Dispatchers.IO) {
            MyDatabase.getInstance().getDao().deleteMovie(movie)
            deleteState.postValue(1)
        }
    }



}