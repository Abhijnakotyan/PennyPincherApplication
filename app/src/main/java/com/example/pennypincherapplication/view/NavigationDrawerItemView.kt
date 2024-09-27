package com.example.pennypincherapplication.view

import androidx.fragment.app.Fragment

interface NavigationDrawerItemView {
    fun render(fragment: Fragment)
    fun goToHome()
}
