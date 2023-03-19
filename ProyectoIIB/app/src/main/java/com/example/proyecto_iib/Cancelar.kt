package com.example.proyecto_iib

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Cancelar : AppCompatActivity() {
    var citas: ArrayList<Cita> = arrayListOf<Cita>()
    val db: FirebaseFirestore = Firebase.firestore
    var idItemSeleccionado = 0
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cancelar)

        val idUsuario =intent.getStringExtra("id")
        val tipo =intent.getStringExtra("tipo")
        val nombre =intent.getStringExtra("nombre")

        val home = findViewById<ImageButton>(R.id.btn_home)
        val agendar = findViewById<ImageButton>(R.id.btn_agendar)
        val cancelar = findViewById<ImageButton>(R.id.btn_cancelar)
        val pacientes = findViewById<ImageButton>(R.id.btn_pacientes)
        val perfil = findViewById<ImageButton>(R.id.btn_perfil)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_cancelar_cita)

        //Conusltad de Citas paciente

        db.collection("citas")
            .whereEqualTo("idPaciente",idUsuario)
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

        agendar
            .setOnClickListener{
                abrirActividad(Agendar::class.java)
            }

        home
            .setOnClickListener{
                abrirActividad(Home::class.java)
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

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        //Lenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.cancelar_cita,menu)
        //Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        //idItemSeleccionado = componentes[id].id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_eliminar->{
                abrirDialogo(idItemSeleccionado)
                "${idItemSeleccionado}"
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogo(id :Int){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Está seguro que quiere eliminar este elemento?")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{
                    dialog,
                    which->
                /*
                db.collection("componentes")
                    .document(idItemSeleccionado.toString())
                    .delete()
                abrirActividadConParametos(ListaComponentes::class.java,compuId)*/
            }
        )

        builder.setNegativeButton(
            "Cancelar",
            null
        )

        val dialogo = builder.create()
        dialogo.show()
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