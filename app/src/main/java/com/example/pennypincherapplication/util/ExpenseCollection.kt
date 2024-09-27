package com.example.pennypincherapplication.util

import com.example.pennypincherapplication.model.Expense
import java.math.BigDecimal

class ExpenseCollection(private val expenses: List<Expense>) {

    fun TotalExpense(): Long {
        // Convert BigDecimal to Long before summing
        return expenses.sumOf { it.amount.toLong() }
    }

    fun groupByDate(): Map<String, List<Expense>> {
        return expenses.groupBy { it.date }
    }

    fun withoutMoneyTransfer(): List<Expense> {
        return expenses.filter { it.type != "Money-Transfer" }
    }
}
