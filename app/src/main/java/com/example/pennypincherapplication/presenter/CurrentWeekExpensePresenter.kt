package com.example.pennypincherapplication.presenter


import com.example
import com.example.pennypincherapplication.database.ExpenseDatabaseHelper
import com.example.pennypincherapplication.utils.ExpenseCollection
import com.example.pennypincherapplication.view.CurrentWeekExpenseView

class CurrentWeekExpensePresenter(
    private val database: ExpenseDatabaseHelper,
    private val view: CurrentWeekExpenseView
) {
    private val expenseCollection: ExpenseCollection = ExpenseCollection(database.getCurrentWeeksExpenses())

    fun renderTotalExpenses() {
        view.displayTotalExpenses(expenseCollection.totalExpense)
    }

    fun renderCurrentWeeksExpenses() {
        view.displayCurrentWeeksExpenses(expenseCollection.groupByDate())
    }
}
