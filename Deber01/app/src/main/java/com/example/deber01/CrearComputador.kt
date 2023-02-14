package com.example.deber01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import org.json.JSONArray

class CrearComputador : AppCompatActivity() {
    lateinit var computadorDBHelper:miSQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_computador)
        val id = intent.getIntExtra("id",-1)
        computadorDBHelper = miSQLiteHelper(this)

        val botonGuardar = findViewById<Button>(R.id.btn_computador_guardar)

        val nombre = findViewById<EditText>(R.id.tf_computador_nombre)
        val precio = findViewById<EditText>(R.id.tf_computador_precio)
        val stock = findViewById<EditText>(R.id.tf_computador_stock)
        val marca = findViewById<EditText>(R.id.tf_computador_marca)
        val si = findViewById<RadioButton>(R.id.rg_computador_si)
        val no = findViewById<RadioButton>(R.id.rg_computador_no)
        var nuevo = 0

        if(id>=0){
            editar(id,nombre, precio, stock, marca, si, no)
        }

        botonGuardar
            .setOnClickListener{
                if(si.isChecked)
                    nuevo = 1
                else nuevo = 0

                if(id>=0){
                    computadorDBHelper.actualizarComputador(
                        nombre.text.toString(),
                        precio.text.toString().toDouble(),
                        stock.text.toString().toInt(),
                        marca.text.toString(),
                        nuevo,
                        id
                    )
                }else{
                    computadorDBHelper.guardarComputador(
                        nombre.text.toString(),
                        precio.text.toString().toDouble(),
                        stock.text.toString().toInt(),
                        marca.text.toString(),
                        nuevo
                    )
                }

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
        no:RadioButton
    ){
        computadorDBHelper = miSQLiteHelper(this)
        var computador = computadorDBHelper.consultarComputadoraPorId(id)

        nombre.setText(computador.nombre)
        precio.setText(computador.precio.toString())
        marca.setText(computador.marca)
        stock.setText(computador.stock.toString())
        if(computador.nuevo == 1)
            si.setChecked(true)
        else
            no.setChecked(true)
    }

    private fun abrirActividad(
        clase: Class<*>,
    ) {
        val i = Intent(this, clase)
        startActivity(i);
    }

}