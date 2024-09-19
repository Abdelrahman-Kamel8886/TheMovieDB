package com.abdok.themoviedb.Ui.Search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdok.themoviedb.Models.MovieModel
import com.abdok.themoviedb.Network.RetroConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

class SearchViewModel : ViewModel() {

    val searchedMovies = MutableLiveData<MovieModel>()


    fun searchMovies(movieName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val searchData = RetroConnection.retroServices.searchForMovie(movieName , Locale.getDefault().language)
            searchedMovies.postValue(searchData)
        }
    }
}