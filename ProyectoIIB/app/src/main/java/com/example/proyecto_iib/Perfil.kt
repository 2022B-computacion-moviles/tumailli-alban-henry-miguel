package com.example.proyecto_iib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class Perfil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        val agendar = findViewById<ImageButton>(R.id.btn_agendar)
        val cancelar = findViewById<ImageButton>(R.id.btn_cancelar)
        val home = findViewById<ImageButton>(R.id.btn_home)
        val pacientes = findViewById<ImageButton>(R.id.btn_pacientes)

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

        pacientes
            .setOnClickListener{
                abrirActividad(Pacientes::class.java)
            }
    }

    private fun abrirActividad(
        clase: Class<*>,
    ) {
        val i = Intent(this, clase)
        startActivity(i);
    }
}