package DBClass

import android.provider.BaseColumns

object DBProduk {
    class tableProduk: BaseColumns {
        companion object {
            val TABLE_NAME = "produk"
            val COLUMN_NAME = "nama_produk"
            val COLUMN_PRICE = "harga_produk"
        }
    }
}