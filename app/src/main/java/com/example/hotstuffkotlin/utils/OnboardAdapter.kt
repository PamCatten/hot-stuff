package com.example.hotstuffkotlin.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Create a helper object to perform basic CRUD operations with SQL on the database.
 * @param list
 * @param fm
 * @param lifecycle
 * @author Cam Patten
 */
class OnboardAdapter(list: ArrayList<Fragment>, fm: FragmentManager, lifecycle: Lifecycle):
    FragmentStateAdapter(fm, lifecycle) {
    private val fragmentList = list
    override fun getItemCount(): Int {
        return fragmentList.size
    }
    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}