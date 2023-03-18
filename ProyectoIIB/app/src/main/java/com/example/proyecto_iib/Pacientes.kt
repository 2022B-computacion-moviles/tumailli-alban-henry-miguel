package com.example.proyecto_iib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class Pacientes : AppCompatActivity() {
    private var listaPacientes: ArrayList<Paciente> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pacientes)

        listaPacientes.add(Paciente("Henry Tumailli","02/02/2023"))
        listaPacientes.add(Paciente("Henry Tumailli","02/02/2023"))
        listaPacientes.add(Paciente("Henry Tumaill","02/02/2023"))

        val recyclerView = findViewById<RecyclerView>(R.id.rv_pacientes)
        inicializarRecyclerView(listaPacientes,recyclerView)
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


}