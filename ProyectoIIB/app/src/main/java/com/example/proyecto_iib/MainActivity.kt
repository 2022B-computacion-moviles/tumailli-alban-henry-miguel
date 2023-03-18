package com.example.proyecto_iib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val boton_ir_iniciar_sesion=findViewById<Button>(R.id.btn_ir_iniciar_sesion)
        boton_ir_iniciar_sesion.setOnClickListener {
            irActividad(Inicio_Sesion::class.java)

        }
        val ir_registro= findViewById<TextView>(R.id.text_ir_registrarse)
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