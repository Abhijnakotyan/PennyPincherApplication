package com.example.miniproject.activity


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pennypincherapplication.MainActivity
import com.example.pennypincherapplication.R
import com.example.pennypincherapplication.database.ExpenseDatabaseHelper
import com.example.pennypincherapplication.presenter.ExpensePresenter
import com.example.pennypincherapplication.view.ExpenseView

class ExpenseFragment : Fragment(), ExpenseView, View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.example.pennypincherapplication.R.layout.new_expense, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val expenseDatabaseHelper = ExpenseDatabaseHelper(activity)
        val expensePresenter = ExpensePresenter(expenseDatabaseHelper, this)
        expensePresenter.setExpenseTypes()
        expenseDatabaseHelper.close()

        val addExpenseButton = activity?.findViewById<Button>(com.example.pennypincherapplication.R.id.add_expense)
        addExpenseButton?.setOnClickListener(this)
    }

    override fun getAmount(): String {
        val view = activity?.findViewById<TextView>(com.example.pennypincherapplication.R.id.amount)
        return view?.text.toString()
    }

    override fun getType(): String {
        val spinner = activity?.findViewById<Spinner>(com.example.pennypincherapplication.R.id.expense_type)
        return spinner?.selectedItem as String
    }

    override fun renderExpenseTypes(expenseTypes: List<String>) {
        val spinner = activity?.findViewById<Spinner>(com.example.pennypincherapplication.R.id.expense_type)
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_dropdown_item, expenseTypes)
        spinner?.adapter = adapter
    }

    override fun displayError() {
        val view = activity?.findViewById<TextView>(com.example.pennypincherapplication.R.id.amount)
        view?.error = getString(com.example.pennypincherapplication.R.string.amount_empty_error)
    }

    override fun onClick(view: View) {
        val expenseDatabaseHelper = ExpenseDatabaseHelper(activity)
        val expensePresenter = ExpensePresenter(expenseDatabaseHelper, this)
        if (expensePresenter.addExpense()) {
            val activity = activity as? MainActivity
            Toast.makeText(activity, com.example.pennypincherapplication.R.string.expense_add_successfully, Toast.LENGTH_LONG).show()
            activity?.onExpenseAdded()
        }
        expenseDatabaseHelper.close()
    }
}
