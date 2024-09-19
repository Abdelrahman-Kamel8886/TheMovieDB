package com.abdok.themoviedb.Models

data class PersonImagesModel(
    val id: Int,
    val profiles: List<PersonProfile>
)

data class PersonProfile(
    val aspect_ratio: Double,
    val file_path: String,
    val height: Int,
    val iso_639_1: Any,
    val vote_average: Double,
    val vote_count: Int,
    val width: Int
)