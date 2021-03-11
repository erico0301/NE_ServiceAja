package com.example.serviceaja

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.serviceaja.classes.User
import com.example.serviceaja.fragment.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_profil_user.*

class HomeActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val historyFragment = HistoryFragment()
    private val shoppingCartFragment = ShoppingCartFragment()
    private val chatFragment = ChatFragment()
    private val profileFragment = ProfilUserFragment()

    private lateinit var activeFragment: String
    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        user = intent.getParcelableExtra<User>(EXTRA_USER)!!

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(ACTIVE_FRAGMENT, activeFragment)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        activateFragment(savedInstanceState.getString(ACTIVE_FRAGMENT)!!)
    }
}