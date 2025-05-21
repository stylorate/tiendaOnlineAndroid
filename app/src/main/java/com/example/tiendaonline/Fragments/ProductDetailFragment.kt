package com.example.tiendaonline.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.tiendaonline.Activities.MapActivity
import com.example.tiendaonline.Activities.ProductsActivity
import com.example.tiendaonline.model.Product
import com.example.tiendaonline.R
import com.example.tiendaonline.model.entity.Item
import kotlinx.coroutines.launch

class ProductDetailFragment : Fragment() {

    private lateinit var product: Product

    companion object {
        private const val ARG_PRODUCTO = "producto"
        fun newInstance(product: Product): ProductDetailFragment {
            val fragment = ProductDetailFragment()
            val args = Bundle()
            args.putParcelable(ARG_PRODUCTO, product)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            product =
                arguments?.getParcelable(ARG_PRODUCTO, Product::class.java)
                    ?: Product(
                        0, "sin nombre", "sin descripcion", 0.0,
                        R.drawable.usuario
                    )
        } else {
            product = Product(
                0, "sin nombre", "sin descripcion", 0.0,
                R.drawable.usuario
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_detalle_producto, container, false)
        val imageProduct = view.findViewById<ImageView>(R.id.iv_imagen_producto)
        val nameProduct = view.findViewById<TextView>(R.id.tv_detalle_nombre_producto)
        val priceProduct = view.findViewById<TextView>(R.id.tv_detalle_precio_producto)
        val descriptionProduct = view.findViewById<TextView>(R.id.tv_detalle_descripcion_producto)
        val btnComprar = view.findViewById<Button>(R.id.btn_comprar)

//        val db = AppDatabase.getDatabase(requireContext())
//        val itemDao = db.itemDao()

        imageProduct.setImageResource(product.imageUrl)
        nameProduct.text = product.name
        priceProduct.text = "Precio: $${product.price}"
        descriptionProduct.text = product.description

        btnComprar.setOnClickListener {
//            val item = Item(
//                name = product.name,
//                price = product.price,
//                imageId = product.imageUrl,
//                quantity = 1,
//                idProduct = product.id
//            )
//            lifecycleScope.launch {
//                itemDao.insertItem(item)
//                Toast.makeText(requireContext(), "Producto agregado al carrito", Toast.LENGTH_SHORT).show()
//            }
            Toast.makeText(requireContext(), "Producto agregado al carrito", Toast.LENGTH_SHORT)
                .show()
            val intent = Intent(requireContext(), MapActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}