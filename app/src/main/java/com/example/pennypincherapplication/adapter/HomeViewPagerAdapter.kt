package com.example.pennypincherapplication.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.pennypincherapplication.activity.ExpenseFragment
import com.example.pennypincherapplication.activity.TodaysExpenseFragment

class HomeViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ExpenseFragment()
            1 -> TodaysExpenseFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }

    override fun getCount(): Int {
        return 2
    }
}
