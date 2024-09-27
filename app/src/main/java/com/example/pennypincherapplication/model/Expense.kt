package com.example.pennypincherapplication.model

data class Expense(
    val id: Long,
    val amount: Long,   // Ensure `amount` is of type Long (or Double)
    val type: String,
    val date: String
)
