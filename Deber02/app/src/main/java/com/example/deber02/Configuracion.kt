package com.example.deber02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView

class Configuracion : AppCompatActivity() {
    private var listaConfiguraciones: ArrayList<Config> = arrayListOf()
    private var listaConfiguraciones2: ArrayList<Config> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracion)

        listaConfiguraciones
            .add(Config("Tus Pedidos"))
        listaConfiguraciones
            .add(Config("Tu Subscribe & Save"))
        listaConfiguraciones
            .add(Config("Tus pedidos de alquiler"))
        listaConfiguraciones
            .add(Config("Solicitudes de servicio"))
        listaConfiguraciones
            .add(Config("tu cuenta de Intercambio"))
        listaConfiguraciones
            .add(Config("Tu Amazon Day"))

        listaConfiguraciones2
            .add(Config("Inicio de sesión y Seguridad"))
        listaConfiguraciones2
            .add(Config("Inicio de sesión con Amazon"))
        listaConfiguraciones2
            .add(Config("Administrar tu cuenta corporativa"))
        listaConfiguraciones2
            .add(Config("Cambiar de cuenta"))
        listaConfiguraciones2
            .add(Config("Direcciones"))
        listaConfiguraciones2
            .add(Config("Administar las identificaciones guardadas"))


        val recyclerView = findViewById<RecyclerView>(R.id.rv_config)
        inicializarRecyclerView(listaConfiguraciones,recyclerView)

        val recyclerView2 = findViewById<RecyclerView>(R.id.rv_config2)
        inicializarRecyclerView(listaConfiguraciones2,recyclerView2)

        val botonInicio = findViewById<ImageButton>(R.id.btn_inicio)
        botonInicio
            .setOnClickListener{
                abrirActividad(MainActivity::class.java)
            }
    }

    fun inicializarRecyclerView(
        lista:ArrayList<Config>,
        recyclerView: RecyclerView
    ){
        val adaptador = RecyclerConfiguracion(
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