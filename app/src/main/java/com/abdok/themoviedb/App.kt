package com.abdok.themoviedb

import android.app.Application
import com.abdok.contacts.Local.MyDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MyDatabase.init(this)
    }

}