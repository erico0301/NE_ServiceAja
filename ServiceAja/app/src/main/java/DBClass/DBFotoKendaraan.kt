package DBClass

import android.provider.BaseColumns

object DBFotoKendaraan {
    class tableFotoKendaraan : BaseColumns {
        companion object {
            val TABLE_NAME = "foto_kendaraan"
            val COLUMN_URI_PHOTO = "uri_photo"
        }
    }
}