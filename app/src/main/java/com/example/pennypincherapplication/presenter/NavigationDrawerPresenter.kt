package com.example.pennypincherapplication.presenter

import com.example.pennypincherapplication.activity.CurrentMonthExpenseFragment
import com.example.pennypincherapplication.activity.CurrentWeekExpenseFragment
import com.example.pennypincherapplication.view.NavigationDrawerItemView

class NavigationDrawerPresenter(private val view: NavigationDrawerItemView) {

    companion object {
        const val THIS_WEEK = "This Week"
        const val THIS_MONTH = "This Month"
        const val HOME = "Home"
    }

    fun onItemSelected(drawerItem: String) {
        when (drawerItem) {
            THIS_WEEK -> view.render(CurrentWeekExpenseFragment())
            THIS_MONTH -> view.render(CurrentMonthExpenseFragment())
            HOME -> view.goToHome()
        }
    }
}
