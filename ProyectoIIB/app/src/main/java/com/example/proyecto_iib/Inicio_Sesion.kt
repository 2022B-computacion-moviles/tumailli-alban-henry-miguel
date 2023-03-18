package com.example.proyecto_iib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Inicio_Sesion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_sesion)

        val ir_registro= findViewById<TextView>(R.id.text_ir_registrarse2)
        ir_registro.setOnClickListener {

            irActividad(Registro_Paciente::class.java)

        }
    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)

    }




}