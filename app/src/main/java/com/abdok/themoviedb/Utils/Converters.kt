package com.abdok.themoviedb.Utils

import androidx.room.TypeConverter
import com.abdok.themoviedb.Models.BelongsToCollection
import com.abdok.themoviedb.Models.Genre
import com.abdok.themoviedb.Models.ProductionCompany
import com.abdok.themoviedb.Models.ProductionCountry
import com.abdok.themoviedb.Models.SpokenLanguage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String>? {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }
    @TypeConverter
    fun fromBelongsToCollection(value: BelongsToCollection?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toBelongsToCollection(value: String): BelongsToCollection? {
        return Gson().fromJson(value, BelongsToCollection::class.java)
    }

    @TypeConverter
    fun fromGenreList(genres: List<Genre>?): String {
        return Gson().toJson(genres)
    }

    @TypeConverter
    fun toGenreList(value: String): List<Genre>? {
        val listType = object : TypeToken<List<Genre>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromProductionCompanyList(companies: List<ProductionCompany>?): String {
        return Gson().toJson(companies)
    }

    @TypeConverter
    fun toProductionCompanyList(value: String): List<ProductionCompany>? {
        val listType = object : TypeToken<List<ProductionCompany>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromProductionCountryList(countries: List<ProductionCountry>?): String {
        return Gson().toJson(countries)
    }

    @TypeConverter
    fun toProductionCountryList(value: String): List<ProductionCountry>? {
        val listType = object : TypeToken<List<ProductionCountry>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromSpokenLanguageList(languages: List<SpokenLanguage>?): String {
        return Gson().toJson(languages)
    }

    @TypeConverter
    fun toSpokenLanguageList(value: String): List<SpokenLanguage>? {
        val listType = object : TypeToken<List<SpokenLanguage>>() {}.type
        return Gson().fromJson(value, listType)
    }
}
