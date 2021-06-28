package com.example.serviceaja.LoginRegister

import DBClass.DBUser
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.serviceaja.*
import com.example.serviceaja.classes.AccountSharedPref
import com.example.serviceaja.classes.DBHelper
import com.example.serviceaja.classes.User
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        val user = intent.getParcelableExtra<User>(EXTRA_USER)

        val db = DBHelper(this)
        db.addUser(user!!)

        val firebaseDb = FirebaseRealtimeDBHelper(this)
        firebaseDb.addUser(user)

        AccountSharedPref(this).no_telp = user.noTelp
        updateWidget()

        startBtn.setOnClickListener {
            var homeIntent = Intent(this, HomeActivity::class.java)
            homeIntent.putExtra(EXTRA_USER, user)
            startActivity(homeIntent)
            finishAffinity()
        }
    }

    private fun updateWidget() {
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val ids = appWidgetManager.getAppWidgetIds(ComponentName(this, InfoKendaraanWidget::class.java))
        val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        sendBroadcast(intent)
    }
}