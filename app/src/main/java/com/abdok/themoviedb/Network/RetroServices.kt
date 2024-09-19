package com.abdok.themoviedb.Network

import com.abdok.themoviedb.Models.MovieDetailsModel
import com.abdok.themoviedb.Models.MovieImagesModel
import com.abdok.themoviedb.Models.MovieModel
import com.abdok.themoviedb.Models.PersonDetailsModel
import com.abdok.themoviedb.Models.PersonImagesModel
import com.abdok.themoviedb.Models.PersonModel
import com.abdok.themoviedb.Models.PersonMoviesModel
import com.abdok.themoviedb.Models.PersonSearchModel
import com.abdok.themoviedb.Models.VideoModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetroServices {


    //Home Page functions
    @GET("discover/movie")
    suspend fun discoverMovies(@Query("language") lang: String?): MovieModel

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("language") lang: String?): MovieModel

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("language") lang: String?): MovieModel

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("language") lang: String?): MovieModel

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("language") lang: String?): MovieModel

    // Movie Page functions

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") lang: String?
    ): MovieDetailsModel

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("language") lang: String?
    ): MovieModel

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int,
        @Query("language") lang: String?
    ): PersonModel

    @GET("movie/{movie_id}/images")
    suspend fun getMovieImages(
        @Path("movie_id") movieId: Int,
        @Query("language") lang: String?
    ): MovieImagesModel

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query("language") lang: String?
    ): VideoModel

    // Person Page functions

    @GET("person/{person_id}")
    suspend fun getPersonDetails(
        @Path("person_id") personId: Int,
        @Query("language") lang: String?
    ): PersonDetailsModel

    @GET("person/{person_id}/images")
    suspend fun getPersonImages(
        @Path("person_id") personId: Int,
        @Query("language") lang: String?
    ):PersonImagesModel

    @GET("person/{person_id}/movie_credits")
    suspend fun getPersonMovies(
        @Path("person_id") personId: Int,
        @Query("language") lang: String?
    ): PersonMoviesModel

    // Search Page functions

    @GET("search/movie")
    suspend fun searchForMovie(
        @Query("query") query: String?,
        @Query("language") lang: String?
    ): MovieModel

    @GET("search/movie")
    suspend fun searchForPerson(
        @Query("query") query: String?,
        @Query("language") lang: String?
    ): PersonSearchModel










}