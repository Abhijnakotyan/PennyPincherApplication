package com.example.pennypincherapplication.model

data class Expense(
    val id: Int? = null,
    val amount: Long,
    val type: String,
    val date: String
)
