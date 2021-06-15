package DBClass

import android.provider.BaseColumns

object DBAlamat {
    class tableAlamat : BaseColumns {
        companion object {
            val TABLE_ALAMAT = "alamat"
            val COLUMN_NO_TELP_USER = "no_telp_user"
            val COLUMN_NAME = "nama_alamat"
            val COLUMN_PENERIMA = "nama_penerima"
            val COLUMN_NO_TELP = "no_telp"
            val COLUMN_KECAMATAN = "kecamatan"
            val COLUMN_KAB_KOTA = "kabupaten_kota"
            val COLUMN_PROVINSI = "provinsi"
            val COLUMN_DETAIL = "detail_alamat"
        }
    }
}