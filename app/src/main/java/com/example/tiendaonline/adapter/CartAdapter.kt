package com.example.tiendaonline.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tiendaonline.R
import com.example.tiendaonline.model.entity.Item

class CartAdapter(private val items: List<Item>) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.iv_product_cart)
        val name = view.findViewById<TextView>(R.id.tv_product_name_cart)
        val price = view.findViewById<TextView>(R.id.tv_product_price_cart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_carrito, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]
        holder.name.text = item.name
        holder.price.text = "$${item.price}"
        holder.image.setImageResource(item.imageId)
    }

    override fun getItemCount(): Int = items.size
}