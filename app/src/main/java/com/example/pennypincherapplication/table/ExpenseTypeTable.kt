package com.example.pennypincherapplication.table

import android.provider.BaseColumns
import com.example.pennypincherapplication.model.ExpenseType

class ExpenseTypeTable : BaseColumns {
    companion object {
        const val TABLE_NAME = "expense_types"
        const val TYPE = "type"

        const val CREATE_TABLE_QUERY = "CREATE TABLE $TABLE_NAME (" +
                "${_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$TYPE TEXT)"

        const val SELECT_ALL = "SELECT * FROM $TABLE_NAME"

        fun seedData(): List<ExpenseType> {
            return listOf(
                ExpenseType("Food"),
                ExpenseType("Travel"),
                ExpenseType("Health"),
                ExpenseType("Shopping"),
                ExpenseType("Rent"),
                ExpenseType("Money-Transfer"),
                ExpenseType("Other")
            )
        }
    }
}
