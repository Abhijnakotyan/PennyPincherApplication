package com.example.pennypincherapplication.presenter

import com.example.pennypincherapplication.database.ExpenseDatabaseHelper
import com.example.pennypincherapplication.model.Expense
import com.example.pennypincherapplication.view.TodaysExpenseView

class TodaysExpensePresenter(
    private val view: TodaysExpenseView,
    expenseDatabaseHelper: ExpenseDatabaseHelper
) {
    private val expenses: List<Expense> = expenseDatabaseHelper.getTodaysExpenses()

    fun renderTotalExpense() {
        val totalExpense = expenses.sumOf { it.amount }
        view.displayTotalExpense(totalExpense)
    }

    fun renderTodaysExpenses() {
        view.displayTodaysExpenses(expenses)
    }
}
