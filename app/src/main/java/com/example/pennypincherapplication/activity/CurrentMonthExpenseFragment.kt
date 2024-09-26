package com.example.pennypincherapplication.activity

import android.annotation.SuppressLint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.pennypincherapplication.R
import com.example.pennypincherapplication.database.ExpenseDatabaseHelper
import com.example.pennypincherapplication.presenter.CurrentMonthExpensePresenter
import com.example.pennypincherapplication.view.CurrentMonthExpenseView
import com.example.pennypincherapplication.widget.Bar
import com.example.pennypincherapplication.widget.BarGraph

class CurrentMonthExpenseFragment : Fragment(), CurrentMonthExpenseView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.expense_graph, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val expenseDatabaseHelper = ExpenseDatabaseHelper(activity)
        val presenter = CurrentMonthExpensePresenter(this, expenseDatabaseHelper)

        presenter.plotGraph()
        presenter.showTotalExpense()
        expenseDatabaseHelper.close()
    }

    override fun displayGraph(points: List<Bar>) {
        val graph = activity?.findViewById<BarGraph>(R.id.graph)
        graph?.setBars(ArrayList(points))
    }

    @SuppressLint("SetTextI18n")
    override fun displayTotalExpense(totalExpense: Long) {
        val totalExpenseTextBox = activity?.findViewById<TextView>(R.id.current_months_total_expense)
        totalExpenseTextBox?.text = getString(R.string.total_expense) + " " + getString(R.string.rupee_sym) + totalExpense
    }

    override fun getGraphColor(): Int {
        return activity?.resources?.getColor(R.color.light_blue) ?: 0
    }
}
