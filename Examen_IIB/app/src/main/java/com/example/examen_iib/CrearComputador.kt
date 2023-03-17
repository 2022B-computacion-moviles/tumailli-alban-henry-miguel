package com.example.examen_iib

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.FirebaseFirestore

class CrearComputador : AppCompatActivity() {

    val db = Firebase.firestore
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_computador)
        var id = intent.getIntExtra("id",-1)

        val botonGuardar = findViewById<Button>(R.id.btn_computador_guardar)

        val nombre_et = findViewById<EditText>(R.id.tf_computador_nombre)
        val precio_et = findViewById<EditText>(R.id.tf_computador_precio)
        val stock_et = findViewById<EditText>(R.id.tf_computador_stock)
        val marca_et = findViewById<EditText>(R.id.tf_computador_marca)
        val si = findViewById<RadioButton>(R.id.rg_computador_si)
        val no = findViewById<RadioButton>(R.id.rg_computador_no)
        val id_et = findViewById<EditText>(R.id.tf_computador_id)
        var nuevo = false

        if(id>=0){
            editar(id,nombre_et, precio_et, stock_et, marca_et, si, no,id_et)
        }

        botonGuardar
            .setOnClickListener{
                if(si.isChecked)
                    nuevo = true
                else nuevo = false

                val dato = hashMapOf(
                    "nombre" to nombre_et.text.toString(),
                    "precio" to precio_et.text.toString().toDouble(),
                    "marca" to marca_et.text.toString(),
                    "stock" to stock_et.text.toString().toInt(),
                    "nuevo" to nuevo
                )

                if(id==-1){
                    id = id_et.text.toString().toInt()
                }

                db.collection("computadores")
                    .document(id.toString())
                    .set(dato)

                abrirActividad(MainActivity::class.java)
            }
    }

    fun editar(
        id:Int,
        nombre:EditText,
        precio:EditText,
        stock:EditText,
        marca:EditText,
        si:RadioButton,
        no:RadioButton,
        id_et: EditText
    ){
        db.collection("computadores")
            .document(id.toString())
            .get()
            .addOnSuccessListener { computador ->
                nombre.setText(computador["nombre"].toString())
                precio.setText(computador["precio"].toString())
                marca.setText(computador["marca"].toString())
                stock.setText(computador["stock"].toString())
                id_et.setText(computador.id)
                if(computador["nuevo"].toString().toBoolean())
                    si.setChecked(true)
                else
                    no.setChecked(true)
            }
    }

    private fun abrirActividad(
        clase: Class<*>,
    ) {
        val i = Intent(this, clase)
        startActivity(i);
    }

}