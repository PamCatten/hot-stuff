package com.hotstuff.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * A bridge class that prepares a list of fragments to be managed by a ViewPager2 interface.
 * @param list The list of fragments to be displayed in the ViewPager.
 * @param fm The interface responsible for interacting with the fragment objects.
 * @param lifecycle The object that holds the status of the ViewPager's state.
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