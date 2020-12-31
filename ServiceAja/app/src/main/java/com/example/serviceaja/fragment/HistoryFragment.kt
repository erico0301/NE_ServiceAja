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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        onProcess.setOnClickListener {
            onProcess.setTextColor(Color.WHITE)
            done.setTextColor(Color.BLACK)
        }

        done.setOnClickListener {
            onProcess.setTextColor(Color.BLACK)
            done.setTextColor(Color.WHITE)
        }

    }

    companion object {

    }
}