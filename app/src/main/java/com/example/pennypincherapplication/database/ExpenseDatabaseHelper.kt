package com.example.pennypincherapplication.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.pennypincherapplication.model.Expense
import com.example.pennypincherapplication.model.ExpenseType
import com.example.pennypincherapplication.table.ExpenseTable
import com.example.pennypincherapplication.table.ExpenseTypeTable
import com.example.pennypincherapplication.utils.DateUtil
import com.example.pennypincherapplication.utils.DateUtil.getCurrentDate
import com.example.pennypincherapplication.utils.DateUtil.getCurrentWeeksDates

class ExpenseDatabaseHelper(context: Context) : SQLiteOpenHelper(context, EXPENSE_DB, null, 1) {

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(ExpenseTable.CREATE_TABLE_QUERY)
        sqLiteDatabase.execSQL(ExpenseTypeTable.CREATE_TABLE_QUERY)
        seedExpenseTypes(sqLiteDatabase)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // No upgrade logic yet
    }

    fun getExpenseTypes(): List<String> {
        val expenseTypes = ArrayList<String>()
        val database = writableDatabase
        val cursor = database.rawQuery(ExpenseTypeTable.SELECT_ALL, null)

        if (isCursorPopulated(cursor)) {
            do {
                val type = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTypeTable.TYPE))
                expenseTypes.add(type)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return expenseTypes
    }

    fun deleteAll() {
        val database = writableDatabase
        database.delete(ExpenseTypeTable.TABLE_NAME, "", arrayOf())
        database.delete(ExpenseTable.TABLE_NAME, "", arrayOf())
        database.close()
    }

    fun addExpense(expense: Expense) {
        val database = writableDatabase
        val values = ContentValues().apply {
            put(ExpenseTable.AMOUNT, expense.amount)
            put(ExpenseTable.TYPE, expense.type)
            put(ExpenseTable.DATE, expense.date)
        }
        database.insert(ExpenseTable.TABLE_NAME, null, values)
    }

    fun getExpenses(): List<Expense> {
        val database = writableDatabase
        val cursor = database.rawQuery(ExpenseTable.SELECT_ALL, null)
        return buildExpenses(cursor)
    }

    fun getTodaysExpenses(): List<Expense> {
        val database = writableDatabase
        val cursor = database.rawQuery(ExpenseTable.getExpensesForDate(getCurrentDate()), null)
        return buildExpenses(cursor)
    }

    fun getCurrentWeeksExpenses(): List<Expense> {
        val database = writableDatabase
        val cursor = database.rawQuery(ExpenseTable.getConsolidatedExpensesForDates(getCurrentWeeksDates()), null)
        return buildExpenses(cursor)
    }

    fun getExpensesGroupByCategory(): List<Expense> {
        val database = writableDatabase
        val cursor = database.rawQuery(ExpenseTable.SELECT_ALL_GROUP_BY_CATEGORY, null)
        return buildExpenses(cursor)
    }

    fun getExpensesForCurrentMonthGroupByCategory(): List<Expense> {
        val database = writableDatabase
        val cursor = database.rawQuery(ExpenseTable.getExpenseForCurrentMonth(DateUtil.currentMonthOfYear()), null)
        return buildExpenses(cursor)
    }

    fun addExpenseType(type: ExpenseType) {
        val database = writableDatabase
        val values = ContentValues().apply {
            put(ExpenseTypeTable.TYPE, type.type)
        }
        database.insert(ExpenseTypeTable.TABLE_NAME, null, values)
    }

    fun truncate(tableName: String) {
        val database = writableDatabase
        database.execSQL("DELETE FROM $tableName")
    }

    private fun buildExpenses(cursor: Cursor?): List<Expense> {
        val expenses = mutableListOf<Expense>()
        if (isCursorPopulated(cursor)) {
            do {
                val type = cursor!!.getString(cursor.getColumnIndexOrThrow(ExpenseTable.TYPE))
                val amount = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTable.AMOUNT))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTable.DATE))
                val id = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTable._ID))

                val expense = if (id == null) {
                    Expense(amount.toLong(), type, date)
                } else {
                    Expense(id.toInt(), amount.toLong(), type, date)
                }
                expenses.add(expense)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return expenses
    }

    private fun isCursorPopulated(cursor: Cursor?): Boolean {
        return cursor != null && cursor.moveToFirst()
    }

    private fun seedExpenseTypes(sqLiteDatabase: SQLiteDatabase) {
        val expenseTypes = ExpenseTypeTable.seedData()
        for (expenseType in expenseTypes) {
            val contentValues = ContentValues().apply {
                put(ExpenseTypeTable.TYPE, expenseType.type)
            }
            sqLiteDatabase.insert(ExpenseTypeTable.TABLE_NAME, null, contentValues)
        }
    }

    companion object {
        const val EXPENSE_DB = "expense"
    }
}
