package com.example.parahabit.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import android.provider.BaseColumns

private const val SQL_CREATE_PERIODS =
        "CREATE TABLE ${Tables.Period.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${Tables.Period.NAME} TEXT" +
                ")"

private const val SQL_CREATE_UNITS =
        "CREATE TABLE ${Tables.Units.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${Tables.Units.NAME} TEXT " +
                ")"

private const val SQL_CREATE_HABITS =
    "CREATE TABLE ${Tables.Habit.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${Tables.Habit.NAME} TEXT," +
            "${Tables.Habit.DESCRIPTION} TEXT," +
            "${Tables.Habit.PERIOD} INTEGER," +
            "${Tables.Habit.GOAL} INTEGER," +
            "${Tables.Habit.UNIT } INTEGER," +
            "FOREIGN KEY(${Tables.Habit.PERIOD}) REFERENCES ${Tables.Period.TABLE_NAME}(${BaseColumns._ID})," +
            "FOREIGN KEY(${Tables.Habit.UNIT}) REFERENCES ${Tables.Units.TABLE_NAME}(${BaseColumns._ID}) " +
            ")"

private const val SQL_CREATE_REPETITIONS =
        "CREATE TABLE ${Tables.Repetitions.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${Tables.Repetitions.HABIT} INTEGER," +
                "${Tables.Repetitions.DAY} INTEGER," +
                "FOREIGN KEY (${Tables.Repetitions.HABIT}) REFERENCES ${Tables.Habit.TABLE_NAME}(${BaseColumns._ID})" +
                ")"

private const val SQL_CREATE_EXECUTIONS =
        "CREATE TABLE ${Tables.HabitExecution.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${Tables.HabitExecution.HABIT} INTEGER," +
                "${Tables.HabitExecution.DATE} INTEGER," +
                "${Tables.HabitExecution.AMOUNT} INTEGER," +
                "FOREIGN KEY (${Tables.HabitExecution.HABIT}) REFERENCES ${Tables.HabitExecution.TABLE_NAME}(${BaseColumns._ID})" +
                ")"

private const val SQL_DELETE_HABITS = "DROP TABLE IF EXISTS ${Tables.Habit.TABLE_NAME}"


class DatabaseHandler(context : Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_PERIODS)
        db.execSQL(SQL_CREATE_UNITS)
        db.execSQL(SQL_CREATE_HABITS);
        db.execSQL(SQL_CREATE_REPETITIONS)
        db.execSQL(SQL_CREATE_EXECUTIONS)

        insertPeriods(db)
        insertUnits(db)
    }

    private fun insertPeriods(db:SQLiteDatabase){
        val table = Tables.Period.TABLE_NAME;
        insertEntry("DAY", table, db)
        insertEntry("WEEK", table, db)
        insertEntry("MONTH", table, db)
        insertEntry("TWO_WEEKS", table, db)
    }

    private fun insertEntry(name: String, table: String, db:SQLiteDatabase){
        val values = ContentValues().apply {
            put(Tables.Period.NAME, name)
        }
        val newRowId = db.insert(table, null, values)

    }

    private fun insertUnits(db:SQLiteDatabase){
        val table = Tables.Units.TABLE_NAME
        insertEntry("NONE", table, db)
        insertEntry("HOUR", table, db)
        insertEntry("MINUTE", table, db)
        insertEntry("LITRE", table, db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_HABITS)
        onCreate(db)
    }

    companion object{
        private const val DATABASE_NAME = "PARAhabit"
        private const val DATABASE_VERSION = 1
    }
}