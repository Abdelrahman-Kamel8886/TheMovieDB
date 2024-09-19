package com.abdok.themoviedb.Ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abdok.themoviedb.Interfaces.BackListener
import com.abdok.themoviedb.Ui.Home.HomeFragment
import com.abdok.themoviedb.Ui.MyHome.MyHomeFragment
import com.abdok.themoviedb.Ui.Search.SearchFragment
import com.abdok.themoviedb.Ui.WatchList.WatchListFragment
import com.abdok.themoviedb.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(binding.frame.id, HomeFragment()).commit()


    }

    override fun onBackPressed() {
        if (WatchListFragment.listener != null) {
            WatchListFragment.listener!!.onBackPressed()
        }
        else if (SearchFragment.listener != null)  {
            SearchFragment.listener!!.onBackPressed()
        }
        else if (MyHomeFragment.listener != null) {
            MyHomeFragment.listener!!.onBackPressed()
        }
        else {
            super.onBackPressed()
        }

        }

}