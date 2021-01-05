package com.example.serviceaja

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.serviceaja.fragment.*
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private  val homeFragment = HomeFragment()
    private val historyFragment = HistoryFragment()
    private val shoppingCartFragment = ShoppingCartFragment()
    private val chatFragment = ChatFragment()
    private val profileFragment = ProfilUserFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        replaceFragment(homeFragment)

        bottomNavBarMenu.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.homeIcon -> replaceFragment(homeFragment)
                R.id.historyIcon -> replaceFragment(historyFragment)
                R.id.cartIcon -> replaceFragment(shoppingCartFragment)
                R.id.chatIcon -> replaceFragment(chatFragment)
                R.id.profileIcon -> replaceFragment(profileFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment : Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }

}