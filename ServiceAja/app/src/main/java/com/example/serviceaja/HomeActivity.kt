package com.example.serviceaja

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.serviceaja.classes.User
import com.example.serviceaja.fragment.*
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val historyFragment = HistoryFragment()
    private val shoppingCartFragment = ShoppingCartFragment()
    private val chatFragment = ChatFragment()
    private val profileFragment = ProfilUserFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var user = intent.getParcelableExtra<User>(EXTRA_USER)

        val bundle = Bundle()
        bundle.putParcelable(EXTRA_USER, user)

        replaceFragment(homeFragment)

        bottomNavBarMenu.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.homeIcon -> {
                    homeFragment.arguments = bundle
                    replaceFragment(homeFragment)
                }
                R.id.historyIcon -> {
                    historyFragment.arguments = bundle
                    replaceFragment(historyFragment)
                }
                R.id.cartIcon -> {
                    shoppingCartFragment.arguments = bundle
                    replaceFragment(shoppingCartFragment)
                }
                R.id.chatIcon -> {
                    chatFragment.arguments = bundle
                    replaceFragment(chatFragment)
                }
                R.id.profileIcon -> {
                    profileFragment.arguments = bundle
                    replaceFragment(profileFragment)
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

}