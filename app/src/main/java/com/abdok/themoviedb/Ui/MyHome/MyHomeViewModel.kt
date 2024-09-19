package com.abdok.themoviedb.Ui.MyHome

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdok.themoviedb.Models.MovieModel
import com.abdok.themoviedb.Network.RetroConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

class MyHomeViewModel : ViewModel() {

    val popularMovies = MutableLiveData<MovieModel>()
    val topRatedMovies = MutableLiveData<MovieModel>()
    val nowPlayingMovies = MutableLiveData<MovieModel>()
    val upcomingMovies = MutableLiveData<MovieModel>()

    fun getPopularMovies() {
        viewModelScope.launch(Dispatchers.IO){
            val popularData = RetroConnection.retroServices.getPopularMovies(Locale.getDefault().language)
            popularMovies.postValue(popularData)

        }
    }

    fun getTopRatedMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val topRatedData= RetroConnection.retroServices.getTopRatedMovies(Locale.getDefault().language)
            topRatedMovies.postValue(topRatedData)
        }
    }

    fun getNowPlayingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val nowPlayingData = RetroConnection.retroServices.getNowPlayingMovies(Locale.getDefault().language)
            nowPlayingMovies.postValue(nowPlayingData)
        }

    }

    fun getUpcomingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val upcomingData = RetroConnection.retroServices.getUpcomingMovies(Locale.getDefault().language)
            upcomingMovies.postValue(upcomingData)
        }
    }


}