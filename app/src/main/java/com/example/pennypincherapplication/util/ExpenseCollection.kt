package com.example.pennypincherapplication.util

import com.example.pennypincherapplication.model.Expense

class ExpenseCollection(private val expenses: List<Expense>) {

    // Calculate the total expense by summing the amounts
    val totalExpense: Long
        get() = expenses.sumOf { it.amount.toLong() } // Convert String to Long

    // Group expenses by date
    fun groupByDate(): Map<String, List<Expense>> {
        // Group the expenses by their date field
        return expenses.groupBy { it.date }
    }

    // Optionally, you can add a method to filter out specific expenses
    fun withoutMoneyTransfer(): List<Expense> {
        return expenses.filter { it.type != "Money Transfer" }
    }
}
