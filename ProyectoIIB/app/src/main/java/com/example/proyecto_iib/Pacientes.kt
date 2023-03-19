package com.example.proyecto_iib

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class Pacientes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pacientes)

        val bundle=intent.extras
        val nombre_Odo= bundle?.getString("nombre_us").toString()
        val correo= bundle?.getString("correo").toString()

        val msjBienvenida= findViewById<TextView>(R.id.msj_bienvenida_odo)
        msjBienvenida.text="Bienvenido, ${nombre_Odo}"

        //Guardado de Datos

        val prefs=getSharedPreferences(getString(R.string.prefs_file),Context.MODE_PRIVATE).edit()
        prefs.putString("correo",correo)
        prefs.putString("nombre",nombre_Odo)
        prefs.apply()

        //logaut
        val btn_logaut=findViewById<Button>(R.id.logaut_odo)
        btn_logaut.setOnClickListener{

            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
            val editor =prefs.edit()
            editor.clear()
            editor.apply()

            FirebaseAuth.getInstance().signOut()
            onBackPressed()

            irActividad(MainActivity::class.java)

        }
    }

    override fun onBackPressed() {
        // Dejar vacío para deshabilitar el botón de retroceso
    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)

    }
}