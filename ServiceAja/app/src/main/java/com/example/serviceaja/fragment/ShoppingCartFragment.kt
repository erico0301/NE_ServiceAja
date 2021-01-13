package com.example.serviceaja.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import com.example.serviceaja.checkout.CheckoutActivity
import com.example.serviceaja.classes.ItemProductInstance
import com.example.serviceaja.classes.ItemServiceInstance
import com.example.serviceaja.classes.ShoppingCartInstance
import com.example.serviceaja.recyclerview.RVShoppingCartContainer
import kotlinx.android.synthetic.main.fragment_shopping_cart.*

class ShoppingCartFragment : Fragment() {

    private var layoutManager : RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<RVShoppingCartContainer.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_shopping_cart, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var shoppingCartInstances = ShoppingCartInstance(
                "Mitsubishi",
                R.drawable.mitsubishi_logo,
                "Medan",
                mutableListOf(),
                mutableListOf())
        shoppingCartInstances.itemServiceInstances.add(ItemServiceInstance(R.drawable.wash_xpander, "Cuci Mobil", "50.000"))
        shoppingCartInstances.itemServiceInstances[0].namaMobil = "Mitsubishi Xpander"
        shoppingCartInstances.itemServiceInstances[0].platMobil = "BK 9999 XYZ"
        shoppingCartInstances.itemProductInstances.add(ItemProductInstance(R.drawable.fb_aki, "Aki Xpander", "650.000"))
        shoppingCartInstances.itemProductInstances[0].jumlahItem = 2

        var shoppingCartInstances2 = ShoppingCartInstance(
                "BMW",
                R.drawable.bmw_logo,
                R.drawable.bmw_logo_1,
                "Medan",
                mutableListOf(),
                mutableListOf())
        shoppingCartInstances2.itemProductInstances.add(ItemProductInstance(R.drawable.ban_rft, "Ban X5 RFT", "2.000.000"))
        shoppingCartInstances2.itemProductInstances[0].jumlahItem = 2
        shoppingCartInstances2.itemProductInstances.add(ItemProductInstance(R.drawable.wiper_bmw, "Wiper BMW X5 (Rear)", "630.000"))
        shoppingCartInstances2.itemProductInstances[1].jumlahItem = 2

        layoutManager = LinearLayoutManager (activity)
        shoppingCart_container.layoutManager = layoutManager
        // shoppingCart_container.addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))

        adapter = RVShoppingCartContainer(arrayListOf(shoppingCartInstances, shoppingCartInstances2))
        shoppingCart_container.adapter = adapter

        checkoutBtn.setOnClickListener {
            var checkoutIntent = Intent(context, CheckoutActivity::class.java)
            startActivity(checkoutIntent)
        }
    }

    companion object {

    }
}