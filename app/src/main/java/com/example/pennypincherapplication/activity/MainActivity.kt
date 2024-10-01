package com.example.pennypincherapplication.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.example.pennypincherapplication.R
import com.example.pennypincherapplication.adapter.DrawerListViewAdapter
import com.example.pennypincherapplication.adapter.HomeViewPagerAdapter
import com.example.pennypincherapplication.presenter.NavigationDrawerPresenter
import com.example.pennypincherapplication.view.NavigationDrawerItemView

class MainActivity : AppCompatActivity(), NavigationDrawerItemView, ActionBar.TabListener {

    private lateinit var viewPager: ViewPager
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var homeViewPagerAdapter: HomeViewPagerAdapter

    companion object {
        private const val PREFS_NAME = "UserSession"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_USERNAME = "username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = ""

        // Check if the user is logged in, if not redirect to LoginActivity
        val sharedPreferences: SharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
        if (!isLoggedIn) {
            // User is not logged in, redirect to LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return // Ensure the rest of onCreate is not executed
        }

        configureDrawer()
        configureActionBar()
    }

    override fun render(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val existingFragment = fragmentManager.findFragmentById(R.id.main_frame)

        if (existingFragment != null && existingFragment.javaClass == fragment.javaClass) return

        fragmentManager.beginTransaction()
            .addToBackStack(MainActivity::class.java.simpleName)
            .replace(R.id.main_frame, fragment, fragment.javaClass.simpleName)
            .commit()
    }

    override fun goToHome() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0) {
            supportActionBar?.navigationMode = ActionBar.NAVIGATION_MODE_TABS
            supportActionBar?.title = getString(R.string.app_name)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
//            R.id.action_add_category -> {
//                startActivityForResult(Intent(this, AddCategoryActivity::class.java), AddCategoryActivity.ADD_NEW_CAT)
//                true
//            }
            R.id.logout -> {
                handleLogout()
                true
            }
            else -> actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)
        }
    }

    private fun handleLogout() {
        // Clear user session from SharedPreferences
        val sharedPreferences: SharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            clear() // Clear all saved user data
            apply()
        }

        // Optionally, show a Toast message to confirm logout
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()

        // Navigate to the login screen
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // Close the MainActivity
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddCategoryActivity.ADD_NEW_CAT && resultCode == RESULT_OK) {
            viewPager.adapter = HomeViewPagerAdapter(supportFragmentManager)
            viewPager.currentItem = 0
        }
    }

    fun onExpenseAdded() {
        // Refresh the ViewPager to update the data
        viewPager.adapter = HomeViewPagerAdapter(supportFragmentManager)
        viewPager.currentItem = 0

        // Show a toast message to confirm the expense has been added
        Toast.makeText(this, "Expense added successfully!", Toast.LENGTH_SHORT).show()
    }

    override fun onTabSelected(tab: ActionBar.Tab, ft: FragmentTransaction) {
        viewPager.currentItem = tab.position
    }

    override fun onTabUnselected(tab: ActionBar.Tab, ft: FragmentTransaction) {}

    override fun onTabReselected(tab: ActionBar.Tab, ft: FragmentTransaction) {}

    private fun configureDrawer() {
        drawerLayout = findViewById(R.id.drawer)

        actionBarDrawerToggle = object : ActionBarDrawerToggle(
            this, drawerLayout, R.string.app_name, R.string.action_settings
        ) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                drawerView.bringToFront()
            }
        }
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        drawerLayout.setDrawerShadow(R.mipmap.drawer_shadow, GravityCompat.START)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val drawerList = findViewById<ListView>(R.id.drawer_list)
        drawerList.adapter = DrawerListViewAdapter(this)

        onDrawerItemSelected()
    }

    private fun onDrawerItemSelected() {
        val drawerList = findViewById<ListView>(R.id.drawer_list)
        val navigationDrawerPresenter = NavigationDrawerPresenter(this)
        drawerList.setOnItemClickListener { parent, view, position, id ->
            val drawerItems = resources.getStringArray(R.array.drawer_items)
            drawerList.setItemChecked(position, true)
            supportActionBar?.title = drawerItems[position]
            drawerLayout.closeDrawer(GravityCompat.START)
            drawerList.bringToFront()
            navigationDrawerPresenter.onItemSelected(drawerItems[position])
            val mainFrame = findViewById<FrameLayout>(R.id.main_frame)
            mainFrame.bringToFront()
        }
    }

    private fun configureActionBar() {
        supportActionBar?.navigationMode = ActionBar.NAVIGATION_MODE_TABS

        viewPager = findViewById(R.id.view_pager)
        homeViewPagerAdapter = HomeViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = homeViewPagerAdapter

        addTabs()

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i2: Int) {}

            override fun onPageSelected(i: Int) {
                supportActionBar?.setSelectedNavigationItem(i)
            }

            override fun onPageScrollStateChanged(i: Int) {}
        })
    }

    private fun addTabs() {
        val addNewExpenseTab = supportActionBar?.newTab()
        addNewExpenseTab?.setTabListener(this)
        if (addNewExpenseTab != null) {
            addNewExpenseTab.text = "Add New"
        }
        supportActionBar?.addTab(addNewExpenseTab)

        val todayTab = supportActionBar?.newTab()
        todayTab?.setTabListener(this)
        if (todayTab != null) {
            todayTab.text = "Today"
        }
        supportActionBar?.addTab(todayTab)
    }
}
