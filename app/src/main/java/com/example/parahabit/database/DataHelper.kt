package com.example.parahabit.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import android.provider.BaseColumns

private const val SQL_CREATE_PERIODS =
        "CREATE TABLE"

private const val SQL_CREATE_HABITS =
    "CREATE TABLE ${Tables.HabitTable.TABLE_NAME} + {" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${Tables.HabitTable.NAME} TEXT," +
            "${Tables.HabitTable.DESCRIPTION} TEXT" +
            "${Tables.HabitTable.PERIOD} INT " +
            "${Tables.HabitTable.GOAL} INT " +
            "${Tables.HabitTable.UNIT } INT" +
            "FOREIGN KEY(${Tables.HabitTable.PERIOD} REFERENCES ${Tables.Period.TABLE_NAME}(${BaseColumns._ID})" +
            "FOREIGN KEY(${Tables.HabitTable.UNIT} REFERENCES {${Tables.Units.TABLE_NAME}(${BaseColumns._ID} " +
            ")"

private const val SQL_DELETE_HABITS = "DROP TABLE IF EXISTS ${Tables.HabitTable.TABLE_NAME}"


class DataHelper(context : Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_HABITS);
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