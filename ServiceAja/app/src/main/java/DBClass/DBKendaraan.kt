package DBClass

import android.provider.BaseColumns

object DBKendaraan {
    class tableKendaraan : BaseColumns {
        companion object {
            val TABLE_KENDARAAN = "kendaraan"
            val COLUMN_JENIS = "jenis"
            val COLUMN_PLAT = "no_plat"
            val COLUMN_MERK = "merk"
            val COLUMN_NAMA = "nama"
            val COLUMN_TAHUN = "tahun"
            val COLUMN_BAHAN_BAKAR = "bahan_bakar"
            val COLUMN_WARNA = "warna"
            val COLUMN_NO_RANGKA = "no_rangka"
            val COLUMN_NO_MESIN = "no_mesin"
            val COLUMN_NO_BPKB = "no_bpkb"
            val COLUMN_SERVICE_TERAKHIR = "service_terakhir"
        }
    }
}