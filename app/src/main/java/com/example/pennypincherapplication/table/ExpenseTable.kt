package com.example.pennypincherapplication.table

object ExpenseTable {
    const val TABLE_NAME = "expenses"
    const val _ID = "_id"
    const val AMOUNT = "amount"
    const val TYPE = "type"
    const val DATE = "date"

    const val CREATE_TABLE_QUERY = """
        CREATE TABLE $TABLE_NAME (
            $_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $AMOUNT REAL,
            $TYPE TEXT,
            $DATE TEXT
        )
    """

    // Method to get expenses for a specific date
    fun getExpensesForDate(date: String): String {
        return "SELECT * FROM $TABLE_NAME WHERE $DATE = '$date'"
    }

    // Method to get consolidated expenses for a range of dates (weekly)
    fun getConsolidatedExpensesForDates(dates: List<String>): String {
        val formattedDates = dates.joinToString(separator = ",") { "'$it'" }
        return "SELECT * FROM $TABLE_NAME WHERE $DATE IN ($formattedDates)"
    }

    // Method to get expenses for the current month
    fun getExpenseForCurrentMonth(month: String): String {
        return "SELECT * FROM $TABLE_NAME WHERE strftime('%m', $DATE) = '$month'"
    }

    const val SELECT_ALL = "SELECT * FROM $TABLE_NAME"
    const val SELECT_ALL_GROUP_BY_CATEGORY = "SELECT $TYPE, SUM($AMOUNT) AS total FROM $TABLE_NAME GROUP BY $TYPE"
}
