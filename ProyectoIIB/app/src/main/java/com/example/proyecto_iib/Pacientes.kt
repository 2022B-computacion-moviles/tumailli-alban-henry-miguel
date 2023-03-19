package com.example.proyecto_iib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView

class Pacientes : AppCompatActivity() {
    private var listaPacientes: ArrayList<Paciente> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pacientes)

        val agendar = findViewById<ImageButton>(R.id.btn_agendar)
        val cancelar = findViewById<ImageButton>(R.id.btn_cancelar)
        val home = findViewById<ImageButton>(R.id.btn_home)
        val perfil = findViewById<ImageButton>(R.id.btn_perfil)

        listaPacientes.add(Paciente("Henry Tumailli","02/02/2023"))
        listaPacientes.add(Paciente("Henry Tumailli","02/02/2023"))
        listaPacientes.add(Paciente("Henry Tumaill","02/02/2023"))

        val recyclerView = findViewById<RecyclerView>(R.id.rv_pacientes)
        inicializarRecyclerView(listaPacientes,recyclerView)

        agendar
            .setOnClickListener{
                abrirActividad(Agendar::class.java)
            }

        cancelar
            .setOnClickListener{
                abrirActividad(Cancelar::class.java)
            }

        home
            .setOnClickListener{
                abrirActividad(Home::class.java)
            }

        perfil
            .setOnClickListener{
                abrirActividad(Perfil::class.java)
            }
    }

    fun inicializarRecyclerView(
        lista:ArrayList<Paciente>,
        recyclerView: RecyclerView
    ){
        val adaptador = RecyclerPacientes(
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