package com.example.serviceaja.Presenter

import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito

class MainPresenterTest {
    val view : MainVPInterface = Mockito.mock(MainVPInterface::class.java)
    val presenter = MainPresenter(view)

    @Test
    fun hitungTtlShopping() {
        val ttlShopping = presenter.TtlShopping(1000000.0, 2)
        assertEquals(2000000.0,ttlShopping, 0.0001)
    }

    @Test
    fun hitungTtlPlusDisc() {
        val ttlPlusDisc = presenter.TtlPlusDisc(presenter.TtlShopping(1000000.0,2), 25)
        assertEquals(1500000.0,ttlPlusDisc,0.0001)
    }
}