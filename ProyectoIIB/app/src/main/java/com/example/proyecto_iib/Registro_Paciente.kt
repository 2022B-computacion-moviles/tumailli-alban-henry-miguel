package com.example.proyecto_iib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Registro_Paciente : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resgistro_paciente)

        val ir_registro_odontologo= findViewById<TextView>(R.id.text_ir_registro_odont)
        ir_registro_odontologo.setOnClickListener {

            irActividad(Registro_Odontologo::class.java)

        }
    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)

    }

    fun registrarPaciente(){






    }


}