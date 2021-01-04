package com.example.serviceaja.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.serviceaja.R
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment : Fragment() {

    private val chatFragment = ChatUserFragment()
    private val reviewFragment = ReviewUserFragment()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        replaceFragment(chatFragment)
        chatMenu.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.chat -> {
                    chat.setTextColor(Color.WHITE)
                    review.setTextColor(Color.BLACK)
                    replaceFragment(chatFragment)
                }
                R.id.review  -> {
                    review.setTextColor(Color.WHITE)
                    chat.setTextColor(Color.BLACK)
                    replaceFragment(reviewFragment)
                }
            }
        }

    }

    private fun replaceFragment(fragment : Fragment) =
            childFragmentManager.beginTransaction().apply {
                replace(R.id.chatreview_fragment, fragment)
                commit()
            }

    companion object {

    }
}