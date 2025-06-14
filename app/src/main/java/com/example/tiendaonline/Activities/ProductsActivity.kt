package com.example.tiendaonline.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tiendaonline.Fragments.ProductDetailFragment
import com.example.tiendaonline.R
import com.example.tiendaonline.adapter.ProductAdapter
import com.example.tiendaonline.model.Product

class ProductsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var products: List<Product>
    private lateinit var btnAddItem: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_lista_producto)
        btnAddItem = findViewById<Button>(R.id.btn_addItem)

        recyclerView = findViewById(R.id.rv_products)

        products = generateProducts()

        adapter = ProductAdapter(products){product ->
            val fragment = ProductDetailFragment.newInstance(product)
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, fragment)
                .addToBackStack(null)
                .commit()
        }

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter

        btnAddItem.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            startActivity(intent)
        }
    }

    private fun generateProducts(): List<Product> {
        return listOf(
            Product(
                1,
                "Producto 1",
                "Detalle",
                50.0,
                R.drawable.item
            ),
            Product(
                2,
                "Producto 2",
                "Detalle",
                50.0,
                R.drawable.item
            ),
        )
    }
}