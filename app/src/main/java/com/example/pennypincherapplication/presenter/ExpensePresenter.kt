package com.example.pennypincherapplication.presenter

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.pennypincherapplication.database.ExpenseDatabaseHelper
import com.example.pennypincherapplication.model.Expense
import com.example.pennypincherapplication.util.DateUtil.getCurrentDate
import com.example.pennypincherapplication.view.ExpenseView

class ExpensePresenter(
    private val database: ExpenseDatabaseHelper,
    private val view: ExpenseView
) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun addExpense(): Boolean {
        val amount = view.getAmount()

        if (amount.isEmpty()) {
            view.displayError()
            return false
        }

        // Create an Expense object with the correct types
        val expense = Expense(
            id = 0L,               // Use 0L for the auto-generated id
            amount = amount.toLong(),  // Convert amount to Long
            type = view.getType(),     // Get the expense type from the view
            date = getCurrentDate()     // Get the current date
        )

        // Add the expense to the database
        database.addExpense(expense)
        return true
    }

    fun setExpenseTypes() {
        // Render expense types in the view
        view.renderExpenseTypes(database.getExpenseTypes())
    }
}
