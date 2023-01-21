package com.example.examen_ib

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import org.json.JSONArray
import org.json.JSONObject

class CrearComputador : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_computador)

        val boton = findViewById<Button>(R.id.btn_computador_guardar)
        val id = intent.getIntExtra("id",-1)
        Log.i("intent-epn",id.toString())

        val nombre = findViewById<EditText>(R.id.tf_computador_nombre)
        val precio = findViewById<EditText>(R.id.tf_computador_precio)
        val stock = findViewById<EditText>(R.id.tf_computador_stock)
        val marca = findViewById<EditText>(R.id.tf_computador_marca)
        val si = findViewById<RadioButton>(R.id.rg_computador_si)
        val no = findViewById<RadioButton>(R.id.rg_computador_no)

        if(id>=0){
            editar(id,nombre, precio, stock, marca, si, no)
        }

        boton
            .setOnClickListener{guardar(nombre,precio,stock, marca, si, no)}
    }

    fun guardar(
        nombre:EditText,
        precio:EditText,
        stock:EditText,
        marca:EditText,
        si:RadioButton,
        no:RadioButton
    ){
        val lector:LeerFichero = LeerFichero(applicationContext)
        val jsonArray: JSONArray = JSONArray(lector.leer())

        val id = intent.getIntExtra("id",-1)

        val jsonObject:JSONObject= JSONObject()

        jsonObject.put("nombre",nombre.text)
        jsonObject.put("precio",precio.text)
        jsonObject.put("stock",stock.text)
        jsonObject.put("marca",marca.text)
        if(no.isChecked)
            jsonObject.put("nuevo",false)
        else if(si.isChecked)
            jsonObject.put("nuevo",true)

        if(id>=0)
            jsonArray.put(id,jsonObject)
        else
            jsonArray.put(jsonObject)

        val escritor= GuardarFichero(applicationContext,jsonArray.toString())
        escritor.escribir()
        Log.i("A Guardar", jsonArray.toString())

        abrirActividad(MainActivity::class.java)
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
        val lector:LeerFichero = LeerFichero(applicationContext)
        val jsonArray: JSONArray = JSONArray(lector.leer())

        var objeto = jsonArray.getJSONObject(id)
        nombre.setText(objeto.get("nombre").toString())
        precio.setText(objeto.get("precio").toString())
        marca.setText(objeto.get("marca").toString())
        stock.setText(objeto.get("stock").toString())
        if(objeto.get("nuevo") as Boolean)
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