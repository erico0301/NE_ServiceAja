package com.example.serviceaja.classes

import DBClass.DBUser
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        val DB_NAME = "ServiceAja.db"
        val DB_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_USER_TABLE = """
            CREATE TABLE ${DBUser.tableUser.TABLE_USER}
            (
                ${DBUser.tableUser.COLUMN_NO_TELP} TEXT PRIMARY KEY,
                ${DBUser.tableUser.COLUMN_NAME} TEXT,
                ${DBUser.tableUser.COLUMN_EMAIL} TEXT,
                ${DBUser.tableUser.COLUMN_PASSWORD} TEXT
            )
        """.trimIndent()

        db?.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${DBUser.tableUser.TABLE_USER}")
        onCreate(db)
    }

    fun getAllUsers(): ArrayList<User> {
        val users = arrayListOf<User>()
        val dbReader = readableDatabase

        val projection = arrayOf(DBUser.tableUser.COLUMN_NO_TELP, DBUser.tableUser.COLUMN_NAME, DBUser.tableUser.COLUMN_EMAIL, DBUser.tableUser.COLUMN_PASSWORD)
        val cursor = dbReader.query(
                DBUser.tableUser.TABLE_USER,
                projection,
                null,
                null,
                null,
                null,
                null
        )
        with(cursor) {
            while (moveToNext()) {
                users.add(User(
                        getString(getColumnIndex(DBClass.DBUser.tableUser.COLUMN_NAME)),
                        getString(getColumnIndex(DBClass.DBUser.tableUser.COLUMN_EMAIL)),
                        getString(getColumnIndex(DBClass.DBUser.tableUser.COLUMN_NO_TELP)),
                        getString(getColumnIndex(DBClass.DBUser.tableUser.COLUMN_PASSWORD))
                ))
            }
        }

        return users
    }

    fun addUser(user: User) {
        val dbWriter = writableDatabase
        val contentValues = ContentValues().apply {
            put(DBUser.tableUser.COLUMN_NO_TELP, user.noTelp)
            put(DBUser.tableUser.COLUMN_NAME, user.nama)
            put(DBUser.tableUser.COLUMN_EMAIL, user.email)
            put(DBUser.tableUser.COLUMN_PASSWORD, user.password)
        }
        dbWriter.insert(DBUser.tableUser.TABLE_USER, null, contentValues)
        dbWriter.close()
    }
}