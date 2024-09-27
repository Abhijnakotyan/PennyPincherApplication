package com.example.pennypincherapplication.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.pennypincherapplication.R
import com.example.pennypincherapplication.database.ExpenseDatabaseHelper
import com.example.pennypincherapplication.presenter.CategoryPresenter
import com.example.pennypincherapplication.view.AddCategoryView

class AddCategoryActivity : FragmentActivity(), AddCategoryView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_category)
    }

    fun addCategory(view: View) {
        val expenseDatabaseHelper = ExpenseDatabaseHelper(this)
        val categoryPresenter = CategoryPresenter(this, expenseDatabaseHelper)

        if (categoryPresenter.addCategory()) {
            Toast.makeText(this, getString(R.string.add_category_success), Toast.LENGTH_LONG).show()
        }

        expenseDatabaseHelper.close()
        finishActivity(ADD_NEW_CAT)
    }

    fun getCategory(): String {
        val categoryInput = findViewById<TextView>(R.id.category)
        return categoryInput.text.toString()
    }

    fun displayError() {
        val view = findViewById<TextView>(R.id.category)
        view.error = getString(R.string.category_empty_error)
    }

    companion object {
        const val ADD_NEW_CAT = 1
    }
}
