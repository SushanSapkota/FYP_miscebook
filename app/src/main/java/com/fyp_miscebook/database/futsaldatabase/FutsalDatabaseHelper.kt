package com.fyp_miscebook.database.futsaldatabase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DATABASE_NAME = "FutsalDatabase"
const val TABLE_NAME = "Futsal"
const val COL_Name = "Name"
const val COL_ADDRESS = "Address"
const val COL_CONTACT = "Contact"
const val COL_ID = "ID"

class FutsalDataBaseHandler(var context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(Futsaldb: SQLiteDatabase?) {

        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_Name + " VARCHAR(256)," +
                COL_ADDRESS + " VARCHAR(256)," +
                COL_CONTACT + " VARCHAR(256))"

        Futsaldb!!.execSQL(createTable)
    }

    override fun onUpgrade(Futsaldb: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}