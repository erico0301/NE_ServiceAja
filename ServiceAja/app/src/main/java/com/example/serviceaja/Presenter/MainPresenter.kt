package com.example.serviceaja.Presenter

class MainPresenter(SetView : MainVPInterface) {
    private var view = SetView
    private var mainModel = MainModel()

    fun TtlShopping(nominal : Double, qty : Int) = nominal * qty

    fun TtlPlusDisc(ttlNominal : Double, disc : Int) = ttlNominal - (ttlNominal*(disc/100.0))

    fun hitungTtlShopping(nominal: Double, qty: Int){
        var ttlShopping = TtlShopping(nominal, qty)
        mainModel.ttlShopping = ttlShopping
        view.hitungTtlShopping(mainModel)
    }

    fun hitungTtlPlusDisc(ttlNominal: Double, disc: Int){
        var ttlPlusDisc = TtlPlusDisc(ttlNominal,disc)
        mainModel.afterDisc = ttlPlusDisc
        view.hitungTtlPlusDisc(mainModel)
    }
}