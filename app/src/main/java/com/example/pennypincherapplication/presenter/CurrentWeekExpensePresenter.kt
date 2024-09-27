package com.example.pennypincherapplication.presenter



import android.os.Build
import androidx.annotation.RequiresApi
import com.example.pennypincherapplication.database.ExpenseDatabaseHelper
import com.example.pennypincherapplication.util.ExpenseCollection
import com.example.pennypincherapplication.view.CurrentWeekExpenseView

class CurrentWeekExpensePresenter(
    private val database: ExpenseDatabaseHelper,
    private val view: CurrentWeekExpenseView
) {
    @RequiresApi(Build.VERSION_CODES.O)
    private val expenseCollection: ExpenseCollection = ExpenseCollection(database.getCurrentWeeksExpenses())

    @RequiresApi(Build.VERSION_CODES.O)
    fun renderTotalExpenses() {
        view.displayTotalExpenses(expenseCollection.totalExpense)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun renderCurrentWeeksExpenses() {
        view.displayCurrentWeeksExpenses(expenseCollection.groupByDate())
    }
}
