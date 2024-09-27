package com.example.pennypincherapplication.view

import com.echo.holographlibrary.Bar

interface CurrentMonthExpenseView {
    fun displayGraph(points: List<Bar>)

    fun displayTotalExpense(totalExpense: Long)

    fun getGraphColor(): Int
}
