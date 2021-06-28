package com.example.serviceaja

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.serviceaja.classes.DBHelper
import com.example.serviceaja.classes.User
import com.example.serviceaja.classes.WatchAdsSharedPref
import com.example.serviceaja.fragment.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_profil_user.*
import org.jetbrains.anko.doAsync

class HomeActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val historyFragment = HistoryFragment()
    private val shoppingCartFragment = ShoppingCartFragment()
    private val chatFragment = ChatFragment()
    private val profileFragment = ProfilUserFragment()
    lateinit var user: User
    private lateinit var activeFragment: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (savedInstanceState != null)
            user = savedInstanceState.getParcelable(EXTRA_USER)!!
        else
            user = intent.extras?.getParcelable(EXTRA_USER)!!

        activeFragment = "Home"
        activateFragment(activeFragment)

        bottomNavBarMenu.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.homeIcon -> {
                    activeFragment = "Home"
                    activateFragment(activeFragment)
                }
                R.id.historyIcon -> {
                    activeFragment = "History"
                    activateFragment(activeFragment)
                }
                R.id.cartIcon -> {
                    activeFragment = "Shopping Cart"
                    activateFragment(activeFragment)
                }
                R.id.chatIcon -> {
                    activeFragment = "Chat"
                    activateFragment(activeFragment)
                }
                R.id.profileIcon -> {
                    activeFragment = "Profile"
                    activateFragment(activeFragment)
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment : Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }

    fun activateFragment(activeFragment: String) {
        val bundle = Bundle()
        bundle.putParcelable(EXTRA_USER, user)
        this.activeFragment = activeFragment
        when (activeFragment) {
            "Home" -> {
                homeFragment.arguments = bundle
                replaceFragment(homeFragment)
            }
            "History" -> {
                historyFragment.arguments = bundle
                replaceFragment(historyFragment)
            }
            "Shopping Cart" -> {
                shoppingCartFragment.arguments = bundle
                replaceFragment(shoppingCartFragment)
            }
            "Chat" -> {
                chatFragment.arguments = bundle
                replaceFragment(chatFragment)
            }
            "Profile" -> {
                profileFragment.arguments = bundle
                replaceFragment(profileFragment)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE_EDIT_PROFILE) {
            if (resultCode == RESULT_OK && data != null) {
                Log.e("updated data accepted", "Acc")
                this.apply {
                    user = data.getParcelableExtra(EXTRA_USER_RETURN)!!
                    activateFragment("Profile")
                }
            }
        }
        else if (requestCode == 201) {
            if (resultCode == RESULT_OK) {
                replaceFragment(ProfilMitraFragment())
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(ACTIVE_FRAGMENT, activeFragment)
        outState.putParcelable(EXTRA_USER, user)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        activateFragment(savedInstanceState.getString(ACTIVE_FRAGMENT)!!)
        user = savedInstanceState.getParcelable(EXTRA_USER)!!
    }
}