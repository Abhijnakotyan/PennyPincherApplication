package com.example.pennypincherapplication.presenter

import com.example.pennypincherapplication.database.ExpenseDatabaseHelper
import com.example.pennypincherapplication.model.ExpenseType
import com.example.pennypincherapplication.view.AddCategoryView

class CategoryPresenter(
    private val view: AddCategoryView,
    private val database: ExpenseDatabaseHelper
) {

    fun addCategory(): Boolean {
        val newCategory = view.getCategory()
        return if (newCategory.isEmpty()) {
            view.displayError()
            false
        } else {
            database.addExpenseType(ExpenseType(newCategory))
            true
        }
    }
}
