package com.example.parahabit.database

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.provider.BaseColumns
import android.text.TextUtils


class DataProvider: ContentProvider(){

    private val HABITS = 1
    private val HABITS_ID = 2

    private var dbHelper: DatabaseHandler? = null
    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    init{
        uriMatcher.addURI(AUTHORITY, Tables.Habit.TABLE_NAME, HABITS)
        uriMatcher.addURI(AUTHORITY, Tables.Habit.TABLE_NAME + "/#", HABITS_ID)
    }

    companion object{
        val AUTHORITY = "com.example.parahabit.database.DataProvider"
        val HABITS_URI: Uri = Uri.parse("content://"+ AUTHORITY +"/"+ Tables.Habit.TABLE_NAME)
    }

    override fun onCreate(): Boolean {
        dbHelper = context?.let { DatabaseHandler(it) }
        return false
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val uriType = uriMatcher.match(uri)
        val db = dbHelper!!.writableDatabase
        val rowsDeleted: Int
        when(uriType){
            HABITS->rowsDeleted = db.delete(Tables.Habit.TABLE_NAME, selection, selectionArgs)
            HABITS_ID->{
                val id = uri.lastPathSegment
                if(TextUtils.isEmpty(selection)){
                    rowsDeleted = db.delete(Tables.Habit.TABLE_NAME, "${BaseColumns._ID}=${id}",null)
                } else {
                    rowsDeleted = db.delete(Tables.Habit.TABLE_NAME, "${BaseColumns._ID}=${id} and ${selection}", selectionArgs)
                }
            }
            else -> throw IllegalArgumentException("Unknown URI: " + uri)
        }
        context?.contentResolver?.notifyChange(uri, null)
        return rowsDeleted
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val uriType = uriMatcher.match(uri)
        val db = dbHelper!!.writableDatabase
        val id: Long
        when(uriType){
            HABITS->id = db.insert(Tables.Habit.TABLE_NAME, null, values)
            else -> throw IllegalArgumentException("Unknown URI: "+ uri)
        }
        context?.contentResolver?.notifyChange(uri, null)
        // TODO: sprawdzić, o co tutaj chodzi i jaki sposób wygenerować
        return Uri.parse(Tables.Habit.TABLE_NAME+"/"+id)
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        val queryBuilder = SQLiteQueryBuilder()
        queryBuilder.tables = Tables.Habit.TABLE_NAME // TODO: sprawdzić jak postępować w przypadku wielu tabel
        val uriType = uriMatcher.match(uri)
        when(uriType){
            HABITS_ID-> queryBuilder.appendWhere("${BaseColumns._ID}=${uri.lastPathSegment}")
            HABITS -> {}
            else -> throw IllegalArgumentException("Unknown URI")
        }

        val cursor = queryBuilder.query(dbHelper?.readableDatabase, projection, selection, selectionArgs, null, null, sortOrder)
        cursor.setNotificationUri(context?.contentResolver, uri)
        return cursor
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        val uriType = uriMatcher.match(uri)
        val db: SQLiteDatabase = dbHelper!!.writableDatabase
        val rowsUpdated: Int
        when(uriType){
            HABITS->rowsUpdated = db.update(Tables.Habit.TABLE_NAME, values, selection, selectionArgs)
            HABITS_ID->{
                val id = uri.lastPathSegment
                if(TextUtils.isEmpty(selection)){
                    rowsUpdated = db.update(Tables.Habit.TABLE_NAME, values, "${BaseColumns._ID}=${id}", null)
                } else {
                    rowsUpdated = db.update(Tables.Habit.TABLE_NAME, values, "${BaseColumns._ID}=${id} and ${selection}", selectionArgs)
                }
            }
            else -> throw IllegalArgumentException("Unknown URI: " + uri)
        }
        context?.contentResolver?.notifyChange(uri, null)
        return rowsUpdated
    }
}