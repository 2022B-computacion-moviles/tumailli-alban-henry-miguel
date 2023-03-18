package com.example.proyecto_iib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Spinner
import com.google.firebase.firestore.FirebaseFirestore

class Registro_Odontologo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_odontologo)

        val listaEspecialidad = ArrayList<Especialidad>()
        var especialidadEncontrada=Especialidad(0, "Seleccione la Especialidad")
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection("especialidades").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    especialidadEncontrada.cod_especialidad=document.id.toInt()
                    especialidadEncontrada.nombre_especialidad= document.data?.get("nombre").toString()
                    listaEspecialidad.add(especialidadEncontrada)
                    especialidadEncontrada=Especialidad(0, "Seleccione la Especialidad")
                }
                val spinnerEspecialidades = findViewById<Spinner>(R.id.spinner_especialidades)

                val adaptador = Adaptador_Especialidades(this,listaEspecialidad)
                spinnerEspecialidades.adapter = adaptador
            }


        val spinnerHoraInicio=findViewById<Spinner>(R.id.spinner_hora_inicio)
        val nombres = arrayOf("07:00","08:00", "09:00", "10:00", "11:00", "12:00")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, nombres)
        spinnerHoraInicio.adapter = adapter


        val spinnerHoraFinal=findViewById<Spinner>(R.id.spinner_hora_final)
        val nombres1 = arrayOf("14:00", "15:00", "16:00", "17:00", "18:00","19:00","20:00")
        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, nombres1)
        spinnerHoraFinal.adapter = adapter1





    }




}