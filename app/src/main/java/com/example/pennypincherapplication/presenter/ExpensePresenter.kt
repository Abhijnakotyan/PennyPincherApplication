package com.example.pennypincherapplication.presenter

import com.example.pennypincherapplication.database.ExpenseDatabaseHelper
import com.example.pennypincherapplication.model.Expense
import com.example.pennypincherapplication.utils.DateUtil.getCurrentDate
import com.example.pennypincherapplication.view.ExpenseView

class ExpensePresenter(
    private val database: ExpenseDatabaseHelper,
    private val view: ExpenseView
) {

    fun addExpense(): Boolean {
        val amount = view.getAmount()

        if (amount.isEmpty()) {
            view.displayError()
            return false
        }

        val expense = Expense(amount.toLong(), view.getType(), getCurrentDate())
        database.addExpense(expense)
        return true
    }

    fun setExpenseTypes() {
        view.renderExpenseTypes(database.getExpenseTypes())
    }
}
