package com.example.proyecto_iib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class Perfil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        val idUsuario =intent.getStringExtra("id")
        val tipo =intent.getStringExtra("tipo")
        val nombre =intent.getStringExtra("nombre")

        val agendar = findViewById<ImageButton>(R.id.btn_agendar)
        val cancelar = findViewById<ImageButton>(R.id.btn_cancelar)
        val home = findViewById<ImageButton>(R.id.btn_home)
        val pacientes = findViewById<ImageButton>(R.id.btn_pacientes)

        if(tipo == "pacientes"){
            agendar
                .setOnClickListener{
                    abrirActividad(Agendar::class.java,idUsuario.toString(),tipo.toString(),nombre.toString())
                }

            cancelar
                .setOnClickListener{
                    abrirActividad(Cancelar::class.java,idUsuario.toString(),tipo.toString(),nombre.toString())
                }
            home
                .setOnClickListener{
                    abrirActividad(Home::class.java,idUsuario.toString(),tipo.toString(),nombre.toString())
                }

        }else{
            pacientes
                .setOnClickListener{
                    abrirActividad(Pacientes::class.java,idUsuario.toString(),tipo.toString(),nombre.toString())
                }
        }

    }

    private fun abrirActividad(
        clase: Class<*>,
        id: String,
        tipo:String,
        nombre:String
    ) {
        val i = Intent(this, clase)
        i.putExtra("id", id)
        i.putExtra("tipo",tipo)
        i.putExtra("nombre",nombre)
        startActivity(i);
    }
}