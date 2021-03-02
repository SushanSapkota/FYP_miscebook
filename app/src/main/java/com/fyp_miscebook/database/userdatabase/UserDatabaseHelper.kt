package com.fyp_miscebook.database.userdatabase

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.fyp_miscebook.activities.DashboardActivity
import com.fyp_miscebook.activities.LoginActivity
import com.fyp_miscebook.activities.RegisterActivity

const val DATABASE_NAME = "UserDatabase"
const val TABLE_NAME = "Users"
const val COL_FIRSTNAME = "Firstname"
const val COL_MIDDLENAME = "Middlename"
const val COL_LASTNAME = "Lastname"
const val COL_EMAIL = "Email"
const val COL_USERNAME = "Username"
const val COL_PASSWORD = "Password"
const val COL_ADDRESS = "Address"
const val COL_MOBILE = "Mobile"
const val COL_ID = "ID"

class UserDataBaseHandler(var context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(Userdb: SQLiteDatabase?) {

        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_FIRSTNAME + " VARCHAR(256)," +
                COL_MIDDLENAME + " VARCHAR(256)," +
                COL_LASTNAME + " VARCHAR(256)," +
                COL_EMAIL + " VARCHAR(256) UNIQUE," +
                COL_USERNAME + " VARCHAR(256) UNIQUE," +
                COL_PASSWORD + " VARCHAR(256)," +
                COL_ADDRESS + " VARCHAR(256)," +
                COL_MOBILE + " VARCHAR(256) UNIQUE)"

        Userdb!!.execSQL(createTable)
    }

    override fun onUpgrade(Userdb: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertData(user: UserEntity, registerActivity: RegisterActivity) {

        val Userdb = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_FIRSTNAME, user.firstname)
        cv.put(COL_MIDDLENAME, user.middlename)
        cv.put(COL_LASTNAME, user.lastname)
        cv.put(COL_EMAIL, user.email)
        cv.put(COL_USERNAME, user.username)
        cv.put(COL_PASSWORD, user.password)
        cv.put(COL_ADDRESS, user.address)
        cv.put(COL_MOBILE, user.mobile)
        val result = Userdb.insert(TABLE_NAME, null, cv)
        if (result == -1.toLong()) {
            Toast.makeText(registerActivity, "Failed to register User", Toast.LENGTH_SHORT).show()
        } else {
            registerActivity.finish()
            Toast.makeText(registerActivity, "User Registered Successfully", Toast.LENGTH_SHORT)
                .show()
        }
    }

    @SuppressLint("Recycle")
    fun login(Username: String, Password: String, loginActivity: LoginActivity) {
        val Userdb = this.writableDatabase
        val query =
            "Select * from $TABLE_NAME where $COL_USERNAME = '$Username' and $COL_PASSWORD = '$Password'"
        val result = Userdb.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                loginActivity.startActivity(
                    Intent(context, DashboardActivity::class.java)
                )
            } while (result.moveToNext())
        } else {
            Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show()
        }

        result.close()
        Userdb.close()
    }

    @SuppressLint("SetTextI18n")
    fun getall(Username: String): MutableList<UserEntity> {
        val list: MutableList<UserEntity> = ArrayList()
        val Userdb = this.writableDatabase
        val query =
            "Select * from $TABLE_NAME where $COL_USERNAME = '$Username'"
        val result = Userdb.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val user = UserEntity()
                user.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                user.firstname = result.getString(result.getColumnIndex(COL_FIRSTNAME))
                user.middlename = result.getString(result.getColumnIndex(COL_MIDDLENAME))
                user.lastname = result.getString(result.getColumnIndex(COL_LASTNAME))
                user.email = result.getString(result.getColumnIndex(COL_EMAIL))
                user.username = result.getString(result.getColumnIndex(COL_USERNAME))
                user.address = result.getString(result.getColumnIndex(COL_ADDRESS))
                user.mobile = result.getString(result.getColumnIndex(COL_MOBILE))
                list.add(user)
            } while (result.moveToNext())
        }

        result.close()
        Userdb.close()
        return list
    }
}