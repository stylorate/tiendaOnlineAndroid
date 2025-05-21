package com.example.tiendaonline.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tiendaonline.R
import com.example.tiendaonline.model.Product
import java.net.HttpURLConnection
import java.net.URL

class ProductAdapter (private val products: List<Product>,
                      private val onItemClick: (Product) -> Unit) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageProduct: ImageView = itemView.findViewById(R.id.imageProduct)
        val textProductName: TextView = itemView.findViewById(R.id.textProductName)
        init {
            itemView.setOnClickListener {
                onItemClick(products[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.textProductName.text = product.name

        loadImageFromUrl(product.imageUrl, holder.imageProduct)

//        Glide.with(holder.itemView.context)
//            .load(product.imageUrl)
//            .placeholder(R.drawable.placeholder_image)
//            .into(holder.imageProduct)
    }

    override fun getItemCount(): Int = products.size

    private fun loadImageFromUrl(urlString: Int, imageView: ImageView) {
        Thread {
            try {
//                val url = URL(urlString)
//                val connection = url.openConnection() as HttpURLConnection
//                connection.doInput = true
//                connection.connect()
//                val inputStream = connection.inputStream
//                val bitmap = BitmapFactory.decodeStream(inputStream)
//
//                imageView.post {
//                    imageView.setImageBitmap(bitmap)
//                }

                imageView.setImageResource(urlString)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }
}