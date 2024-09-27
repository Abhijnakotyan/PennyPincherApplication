package com.example.pennypincherapplication.presenter

import android.os.Build
import androidx.annotation.RequiresApi
import com.echo.holographlibrary.Bar
import com.example.pennypincherapplication.database.ExpenseDatabaseHelper
import com.example.pennypincherapplication.model.Expense
import com.example.pennypincherapplication.util.ExpenseCollection
import com.example.pennypincherapplication.view.CurrentMonthExpenseView

class CurrentMonthExpensePresenter(
    private val view: CurrentMonthExpenseView,
    database: ExpenseDatabaseHelper
) {
    @RequiresApi(Build.VERSION_CODES.O)
    private val expenseCollection: ExpenseCollection = ExpenseCollection(database.getExpensesForCurrentMonthGroupByCategory())

    @RequiresApi(Build.VERSION_CODES.O)
    fun plotGraph() {
        val points = mutableListOf<Bar>()

        for (expense in expenseCollection.withoutMoneyTransfer()) {
            val bar = Bar().apply {
                color = view.getGraphColor()
                name = expense.type
                value = expense.amount.toFloat()
            }
            points.add(bar)
        }

        view.displayGraph(points)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showTotalExpense() {
        view.displayTotalExpense(expenseCollection.totalExpense)
    }
}
