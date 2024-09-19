package com.abdok.contacts.Local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.abdok.themoviedb.Models.MovieDetailsModel
import com.abdok.themoviedb.Utils.Consts
import com.abdok.themoviedb.Utils.Converters

@Database(entities = [MovieDetailsModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class MyDatabase : RoomDatabase() {

    abstract fun getDao(): MyDao

    companion object {
        private lateinit var myDatabase: MyDatabase
        fun init(context: Context){
            myDatabase = Room.
            databaseBuilder(context, MyDatabase::class.java, Consts.DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
        fun getInstance() = myDatabase

    }


}