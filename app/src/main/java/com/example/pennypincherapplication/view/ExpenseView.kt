package com.example.pennypincherapplication.view

interface ExpenseView {
    fun getAmount(): String
    fun getType(): String
    fun renderExpenseTypes(expenseTypes: List<String>)
    fun displayError()
}
