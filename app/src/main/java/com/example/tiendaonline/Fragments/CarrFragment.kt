package com.example.tiendaonline.Fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tiendaonline.R
import com.example.tiendaonline.adapter.CartAdapter
import com.example.tiendaonline.model.dao.ItemDao
import kotlinx.coroutines.launch

class CarrFragment : Fragment(R.layout.fragment_carrito) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CartAdapter
//    private lateinit var db: AppDatabase
//    private lateinit var itemDao: ItemDao

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.rv_cart_items)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        db = AppDatabase.getDatabase(requireContext())
//        itemDao = db.itemDao()

        loadCartItems()

        view.findViewById<Button>(R.id.btn_vaciar_carrito).setOnClickListener {
            lifecycleScope.launch {
//                itemDao.clearItem()
//                loadCartItems()
                Toast.makeText(requireContext(), "Carrito vaciado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadCartItems() {
        lifecycleScope.launch {
//            val items = itemDao.getAllItems()
//            adapter = CartAdapter(items)
            recyclerView.adapter = adapter
        }
    }
}