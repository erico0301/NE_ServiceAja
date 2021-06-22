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

        val CREATE_TRANSAKSI_TABLE = """
            CREATE TABLE ${DBUser.tableTransaksi.TABLE_TRANSAKSI}
            (
                ${DBUser.tableTransaksi.COLUMN_USERID} TEXT PRIMARY KEY,
                ${DBUser.tableTransaksi.COLUMN_PAYMENT} TEXT,
                ${DBUser.tableTransaksi.COLUMN_CONFIRM} TEXT,
                ${DBUser.tableTransaksi.COLUMN_PACKING} TEXT
                ${DBUser.tableTransaksi.COLUMN_SEND} TEXT
                ${DBUser.tableTransaksi.COLUMN_RECEIVE} TEXT
            )
        """.trimIndent()
        db?.execSQL(CREATE_TRANSAKSI_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${DBUser.tableUser.TABLE_USER}")
        db?.execSQL("DROP TABLE IF EXISTS ${DBUser.tableTransaksi.TABLE_TRANSAKSI}")
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

    fun deleteData(email: String){
        val db = this.writableDatabase
        val selection = "${DBUser.tableUser.COLUMN_EMAIL} = ?"
        val selectionArgs = arrayOf(email)
        db.delete(DBUser.tableUser.TABLE_USER,selection,selectionArgs)
    }

    fun getAllTransaksi(): ArrayList<Transaksi> {
        val transaksi = arrayListOf<Transaksi>()
        val dbReader = readableDatabase

        val projection = arrayOf(DBUser.tableTransaksi.COLUMN_USERID, DBUser.tableTransaksi.COLUMN_PAYMENT, DBUser.tableTransaksi.COLUMN_CONFIRM, DBUser.tableTransaksi.COLUMN_PACKING, DBUser.tableTransaksi.COLUMN_SEND, DBUser.tableTransaksi.COLUMN_RECEIVE)
        val cursor = dbReader.query(
            DBUser.tableTransaksi.TABLE_TRANSAKSI,
            projection,
            null,
            null,
            null,
            null,
            null
        )
        with(cursor) {
            while (moveToNext()) {
                transaksi.add(
                    Transaksi(
                    getString(getColumnIndex(DBUser.tableTransaksi.COLUMN_USERID)),
                    getString(getColumnIndex(DBUser.tableTransaksi.COLUMN_PAYMENT)),
                    getString(getColumnIndex(DBUser.tableTransaksi.COLUMN_CONFIRM)),
                    getString(getColumnIndex(DBUser.tableTransaksi.COLUMN_PACKING)),
                    getString(getColumnIndex(DBUser.tableTransaksi.COLUMN_SEND)),
                    getString(getColumnIndex(DBUser.tableTransaksi.COLUMN_RECEIVE))

                )
                )
            }
        }

        return transaksi
    }

    fun addTransaksi(transaksi : Transaksi) {
        val dbWriter = writableDatabase
        val contentValues = ContentValues().apply {
            put(DBUser.tableTransaksi.COLUMN_USERID, transaksi.userID)
            put(DBUser.tableTransaksi.COLUMN_PAYMENT, transaksi.payment)
            put(DBUser.tableTransaksi.COLUMN_CONFIRM, transaksi.confirm)
            put(DBUser.tableTransaksi.COLUMN_PACKING, transaksi.packing)
            put(DBUser.tableTransaksi.COLUMN_SEND, transaksi.payment)
            put(DBUser.tableTransaksi.COLUMN_RECEIVE, transaksi.receive)
        }
        dbWriter.insert(DBUser.tableTransaksi.TABLE_TRANSAKSI, null, contentValues)
        dbWriter.close()
    }
}