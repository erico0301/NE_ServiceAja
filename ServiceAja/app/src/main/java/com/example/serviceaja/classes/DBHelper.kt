package com.example.serviceaja.classes

import DBClass.*
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DBHelper(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        val DB_NAME = "ServiceAja.db"
        val DB_VERSION = 3
    }

    val dbWriter = writableDatabase
    val dbReader = readableDatabase

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

        val CREATE_KENDARAAN_TABLE = """
            CREATE TABLE ${DBKendaraan.tableKendaraan.TABLE_KENDARAAN}
            (
                ${DBUser.tableUser.COLUMN_NO_TELP} TEXT,
                ${DBKendaraan.tableKendaraan.COLUMN_JENIS} TEXT,
                ${DBKendaraan.tableKendaraan.COLUMN_PLAT} TEXT PRIMARY KEY,
                ${DBKendaraan.tableKendaraan.COLUMN_MERK} TEXT,
                ${DBKendaraan.tableKendaraan.COLUMN_NAMA} TEXT,
                ${DBKendaraan.tableKendaraan.COLUMN_TAHUN} INTEGER,
                ${DBKendaraan.tableKendaraan.COLUMN_WARNA} TEXT,
                ${DBKendaraan.tableKendaraan.COLUMN_BAHAN_BAKAR} TEXT,
                ${DBKendaraan.tableKendaraan.COLUMN_NO_RANGKA} TEXT,
                ${DBKendaraan.tableKendaraan.COLUMN_NO_MESIN} TEXT,
                ${DBKendaraan.tableKendaraan.COLUMN_NO_BPKB} TEXT,
                ${DBKendaraan.tableKendaraan.COLUMN_SERVICE_TERAKHIR} TEXT,
                FOREIGN KEY (${DBUser.tableUser.COLUMN_NO_TELP}) REFERENCES ${DBUser.tableUser.TABLE_USER} (${DBUser.tableUser.COLUMN_NO_TELP})
            )
        """.trimIndent()

        val CREATE_FOTO_KENDARAAN_TABLE = """
            CREATE TABLE ${DBFotoKendaraan.tableFotoKendaraan.TABLE_NAME}
            (
                ${DBKendaraan.tableKendaraan.COLUMN_PLAT} TEXT,
                ${DBFotoKendaraan.tableFotoKendaraan.COLUMN_URI_PHOTO} TEXT,
                FOREIGN KEY (${DBKendaraan.tableKendaraan.COLUMN_PLAT}) REFERENCES ${DBKendaraan.tableKendaraan.TABLE_KENDARAAN} (${DBKendaraan.tableKendaraan.COLUMN_PLAT})
            )
        """.trimIndent()

        val CREATE_ALAMAT_TABLE = """
            CREATE TABLE ${DBAlamat.tableAlamat.TABLE_ALAMAT}
            (
                ${BaseColumns._ID} INTEGER PRIMARY KEY,
                ${DBAlamat.tableAlamat.COLUMN_NO_TELP_USER} TEXT,
                ${DBAlamat.tableAlamat.COLUMN_NAME} TEXT,
                ${DBAlamat.tableAlamat.COLUMN_PENERIMA} TEXT,
                ${DBAlamat.tableAlamat.COLUMN_NO_TELP} TEXT,
                ${DBAlamat.tableAlamat.COLUMN_KECAMATAN} TEXT,
                ${DBAlamat.tableAlamat.COLUMN_KAB_KOTA} TEXT,
                ${DBAlamat.tableAlamat.COLUMN_PROVINSI} TEXT,
                ${DBAlamat.tableAlamat.COLUMN_DETAIL} TEXT,
                FOREIGN KEY (${DBAlamat.tableAlamat.COLUMN_NO_TELP_USER}) REFERENCES ${DBUser.tableUser.TABLE_USER} (${DBUser.tableUser.COLUMN_NO_TELP})
            )
        """.trimIndent()

        val CREATE_PRODUK_TABLE = """
            CREATE TABLE ${DBProduk.tableProduk.TABLE_NAME}
            (
                ${BaseColumns._ID} INTEGER PRIMARY KEY,
                ${DBProduk.tableProduk.COLUMN_NAME} TEXT,
                ${DBProduk.tableProduk.COLUMN_PRICE} INTEGER
            )
        """.trimIndent()

        db?.execSQL(CREATE_USER_TABLE)
        db?.execSQL(CREATE_KENDARAAN_TABLE)
        db?.execSQL(CREATE_FOTO_KENDARAAN_TABLE)
        db?.execSQL(CREATE_ALAMAT_TABLE)
        db?.execSQL(CREATE_PRODUK_TABLE)
        Log.e("onCreate SQLiteHelper", "Dijalankan")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${DBUser.tableUser.TABLE_USER}")
        db?.execSQL("DROP TABLE IF EXISTS ${DBAlamat.tableAlamat.TABLE_ALAMAT}")
        onCreate(db)
    }

    val beginTransaction : () -> Unit = {
        dbWriter.beginTransaction()
        dbReader.beginTransaction()
    }
    val successTransaction : () -> Unit = {
        dbWriter.setTransactionSuccessful()
        dbReader.setTransactionSuccessful()
    }
    val endTransaction : () -> Unit = {
        dbWriter.endTransaction()
        dbReader.endTransaction()
    }

    fun getAllUsers(): ArrayList<User> {
        val users = arrayListOf<User>()

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
                        getString(getColumnIndex(DBUser.tableUser.COLUMN_NAME)),
                        getString(getColumnIndex(DBUser.tableUser.COLUMN_EMAIL)),
                        getString(getColumnIndex(DBUser.tableUser.COLUMN_NO_TELP)),
                        getString(getColumnIndex(DBUser.tableUser.COLUMN_PASSWORD))
                ))
            }
        }
        cursor.close()
        return users
    }

    fun getUserWithNoTelp(no_telp: String): User {
        val cursor = dbReader.query(
                DBUser.tableUser.TABLE_USER,
                null,
                "${DBUser.tableUser.COLUMN_NO_TELP} = ?",
                arrayOf(no_telp),
                null,
                null,
                null
        )
        cursor.moveToFirst()
        return User(
            cursor.getString(cursor.getColumnIndex(DBUser.tableUser.COLUMN_NO_TELP)),
            cursor.getString(cursor.getColumnIndex(DBUser.tableUser.COLUMN_NO_TELP)),
            cursor.getString(cursor.getColumnIndex(DBUser.tableUser.COLUMN_NO_TELP)),
            cursor.getString(cursor.getColumnIndex(DBUser.tableUser.COLUMN_NO_TELP))
        ).apply { cursor.close() }
    }

    fun addUser(user: User) {
        val contentValues = ContentValues().apply {
            put(DBUser.tableUser.COLUMN_NO_TELP, user.noTelp)
            put(DBUser.tableUser.COLUMN_NAME, user.nama)
            put(DBUser.tableUser.COLUMN_EMAIL, user.email)
            put(DBUser.tableUser.COLUMN_PASSWORD, user.password)
        }
        dbWriter.insert(DBUser.tableUser.TABLE_USER, null, contentValues)
    }

    fun getAllKendaraan(no_telp: String) : ArrayList<Kendaraan> {
        val kendaraan = arrayListOf<Kendaraan>()

        val query = """
            SELECT DISTINCT
                ${DBKendaraan.tableKendaraan.COLUMN_JENIS},
                ${DBKendaraan.tableKendaraan.TABLE_KENDARAAN}.${DBKendaraan.tableKendaraan.COLUMN_PLAT},
                ${DBKendaraan.tableKendaraan.COLUMN_MERK},
                ${DBKendaraan.tableKendaraan.COLUMN_NAMA},
                ${DBKendaraan.tableKendaraan.COLUMN_TAHUN},
                ${DBKendaraan.tableKendaraan.COLUMN_WARNA},
                ${DBKendaraan.tableKendaraan.COLUMN_BAHAN_BAKAR},
                ${DBKendaraan.tableKendaraan.COLUMN_NO_RANGKA},
                ${DBKendaraan.tableKendaraan.COLUMN_NO_MESIN},
                ${DBKendaraan.tableKendaraan.COLUMN_NO_BPKB},
                ${DBKendaraan.tableKendaraan.COLUMN_SERVICE_TERAKHIR},
                ${DBFotoKendaraan.tableFotoKendaraan.COLUMN_URI_PHOTO}
            FROM ${DBKendaraan.tableKendaraan.TABLE_KENDARAAN}
            INNER JOIN ${DBFotoKendaraan.tableFotoKendaraan.TABLE_NAME}
            ON ${DBKendaraan.tableKendaraan.TABLE_KENDARAAN}.${DBKendaraan.tableKendaraan.COLUMN_PLAT} = ${DBFotoKendaraan.tableFotoKendaraan.TABLE_NAME}.${DBKendaraan.tableKendaraan.COLUMN_PLAT}
            WHERE ${DBKendaraan.tableKendaraan.TABLE_KENDARAAN}.${DBUser.tableUser.COLUMN_NO_TELP} = ?
        """.trimIndent()

        val cursor = dbReader.rawQuery(query, arrayOf(no_telp))
        if (cursor.count == 0) return kendaraan
        with(cursor) {
            while (moveToNext()) {
                kendaraan.add(Kendaraan(
                        getString(getColumnIndex(DBKendaraan.tableKendaraan.COLUMN_JENIS)),
                        getString(getColumnIndex(DBKendaraan.tableKendaraan.COLUMN_PLAT)),
                        getString(getColumnIndex(DBKendaraan.tableKendaraan.COLUMN_MERK)),
                        getString(getColumnIndex(DBKendaraan.tableKendaraan.COLUMN_NAMA)),
                        getInt(getColumnIndex(DBKendaraan.tableKendaraan.COLUMN_TAHUN)),
                        getString(getColumnIndex(DBKendaraan.tableKendaraan.COLUMN_BAHAN_BAKAR)),
                        getString(getColumnIndex(DBKendaraan.tableKendaraan.COLUMN_WARNA)),
                        getString(getColumnIndex(DBKendaraan.tableKendaraan.COLUMN_NO_RANGKA)),
                        getString(getColumnIndex(DBKendaraan.tableKendaraan.COLUMN_NO_MESIN)),
                        getString(getColumnIndex(DBKendaraan.tableKendaraan.COLUMN_NO_BPKB)),
                        getString(getColumnIndex(DBKendaraan.tableKendaraan.COLUMN_SERVICE_TERAKHIR)),
                        getString(getColumnIndex(DBFotoKendaraan.tableFotoKendaraan.COLUMN_URI_PHOTO))
                ))
            }
        }
        cursor.close()
        return kendaraan
    }

    fun getKendaraanByPlat(no_plat: String): Kendaraan? {
        val query = """
            SELECT
                ${DBKendaraan.tableKendaraan.COLUMN_JENIS},
                ${DBKendaraan.tableKendaraan.TABLE_KENDARAAN}.${DBKendaraan.tableKendaraan.COLUMN_PLAT},
                ${DBKendaraan.tableKendaraan.COLUMN_MERK},
                ${DBKendaraan.tableKendaraan.COLUMN_NAMA},
                ${DBKendaraan.tableKendaraan.COLUMN_TAHUN},
                ${DBKendaraan.tableKendaraan.COLUMN_WARNA},
                ${DBKendaraan.tableKendaraan.COLUMN_BAHAN_BAKAR},
                ${DBKendaraan.tableKendaraan.COLUMN_NO_RANGKA},
                ${DBKendaraan.tableKendaraan.COLUMN_NO_MESIN},
                ${DBKendaraan.tableKendaraan.COLUMN_NO_BPKB},
                ${DBKendaraan.tableKendaraan.COLUMN_SERVICE_TERAKHIR},
                ${DBFotoKendaraan.tableFotoKendaraan.COLUMN_URI_PHOTO}
            FROM ${DBKendaraan.tableKendaraan.TABLE_KENDARAAN}
            INNER JOIN ${DBFotoKendaraan.tableFotoKendaraan.TABLE_NAME}
            ON ${DBKendaraan.tableKendaraan.TABLE_KENDARAAN}.${DBKendaraan.tableKendaraan.COLUMN_PLAT} = ${DBFotoKendaraan.tableFotoKendaraan.TABLE_NAME}.${DBKendaraan.tableKendaraan.COLUMN_PLAT}
            WHERE ${DBKendaraan.tableKendaraan.TABLE_KENDARAAN}.${DBKendaraan.tableKendaraan.COLUMN_PLAT} = ?
        """.trimIndent()

        val cursor = dbReader.rawQuery(query, arrayOf(no_plat))
        if (cursor.count > 0) {
            cursor.moveToFirst()
            val kendaraan = Kendaraan(
                    cursor.getString(cursor.getColumnIndex(DBKendaraan.tableKendaraan.COLUMN_JENIS)),
                    cursor.getString(cursor.getColumnIndex(DBKendaraan.tableKendaraan.COLUMN_PLAT)),
                    cursor.getString(cursor.getColumnIndex(DBKendaraan.tableKendaraan.COLUMN_MERK)),
                    cursor.getString(cursor.getColumnIndex(DBKendaraan.tableKendaraan.COLUMN_NAMA)),
                    cursor.getString(cursor.getColumnIndex(DBKendaraan.tableKendaraan.COLUMN_TAHUN)).toInt(),
                    cursor.getString(cursor.getColumnIndex(DBKendaraan.tableKendaraan.COLUMN_BAHAN_BAKAR)),
                    cursor.getString(cursor.getColumnIndex(DBKendaraan.tableKendaraan.COLUMN_WARNA)),
                    cursor.getString(cursor.getColumnIndex(DBKendaraan.tableKendaraan.COLUMN_NO_RANGKA)),
                    cursor.getString(cursor.getColumnIndex(DBKendaraan.tableKendaraan.COLUMN_NO_MESIN)),
                    cursor.getString(cursor.getColumnIndex(DBKendaraan.tableKendaraan.COLUMN_NO_BPKB)),
                    cursor.getString(cursor.getColumnIndex(DBKendaraan.tableKendaraan.COLUMN_SERVICE_TERAKHIR)),
                    cursor.getString(cursor.getColumnIndex(DBFotoKendaraan.tableFotoKendaraan.COLUMN_URI_PHOTO))
            )
            cursor.close()

            return kendaraan
        }
        cursor.close()
        return null
    }

    fun addKendaraan(no_telp: String, kendaraan: Kendaraan, arrayUriPhoto: ArrayList<String>) {
        val contentValues = ContentValues().apply {
            put(DBUser.tableUser.COLUMN_NO_TELP, no_telp)
            put(DBKendaraan.tableKendaraan.COLUMN_JENIS, kendaraan.jenis)
            put(DBKendaraan.tableKendaraan.COLUMN_PLAT, kendaraan.plat)
            put(DBKendaraan.tableKendaraan.COLUMN_MERK, kendaraan.merk)
            put(DBKendaraan.tableKendaraan.COLUMN_NAMA, kendaraan.nama)
            put(DBKendaraan.tableKendaraan.COLUMN_TAHUN, kendaraan.tahun)
            put(DBKendaraan.tableKendaraan.COLUMN_WARNA, kendaraan.warna)
            put(DBKendaraan.tableKendaraan.COLUMN_BAHAN_BAKAR, kendaraan.bahanBakar)
            put(DBKendaraan.tableKendaraan.COLUMN_NO_RANGKA, kendaraan.noRangka)
            put(DBKendaraan.tableKendaraan.COLUMN_NO_MESIN, kendaraan.noMesin)
            put(DBKendaraan.tableKendaraan.COLUMN_NO_BPKB, kendaraan.noBPKB)
            put(DBKendaraan.tableKendaraan.COLUMN_SERVICE_TERAKHIR, kendaraan.serviceTerakhir)
        }
        dbWriter.insert(DBKendaraan.tableKendaraan.TABLE_KENDARAAN, null, contentValues)

        for (i in arrayUriPhoto)
            addPhotoKendaraan(kendaraan.plat, i)
    }

    fun deleteAllKendaraan() {
        dbWriter.delete(DBFotoKendaraan.tableFotoKendaraan.TABLE_NAME, null, null)
        dbWriter.delete(DBKendaraan.tableKendaraan.TABLE_KENDARAAN, null, null)
    }

    fun deleteKendaraanByPlat(no_plat: String) {
        dbWriter.delete(DBFotoKendaraan.tableFotoKendaraan.TABLE_NAME, "${DBKendaraan.tableKendaraan.COLUMN_PLAT} = ?", arrayOf(no_plat))
        dbWriter.delete(DBKendaraan.tableKendaraan.TABLE_KENDARAAN, "${DBKendaraan.tableKendaraan.COLUMN_PLAT} = ?", arrayOf(no_plat))
    }

    fun addPhotoKendaraan(plat: String, path: String) {
        val contentValues = ContentValues().apply {
            put(DBKendaraan.tableKendaraan.COLUMN_PLAT, plat)
            put(DBFotoKendaraan.tableFotoKendaraan.COLUMN_URI_PHOTO, path)
        }
        dbWriter.insert(DBFotoKendaraan.tableFotoKendaraan.TABLE_NAME, null, contentValues)
    }

    fun getAllAlamat(no_telp: String): ArrayList<Alamat> {
        val alamat = arrayListOf<Alamat>()

        val projection = arrayOf(
                DBAlamat.tableAlamat.COLUMN_NAME,
                DBAlamat.tableAlamat.COLUMN_PENERIMA,
                DBAlamat.tableAlamat.COLUMN_NO_TELP,
                DBAlamat.tableAlamat.COLUMN_KECAMATAN,
                DBAlamat.tableAlamat.COLUMN_KAB_KOTA,
                DBAlamat.tableAlamat.COLUMN_PROVINSI,
                DBAlamat.tableAlamat.COLUMN_DETAIL
        )

        val cursor = dbReader.query(
                DBAlamat.tableAlamat.TABLE_ALAMAT,
                projection,
                "${DBAlamat.tableAlamat.COLUMN_NO_TELP_USER} = ?",
                arrayOf(no_telp),
                null,
                null,
                null
        )

        with(cursor) {
            while (moveToNext()) {
                alamat.add(Alamat(
                        getString(getColumnIndex(DBAlamat.tableAlamat.COLUMN_NAME)),
                        getString(getColumnIndex(DBAlamat.tableAlamat.COLUMN_PENERIMA)),
                        getString(getColumnIndex(DBAlamat.tableAlamat.COLUMN_NO_TELP)),
                        getString(getColumnIndex(DBAlamat.tableAlamat.COLUMN_KECAMATAN)),
                        getString(getColumnIndex(DBAlamat.tableAlamat.COLUMN_KAB_KOTA)),
                        getString(getColumnIndex(DBAlamat.tableAlamat.COLUMN_PROVINSI)),
                        getString(getColumnIndex(DBAlamat.tableAlamat.COLUMN_DETAIL))
                ))
            }
        }
        cursor.close()
        return alamat
    }

    fun addAlamat(no_telp: String, alamat: Alamat) {
        val contentValues = ContentValues().apply {
            put(DBAlamat.tableAlamat.COLUMN_NO_TELP_USER, no_telp)
            put(DBAlamat.tableAlamat.COLUMN_NAME, alamat.namaAlamat)
            put(DBAlamat.tableAlamat.COLUMN_PENERIMA, alamat.namaPenerima)
            put(DBAlamat.tableAlamat.COLUMN_NO_TELP, alamat.noTelepon)
            put(DBAlamat.tableAlamat.COLUMN_KECAMATAN, alamat.kecamatan)
            put(DBAlamat.tableAlamat.COLUMN_KAB_KOTA, alamat.kabupatenKota)
            put(DBAlamat.tableAlamat.COLUMN_PROVINSI, alamat.provinsi)
            put(DBAlamat.tableAlamat.COLUMN_DETAIL, alamat.detailAlamat)
        }
        dbWriter.insert(DBAlamat.tableAlamat.TABLE_ALAMAT, null, contentValues)
    }

    fun updateAlamat(no_telp: String, oldAddress: Alamat, newAddress: Alamat) {
        val contentValues = ContentValues().apply {
            put(DBAlamat.tableAlamat.COLUMN_NAME, newAddress.namaAlamat)
            put(DBAlamat.tableAlamat.COLUMN_PENERIMA, newAddress.namaPenerima)
            put(DBAlamat.tableAlamat.COLUMN_NO_TELP, newAddress.noTelepon)
            put(DBAlamat.tableAlamat.COLUMN_KECAMATAN, newAddress.kecamatan)
            put(DBAlamat.tableAlamat.COLUMN_KAB_KOTA, newAddress.kabupatenKota)
            put(DBAlamat.tableAlamat.COLUMN_PROVINSI, newAddress.provinsi)
            put(DBAlamat.tableAlamat.COLUMN_DETAIL, newAddress.detailAlamat)
        }

        val whereClause = """
            (
                ${DBAlamat.tableAlamat.COLUMN_NO_TELP_USER},
                ${DBAlamat.tableAlamat.COLUMN_NAME},
                ${DBAlamat.tableAlamat.COLUMN_PENERIMA},
                ${DBAlamat.tableAlamat.COLUMN_NO_TELP},
                ${DBAlamat.tableAlamat.COLUMN_KECAMATAN},
                ${DBAlamat.tableAlamat.COLUMN_KAB_KOTA},
                ${DBAlamat.tableAlamat.COLUMN_PROVINSI},
                ${DBAlamat.tableAlamat.COLUMN_DETAIL}
            ) = (?, ?, ?, ?, ?, ?, ?, ?)
        """.trimIndent()

        dbWriter.update(
                DBAlamat.tableAlamat.TABLE_ALAMAT,
                contentValues,
                whereClause,
                arrayOf(no_telp, oldAddress.namaAlamat, oldAddress.namaPenerima,
                        oldAddress.noTelepon, oldAddress.kecamatan, oldAddress.kabupatenKota,
                        oldAddress.provinsi, oldAddress.detailAlamat)
        )
    }

    fun getAllProducts() : ArrayList<Produk> {
        val produk = arrayListOf<Produk>()

        val cursor = dbReader.query(
                DBProduk.tableProduk.TABLE_NAME,
                arrayOf(DBProduk.tableProduk.COLUMN_NAME, DBProduk.tableProduk.COLUMN_PRICE),
                null,
                null,
                null,
                null,
                "${DBProduk.tableProduk.COLUMN_NAME} ASC"
        )
        with(cursor) {
            while (moveToNext()) {
                produk.add(
                    Produk(
                        getString(getColumnIndex(DBProduk.tableProduk.COLUMN_NAME)),
                        getInt(getColumnIndex(DBProduk.tableProduk.COLUMN_PRICE)).toLong()
                    )
                )
            }
        }
        cursor.close()
        return produk
    }

    fun addProduct(produk: Produk) {
        val contentValues = ContentValues().apply {
            put(DBProduk.tableProduk.COLUMN_NAME, produk.nama)
            put(DBProduk.tableProduk.COLUMN_PRICE, produk.harga.toInt())
        }
        dbWriter.insert(DBProduk.tableProduk.TABLE_NAME, null, contentValues)
    }

    fun updateProduct(produkLama: Produk, produkBaru: Produk) {
        val contentValues = ContentValues().apply {
            put(DBProduk.tableProduk.COLUMN_NAME, produkBaru.nama)
            put(DBProduk.tableProduk.COLUMN_PRICE, produkBaru.harga.toInt())
        }
        val whereClause = "(${DBProduk.tableProduk.COLUMN_NAME}, ${DBProduk.tableProduk.COLUMN_PRICE}) = (?, ?)"
        dbWriter.update(DBProduk.tableProduk.TABLE_NAME, contentValues, whereClause, arrayOf(produkLama.nama, produkLama.harga.toString()))
    }

    fun deleteAllProducts() {
        dbWriter.delete(DBProduk.tableProduk.TABLE_NAME, null, null)
    }
}