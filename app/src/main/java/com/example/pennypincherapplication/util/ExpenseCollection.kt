package com.example.pennypincherapplication.util

import com.example.pennypincherapplication.model.Expense

class ExpenseCollection(private val expenses: List<Expense>) {

    fun getTotalExpense(): Long {
        return expenses.sumOf { it.amount }
    }

    fun groupByDate(): Map<String, List<Expense>> {
        return expenses.groupBy { it.date }
    }

    fun withoutMoneyTransfer(): List<Expense> {
        return expenses.filter { it.type != "Money-Transfer" }
    }
}
