package DBClass

import android.provider.BaseColumns

object DBUser {
    class tableUser: BaseColumns {
        companion object {
            val TABLE_USER = "user"
            val COLUMN_NO_TELP = "no_telp"
            val COLUMN_NAME = "nama"
            val COLUMN_EMAIL = "email"
            val COLUMN_PASSWORD = "password"
            val COLUMN_POINTS = "points"
            val COLUMN_PREMIUM_USER = "premium_user"
        }
    }
    class  tableTransaksi: BaseColumns{
        companion object {
            val TABLE_TRANSAKSI = "transaksi"
            val COLUMN_USERID = "userid"
            val COLUMN_PAYMENT = "payment"
            val COLUMN_CONFIRM = "confirm"
            val COLUMN_PACKING = "packing"
            val COLUMN_SEND = "send"
            val COLUMN_RECEIVE = "receive"
        }
    }
}