package com.example.tiendaonline.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tiendaonline.R
import com.example.tiendaonline.adapter.ProductAdapter
import com.example.tiendaonline.model.Product

class ProductsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var products: List<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        recyclerView = findViewById(R.id.recyclerViewProducts)

        products = generateProducts()

        adapter = ProductAdapter(products)

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter
    }

    private fun generateProducts(): List<Product> {
        return listOf(
            Product(
                "Producto 1",
                "https://www.google.com/imgres?q=imagen%20producto&imgurl=https%3A%2F%2Fcdn-icons-png.flaticon.com%2F512%2F5617%2F5617585.png&imgrefurl=https%3A%2F%2Fwww.flaticon.es%2Ficono-gratis%2Fproducto_5617585&docid=3fW3iS6Q3QiY5M&tbnid=u7wCZ3HTtvJ_-M&vet=12ahUKEwjn67XU5PmMAxUzTaQEHTtlHE4QM3oECBoQAA..i&w=512&h=512&hcb=2&ved=2ahUKEwjn67XU5PmMAxUzTaQEHTtlHE4QM3oECBoQAA"
            ),
            Product(
                "Producto 2",
                "https://www.google.com/imgres?q=imagen%20producto&imgurl=https%3A%2F%2Fcdn-icons-png.flaticon.com%2F512%2F5617%2F5617585.png&imgrefurl=https%3A%2F%2Fwww.flaticon.es%2Ficono-gratis%2Fproducto_5617585&docid=3fW3iS6Q3QiY5M&tbnid=u7wCZ3HTtvJ_-M&vet=12ahUKEwjn67XU5PmMAxUzTaQEHTtlHE4QM3oECBoQAA..i&w=512&h=512&hcb=2&ved=2ahUKEwjn67XU5PmMAxUzTaQEHTtlHE4QM3oECBoQAA"
            ),
        )
    }
}