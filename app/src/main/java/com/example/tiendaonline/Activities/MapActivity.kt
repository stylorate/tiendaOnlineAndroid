package com.example.tiendaonline.Activities

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.tiendaonline.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private val ubicacion1 = LatLng(4.6097, -74.0817)//TaBogo
    private val ubicacion2 = LatLng(3.4516, -76.5320)//Lo demás es loma
    private val ubicacion3 = LatLng(10.3927, -75.5144)//Cartagena

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        setupButtonListeners()
    }

    private fun setupButtonListeners() {
        findViewById<Button>(R.id.btn_ubicacion_1).setOnClickListener {
            moveToLocation(ubicacion1, "Bogotá")
        }
        findViewById<Button>(R.id.btn_ubicacion_2).setOnClickListener {
            moveToLocation(ubicacion2, "Cali")
        }
        findViewById<Button>(R.id.btn_ubicacion_3).setOnClickListener {
            moveToLocation(ubicacion3, "Cartagena")
        }
    }
    private fun moveToLocation(ubicacion: LatLng, s: String) {
        mMap.clear()
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15f))
        mMap.addMarker(MarkerOptions().position(ubicacion).title(s))
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion1,15f))
        mMap.addMarker(MarkerOptions().position(ubicacion1).title("Bogotá"))
    }


}