package com.example.tiendaonline.Activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.tiendaonline.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference

class AddItemActivity : AppCompatActivity() {

    private lateinit var btnSeleccionar: Button
    private lateinit var btnTomarFoto: Button
    private lateinit var btnAgregar: Button
    private val IMAGE_REQUEST_CODE = 101
    private lateinit var imageUri: Uri
    private lateinit var storageReference: StorageReference
    private var currentPhotoPath: String? = null

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val REQUEST_CAMERA_PERMISSION = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_add_item)

        val editTextNombre = findViewById<EditText>(R.id.edt_product_name)
        val editTextDetalle = findViewById<EditText>(R.id.edt_product_detail)
        val editTextPrecio = findViewById<EditText>(R.id.edt_product_price)
        val imageViewProducto = findViewById<ImageView>(R.id.iv_producto)

        btnAgregar = findViewById<Button>(R.id.buttonAgregarProducto)
        btnSeleccionar = findViewById<Button>(R.id.buttonSeleccionarImagen)
        btnTomarFoto = findViewById<Button>(R.id.buttonTomarFoto)

        btnTomarFoto.setOnClickListener {
            if (checkCameraPermission()) {
                dispatchTakePictureIntent()
            } else {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
            }
        }

        btnSeleccionar.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, IMAGE_REQUEST_CODE)
        }

        btnAgregar.setOnClickListener {
            val nombre = editTextNombre.text.toString()
            val detalle = editTextDetalle.text.toString()
            val precio = editTextPrecio.text.toString()

            if (nombre.isNotEmpty() && detalle.isNotEmpty() && precio.isNotEmpty()) {
                uploadProductToFirebase(nombre, detalle, precio.toDouble(), imageUri)
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } else {
            Toast.makeText(this, "No se encontró aplicación de cámara", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val imageViewProducto = findViewById<ImageView>(R.id.iv_producto)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                IMAGE_REQUEST_CODE -> {
                    imageUri = data?.data!!
                    imageViewProducto.setImageURI(imageUri)
                }

                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as? Bitmap
                    if (imageBitmap != null) {
                        val path = MediaStore.Images.Media.insertImage(
                            contentResolver, imageBitmap, "Title", null
                        )
                        imageUri = Uri.parse(path)
                        imageViewProducto.setImageBitmap(imageBitmap)
                    }
                }
            }
        } else {
            Toast.makeText(this, "Error al seleccionar imagen", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadProductToFirebase(
        nombre: String,
        detalle: String,
        precio: Double,
        imageUri: Uri
    ) {
        val imageRef = storageReference.child("productos/${System.currentTimeMillis()}.jpg")
        imageRef.putFile(imageUri)
            .addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()
                    saveProductToFirestore(nombre, detalle, precio, imageUrl)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al subir imagen", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveProductToFirestore(
        nombre: String,
        detalle: String,
        precio: Double,
        imageUrl: String
    ) {
        val db = FirebaseFirestore.getInstance()
        val producto = hashMapOf(
            "nombre" to nombre,
            "detalle" to detalle,
            "precio" to precio,
            "imagen" to imageUrl
        )

        db.collection("productos")
            .add(producto)
            .addOnSuccessListener {
                Toast.makeText(this, "Producto agregado correctamente", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al agregar producto", Toast.LENGTH_SHORT).show()
            }
    }
}