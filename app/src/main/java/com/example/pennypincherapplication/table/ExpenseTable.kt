package com.example.pennypincherapplication.table
import android.provider.BaseColumns

class ExpenseTable : BaseColumns {
    companion object {
        const val TABLE_NAME = "expenses"
        const val AMOUNT = "amount"
        const val TYPE = "type"
        const val DATE = "date"
        const val CREATE_TABLE_QUERY = "CREATE TABLE $TABLE_NAME (" +
                "${_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$AMOUNT INTEGER, " +
                "$TYPE TEXT, " +
                "$DATE TEXT)"

        const val SELECT_ALL = "SELECT * FROM $TABLE_NAME ORDER BY $_ID DESC"

        const val SELECT_ALL_GROUP_BY_CATEGORY = "SELECT $_ID, date, type, SUM(amount) AS amount FROM $TABLE_NAME GROUP BY type"

        fun getExpensesForDate(date: String): String {
            return "SELECT * FROM $TABLE_NAME WHERE date LIKE '$date%' ORDER BY $_ID DESC"
        }

        fun getConsolidatedExpensesForDates(dates: ArrayList<String>): String {
            val dateLike = dates.joinToString(" OR ") { "date LIKE '$it%'" }
            return "SELECT $_ID, date, type, SUM(amount) AS amount FROM $TABLE_NAME WHERE $dateLike GROUP BY date, type"
        }

        fun getExpenseForCurrentMonth(currentMonthOfYear: String): String {
            val currentMonthsExpenses = "(SELECT $_ID, date, type, amount FROM $TABLE_NAME WHERE date LIKE '%-$currentMonthOfYear')"
            return "SELECT $_ID, date, type, SUM(amount) AS amount FROM $currentMonthsExpenses GROUP BY type"
        }
    }
}
