package com.example.serviceaja.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.serviceaja.R
import kotlinx.android.synthetic.main.fragment_history.*


class HistoryFragment : Fragment() {

    private val onGoingFragment = OnGoingTransactionFragment()
    private val doneFragment = DoneTransactionFragment()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //default fragment
        replaceFragment(onGoingFragment)

        //berpindah fragment
        transactionMenu.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.onProcess -> {
                    onProcess.setTextColor(Color.WHITE)
                    done.setTextColor(Color.BLACK)
                    replaceFragment(onGoingFragment)
                }
                R.id.done -> {
                    onProcess.setTextColor(Color.BLACK)
                    done.setTextColor(Color.WHITE)
                    replaceFragment(doneFragment)
                }
            }
        }

    }

    private fun replaceFragment(fragment : Fragment) =
            childFragmentManager.beginTransaction().apply {
                replace(R.id.transaction_fragment, fragment)
                commit()
            }

    companion object {

    }
}