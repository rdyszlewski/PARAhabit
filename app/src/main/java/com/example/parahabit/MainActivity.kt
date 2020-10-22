package com.example.parahabit

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import com.example.parahabit.database.Tables
import com.example.parahabit.database.DatabaseHandler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHelper = DatabaseHandler(baseContext);
        val db = dbHelper.writableDatabase;
//       insertData(db, "Siema", "opis")
        readData(db)
    }

    private fun insertData(db:SQLiteDatabase,name: String, description: String){
        val values = ContentValues().apply {
            put(Tables.Habit.NAME, name)
            put(Tables.Habit.DESCRIPTION, description)
        }
        val newRowId = db.insert(Tables.Habit.TABLE_NAME, null, values)
        println(newRowId)
    }

    private fun readData(db:SQLiteDatabase){
        val projection = arrayOf(BaseColumns._ID, Tables.Habit.NAME, Tables.Habit.DESCRIPTION)
        val selection = "${Tables.Habit.NAME} =?"
        val selectionArgs = arrayOf("Siema")
        val sortOrder = "${Tables.Habit.NAME} DESC"

        val cursor = db.query(
                Tables.Habit.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        )

        val itemIds = mutableListOf<Long>()
        with(cursor){
            while (moveToNext()){
                val itemID = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                itemIds.add(itemID)
            }
        }
    }

    private fun update(db: SQLiteDatabase){

    }
}