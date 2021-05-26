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
        }
    }
}