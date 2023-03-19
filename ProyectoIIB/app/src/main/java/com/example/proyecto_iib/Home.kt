package com.example.proyecto_iib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Home : AppCompatActivity() {

    var citas: ArrayList<Cita> = arrayListOf<Cita>()
    val db: FirebaseFirestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val idPaciente = "MlSJXSSTF0iAVtiigSdE"
        val idOdontologo = "MlSJXSSTF0iAVtiigSdE"

        /*val idPaciente =intent.getStringExtra("idPaciente")
        val idOdontologo =intent.getStringExtra("idOdontologo")*/

        val agendar = findViewById<ImageButton>(R.id.btn_agendar)
        val cancelar = findViewById<ImageButton>(R.id.btn_cancelar)
        val pacientes = findViewById<ImageButton>(R.id.btn_pacientes)
        val perfil = findViewById<ImageButton>(R.id.btn_perfil)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_home)
        val sin_cita = findViewById<TextView>(R.id.sin_cita)

        //Conusltad de Citas paciente

        if(idPaciente != null){
            db.collection("citas")
                .whereEqualTo("idPaciente",idPaciente)
                .get()
                .addOnSuccessListener{ resultado ->
                    for ( cita  in resultado){
                        var nombreProd="Procedimiento"
                        db.collection("procedimientos")
                            .document(cita["idProcedimiento"].toString())
                            .get()
                            .addOnSuccessListener { doc ->
                                if(doc != null){
                                    citas.add(
                                        Cita(
                                            cita["idPaciente"].toString(),
                                            cita["idOdontologo"].toString(),
                                            cita["idProcedimiento"].toString(),
                                            doc["nombre"].toString(),
                                            cita["hora"].toString(),
                                            cita["fecha"].toString(),
                                        )
                                    )
                                }
                                inicializarRecyclerView(citas,recyclerView)
                            }
                    }
                }
        }

        //Conusltad de Citas odontologo

        if(idOdontologo != null){
            db.collection("citas")
                .whereEqualTo("idOdontologo",idOdontologo)
                .get()
                .addOnSuccessListener{ resultado ->
                    for ( cita  in resultado){
                        var nombreProd="Procedimiento"
                        db.collection("procedimientos")
                            .document(cita["idProcedimiento"].toString())
                            .get()
                            .addOnSuccessListener { doc ->
                                if(doc != null){
                                    citas.add(
                                        Cita(
                                            cita["idPaciente"].toString(),
                                            cita["idOdontologo"].toString(),
                                            cita["idProcedimiento"].toString(),
                                            doc["nombre"].toString(),
                                            cita["hora"].toString(),
                                            cita["fecha"].toString(),
                                        )
                                    )
                                }
                                inicializarRecyclerView(citas,recyclerView)
                            }
                    }
                }
        }


        //////////POSIBLE
        /*if(citas.size == 0)
            sin_cita.setText("No tiene citas programadas")*/



        agendar
            .setOnClickListener{
                abrirActividad(Agendar::class.java)
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
    }

    fun inicializarRecyclerView(
        lista:ArrayList<Cita>,
        recyclerView: RecyclerView
    ){
        val adaptador = RecyclerCitas(
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

