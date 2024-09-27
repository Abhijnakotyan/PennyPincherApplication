package com.example.pennypincherapplication.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.miniproject.adapter.TodaysExpenseListViewAdapter
import com.example.pennypincherapplication.R
import com.example.pennypincherapplication.database.ExpenseDatabaseHelper
import com.example.pennypincherapplication.model.Expense
import com.example.pennypincherapplication.presenter.CurrentMonthExpensePresenter
import com.example.pennypincherapplication.presenter.TodaysExpensePresenter
import com.example.pennypincherapplication.view.CurrentMonthExpenseView
import com.example.pennypincherapplication.view.TodaysExpenseView


class TodaysExpenseFragment : Fragment(), TodaysExpenseView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.todays_expenses, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val expenseDatabaseHelper = activity?.let { ExpenseDatabaseHelper(it) }
        val todaysExpensePresenter = expenseDatabaseHelper?.let { TodaysExpensePresenter(this, it) }

        todaysExpensePresenter?.renderTodaysExpenses()
        todaysExpensePresenter?.renderTotalExpense()
        expenseDatabaseHelper?.close()
    }

    @SuppressLint("SetTextI18n")
    override fun displayTotalExpense(totalExpense: Long) {
        val totalExpenseTextBox = activity?.findViewById<TextView>(R.id.total_expense)
        totalExpenseTextBox?.text = getString(R.string.total_expense) + " " + getString(R.string.rupee_sym) + totalExpense.toString()
    }

    override fun displayTodaysExpenses(expenses: List<Expense>) {
        val listView = activity?.findViewById<ListView>(R.id.todays_expenses_list)
        listView?.adapter = TodaysExpenseListViewAdapter(expenses, requireActivity(), android.R.layout.simple_list_item_1)
    }
}
