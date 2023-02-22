package com.example.deber02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private var listaProductos: ArrayList<Producto> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listaProductos
            .add(Producto("Fire TV Stick con Alexa Voice Remote (incluye controles de TV), TV gratuita y en directo sin cable ni satélite, Dispositivo de streaming en HD"
                ,"39",
                "Entrega GRATIS <b>el lunes, 27 de feb</b>\nO entrega más rápida el <b>jueves, 23 de feb</b>",
                "Climate Pledge Friendly"))
        listaProductos
            .add(Producto("Dispositivo de streaming Fire TV Stick 4K Max con Wi-Fi 6 y control remoto por voz Alexa (incluye controles para la televisión)",
                "54",
                "Entrega GRATIS <b>el mier, 1 de mar  </b>\n" +
                        "O entrega más rápida el <b>viernes, 24 de feb</b>",
                "Climate Pledge Friendly"))
        listaProductos
            .add(Producto("Fire TV Stick 4K con la más reciente generación de control remoto por voz Alexa (incluye controles de televisión), Dolby Vision",
                "44",
                "Entrega GRATIS <b>el martes, 28 de feb</b>\n" +
                        "O entrega más rápida el <b>viernes, 24 de feb</b>",
                "Climate Pledge Friendly"))
        listaProductos
            .add(Producto("Fire TV Stick Lite control remoto por voz Alexa | Lite (no controla la televisión), dispositivo de streaming en HD",
                "24",
                "Entrega GRATIS <b>el mier, 29 de feb</b>\n" +
                        "O entrega más rápida el <b>viernes, 24 de feb</b>",
                "Climate Pledge Friendly"))
        listaProductos
            .add(Producto("Fire TV Stick Lite control remoto por voz Alexa | Lite (no controla la televisión), dispositivo de streaming en HD",
                "29",
                "Entrega GRATIS <b>el lunes, 27 de feb</b>\n" +
                        "O entrega más rápida el <b>viernes, 24 de feb</b>",
                "Climate Pledge Friendly"))

        val recyclerView = findViewById<RecyclerView>(R.id.rv_productos)
        inicializarRecyclerView(listaProductos,recyclerView)

        val botonPerfil = findViewById<ImageButton>(R.id.btn_perfil)
        botonPerfil
            .setOnClickListener{
                abrirActividad(Configuracion::class.java)
            }
    }

    fun inicializarRecyclerView(
        lista:ArrayList<Producto>,
        recyclerView: RecyclerView
    ){
        val adaptador = RecyclerProductos(
            this,
            lista
        )
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        adaptador.notifyDataSetChanged()
    }

    private fun abrirActividad(
        clase: Class<*>,
    ) {
        val i = Intent(this, clase)
        startActivity(i);
    }
}