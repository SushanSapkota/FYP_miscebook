package com.fyp_miscebook.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.fyp_miscebook.activities.FutsalActivity

const val DATABASE_NAME = "FutsalDatabase"
const val TABLE_NAME = "Futsal"

const val COL_Name = "Name"
const val COL_ADDRESS = "Address"
const val COL_Email = "Email"
const val COL_StartTime = "StartTime"
const val COL_EndTime = "EndTime"
const val COL_BookDate = "BookDate"
const val COL_NumberOfPlayers = "NumberOfPlayers"
const val COL_Image = "Image"
const val COL_ID = "ID"

class FutsalDataBaseHandler(var context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(Futsaldb: SQLiteDatabase?) {

        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_Name + " VARCHAR(256)," +
                COL_ADDRESS + " VARCHAR(256)," +
                COL_Email + " VARCHAR(256)," +
                COL_StartTime + " VARCHAR(256)," +
                COL_EndTime + " VARCHAR(256)," +
                COL_BookDate + " VARCHAR(256)," +
                COL_NumberOfPlayers + " VARCHAR(256)," +
                COL_Image + " VARCHAR(256))"

        Futsaldb!!.execSQL(createTable)
    }

    fun insertData(futsal: FutsalEntity, futsalActivity: FutsalActivity) {

        val Futsaldb = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_Name, futsal.name)
        cv.put(COL_ADDRESS, futsal.address)
        cv.put(COL_Email, futsal.email)
        cv.put(COL_StartTime, futsal.start)
        cv.put(COL_EndTime, futsal.end)
        cv.put(COL_BookDate, futsal.bookwhen)
        cv.put(COL_NumberOfPlayers, futsal.no_ofplayers)
        cv.put(COL_Image, futsal.image)
        val result = Futsaldb.insert(TABLE_NAME, null, cv)
        if (result == -1.toLong()) {
            Toast.makeText(futsalActivity, "Failed to Book futsal", Toast.LENGTH_SHORT).show()
        } else {
            futsalActivity.finish()
            Toast.makeText(futsalActivity, "Futsal Booked Successfully", Toast.LENGTH_SHORT)
                .show()
        }
    }

    @SuppressLint("SetTextI18n")
    fun getall(): MutableList<FutsalEntity> {
        val list: MutableList<FutsalEntity> = ArrayList()
        val Futsaldb = this.writableDatabase
        val query =
            "Select * from $TABLE_NAME"
        val result = Futsaldb.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val futsal = FutsalEntity()
                futsal.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                futsal.name = result.getString(result.getColumnIndex(COL_Name))
                futsal.address = result.getString(result.getColumnIndex(COL_ADDRESS))
                futsal.email = result.getString(result.getColumnIndex(COL_Email))
                futsal.start = result.getString(result.getColumnIndex(COL_StartTime))
                futsal.end = result.getString(result.getColumnIndex(COL_EndTime))
                futsal.bookwhen = result.getString(result.getColumnIndex(COL_BookDate))
                futsal.no_ofplayers = result.getString(result.getColumnIndex(COL_NumberOfPlayers))
                futsal.image = result.getString(result.getColumnIndex(COL_Image))
                list.add(futsal)
            } while (result.moveToNext())
        }

        result.close()
        Futsaldb.close()
        return list
    }

    override fun onUpgrade(Futsaldb: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}