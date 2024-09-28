package com.example.pennypincherapplication.presenter

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.pennypincherapplication.database.ExpenseDatabaseHelper
import com.example.pennypincherapplication.model.Expense
import com.example.pennypincherapplication.view.TodaysExpenseView

class TodaysExpensePresenter(
    private val view: TodaysExpenseView,
    expenseDatabaseHelper: ExpenseDatabaseHelper
) {
    @RequiresApi(Build.VERSION_CODES.O)
    private val expenses: List<Expense> = expenseDatabaseHelper.getTodaysExpenses()

    @RequiresApi(Build.VERSION_CODES.O)
    fun renderTotalExpense() {
        val totalExpense = expenses.sumOf { it.amount }
        view.displayTotalExpense(totalExpense)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun renderTodaysExpenses() {
        view.displayTodaysExpenses(expenses)
    }
}
