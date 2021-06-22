package DBClass

import android.provider.BaseColumns

object DBTransaksi {
    class tableTransaksi: BaseColumns {
        companion object {
            val TABLE_USER = "transaksi"
            val COLUMN_PAYMENT = "payment"
            val COLUMN_CONFIRM = "confirm"
            val COLUMN_PACKING = "packing"
            val COLUMN_SEND = "send"
            val COLUMN_RECEIVE = "receive"
        }
    }
}