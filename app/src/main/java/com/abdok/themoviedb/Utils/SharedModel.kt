package com.abdok.themoviedb.Utils

import com.abdok.themoviedb.Enums.CategoriesEnum
import com.abdok.themoviedb.Enums.PersonWorkEnum
import com.abdok.themoviedb.Models.MovieDetailsModel
import com.abdok.themoviedb.Models.MovieResult
import com.abdok.themoviedb.Models.PersonCast
import com.abdok.themoviedb.Models.PersonCrew
import com.abdok.themoviedb.Models.PersonMoviesCast
import com.abdok.themoviedb.Models.PersonMoviesCrew

object SharedModel {

    var startIndex = 1

    var selectedLocalMovie: MovieDetailsModel? = null

    var selectedCategory: CategoriesEnum? = null
    var selectedWork: PersonWorkEnum? = null


    var popularMovies: List<MovieResult>? = null
    var topRatedMovies: MutableList<MovieResult>? = null
    var upcomingMovies: MutableList<MovieResult>? = null
    var nowPlayingMovies: MutableList<MovieResult>? = null

    var selectedPersonCastMovies: List<PersonMoviesCast>? = null
    var selectedPersonCrewMovies: List<PersonMoviesCrew>? = null
    var selectedPersonName: String? = null

    var selectedMovieId: Int? = null
    var selectedPersonId: Int? = null

    fun clearData() {
        popularMovies = null
        topRatedMovies = null
        upcomingMovies = null
        nowPlayingMovies = null
    }

}