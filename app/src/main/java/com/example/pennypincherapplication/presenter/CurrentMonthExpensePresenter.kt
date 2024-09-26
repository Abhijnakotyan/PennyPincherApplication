package com.example.pennypincherapplication.presenter

import com.echo.holographlibrary.Bar
import com.example.pennypincherapplication.database.ExpenseDatabaseHelper
import com.example.pennypincherapplication.model.Expense
import com.example.pennypincherapplication.utils.ExpenseCollection
import com.example.pennypincherapplication.view.CurrentMonthExpenseView

class CurrentMonthExpensePresenter(
    private val view: CurrentMonthExpenseView,
    database: ExpenseDatabaseHelper
) {
    private val expenseCollection: ExpenseCollection = ExpenseCollection(database.getExpensesForCurrentMonthGroupByCategory())

    fun plotGraph() {
        val points = mutableListOf<Bar>()

        for (expense in expenseCollection.withoutMoneyTransfer()) {
            val bar = Bar().apply {
                color = view.getGraphColor()
                name = expense.type
                value = expense.amount
            }
            points.add(bar)
        }

        view.displayGraph(points)
    }

    fun showTotalExpense() {
        view.displayTotalExpense(expenseCollection.totalExpense)
    }
}
