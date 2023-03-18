package com.example.proyecto_iib

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity


class Agendar : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agendar)

        val home = findViewById<ImageButton>(R.id.btn_home)
        val agendar = findViewById<ImageButton>(R.id.btn_agendar)
        val cancelar = findViewById<ImageButton>(R.id.btn_cancelar)
        val pacientes = findViewById<ImageButton>(R.id.btn_pacientes)
        val perfil = findViewById<ImageButton>(R.id.btn_perfil)

        home
            .setOnClickListener{
                abrirActividad(Home::class.java)
            }

        cancelar
            .setOnClickListener{
                abrirActividad(Cancelar::class.java)
            }

        pacientes
            .setOnClickListener{
                abrirActividad(Pacientes::class.java)
            }

        perfil
            .setOnClickListener{
                abrirActividad(Perfil::class.java)
            }

        val items = listOf("Opcion 1","Opcion 2")
        val autoComplete : AutoCompleteTextView = findViewById(R.id.auto_complete_especialista)
        val adapter = ArrayAdapter(this,R.layout.list_item,items)

        autoComplete.setAdapter(adapter)
        autoComplete.onItemClickListener = AdapterView.OnItemClickListener{
            adapterView, view, i, l ->
            val itemSelected = adapterView.getItemAtPosition(i)
        }

    }

    private fun abrirActividad(
        clase: Class<*>,
    ) {
        val i = Intent(this, clase)
        startActivity(i);
    }

}