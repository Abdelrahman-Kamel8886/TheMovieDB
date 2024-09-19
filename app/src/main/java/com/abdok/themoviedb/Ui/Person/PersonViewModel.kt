package com.abdok.themoviedb.Ui.Person

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdok.themoviedb.Models.PersonDetailsModel
import com.abdok.themoviedb.Models.PersonImagesModel
import com.abdok.themoviedb.Models.PersonMoviesModel
import com.abdok.themoviedb.Network.RetroConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

class PersonViewModel : ViewModel() {

    var personDetail = MutableLiveData<PersonDetailsModel>()
    var personImages = MutableLiveData<PersonImagesModel>()
    var personMovies = MutableLiveData<PersonMoviesModel>()



    fun getPersonDetail(personId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val personDetailData = RetroConnection.retroServices.getPersonDetails(personId, Locale.getDefault().language)
            personDetail.postValue(personDetailData)

        }
    }

    fun getPersonImages(personId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val personImagesData = RetroConnection.retroServices.getPersonImages(personId, Locale.getDefault().language)
            personImages.postValue(personImagesData)
        }
    }

    fun getPersonMovies(personId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val personMoviesData = RetroConnection.retroServices.getPersonMovies(personId, Locale.getDefault().language)
            personMovies.postValue(personMoviesData)
        }
    }



}