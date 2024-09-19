package com.abdok.themoviedb.Ui.WatchList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdok.contacts.Local.MyDatabase
import com.abdok.themoviedb.Models.MovieDetailsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WatchListViewModel : ViewModel() {
    val localMovies = MutableLiveData<List<MovieDetailsModel>>()

    fun getLocalMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val localMoviesData = MyDatabase.getInstance().getDao().getAllMovies()
            localMovies.postValue(localMoviesData)
        }
    }
}