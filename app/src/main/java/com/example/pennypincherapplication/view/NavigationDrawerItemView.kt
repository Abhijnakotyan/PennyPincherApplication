package com.example.pennypincherapplication.view

import android.content.Intent
import androidx.fragment.app.Fragment
import com.example.pennypincherapplication.activity.AddCategoryActivity

interface NavigationDrawerItemView {
    fun render(fragment: Fragment)
    fun goToHome()
}
