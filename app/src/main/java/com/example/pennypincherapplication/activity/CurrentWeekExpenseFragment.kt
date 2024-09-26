package com.example.pennypincherapplication.activity


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.pennypincherapplication.R
import com.example.pennypincherapplication.database.ExpenseDatabaseHelper
import com.example.pennypincherapplication.presenter.CurrentMonthExpensePresenter
import com.example.pennypincherapplication.view.CurrentMonthExpenseView
import com.example.pennypincherapplication.widget.Bar
import com.example.pennypincherapplication.widget.BarGraph


class CurrentWeekExpenseFragment : Fragment(), CurrentWeekExpenseView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.current_week_expenses, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val expenseDatabaseHelper = ExpenseDatabaseHelper(activity)
        val presenter = CurrentWeekExpensePresenter(expenseDatabaseHelper, this)
        presenter.renderTotalExpenses()
        presenter.renderCurrentWeeksExpenses()
        expenseDatabaseHelper.close()
    }

    override fun displayCurrentWeeksExpenses(expensesByDate: Map<String, List<Expense>>) {
        val listView = activity?.findViewById<ExpandableListView>(R.id.current_week_expenses_list)
        listView?.setAdapter(CurrentWeeksExpenseAdapter(requireActivity(), expensesByDate))
    }

    override fun displayTotalExpenses(totalExpense: Long) {
        val totalExpenseTextBox = activity?.findViewById<TextView>(R.id.current_week_expense)
        totalExpenseTextBox?.text = getString(R.string.total_expense) + " " + getString(R.string.rupee_sym) + totalExpense
    }
}
