package com.abdok.contacts.Local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.abdok.themoviedb.Models.MovieDetailsModel

@Dao
interface MyDao {

    @Query("SELECT * FROM MovieDetailsModel")
    suspend fun getAllMovies(): List<MovieDetailsModel>

    @Insert
    suspend fun insertMovie(movie: MovieDetailsModel)

    @Delete
    suspend fun deleteMovie(movie: MovieDetailsModel)

    @Query("SELECT COUNT(*) FROM MovieDetailsModel WHERE id = :movieId")
    suspend fun isMovieExists(movieId: Int): Int


}