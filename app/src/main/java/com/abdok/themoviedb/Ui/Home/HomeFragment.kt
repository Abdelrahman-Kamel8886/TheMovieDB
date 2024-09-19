package com.abdok.themoviedb.Ui.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.abdok.themoviedb.Network.RetroConnection
import com.abdok.themoviedb.R
import com.abdok.themoviedb.Ui.MyHome.MyHomeFragment
import com.abdok.themoviedb.Ui.Search.SearchFragment
import com.abdok.themoviedb.Ui.WatchList.WatchListFragment
import com.abdok.themoviedb.Utils.SharedModel
import com.abdok.themoviedb.databinding.FragmentHomeBinding
import com.abdok.themoviedb.databinding.FragmentMyHomeBinding
import np.com.susanthapa.curved_bottom_navigation.CbnMenuItem

class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        init()

    }



    private fun init(){
        val menuItems = arrayOf(
            CbnMenuItem(
                R.drawable.ic_baseline_search_avd,
                R.drawable.ic_baseline_search_avd
            ),
            CbnMenuItem(

                R.drawable.ic_baseline_home_avd,
                R.drawable.ic_baseline_home_avd
            ),
            CbnMenuItem(
                R.drawable.ic_baseline_bookmark_avd,
                R.drawable.ic_baseline_bookmark_avd
            )
        )
        binding.nav.setMenuItems(menuItems, SharedModel.startIndex)
        when(SharedModel.startIndex){
            0 -> replaceFragment(SearchFragment())
            1 -> replaceFragment(MyHomeFragment())
            2 -> replaceFragment(WatchListFragment())
        }

        binding.nav.setOnMenuItemClickListener { cbnMenuItem, index ->
            when(index){
                0 -> replaceFragment(SearchFragment())
                1 -> replaceFragment(MyHomeFragment())
                2 -> replaceFragment(WatchListFragment())
            }
        }


    }

    private fun replaceFragment(fragment: Fragment){
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.home_frame, fragment).commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
