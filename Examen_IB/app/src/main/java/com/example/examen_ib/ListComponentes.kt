package com.example.examen_ib

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.Calendar

class ListComponentes : AppCompatActivity() {
    var jsonArray: JSONArray = JSONArray()
    val componentes: ArrayList<Componente> = arrayListOf<Componente>()
    var idItemSeleccionado = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_componentes)
        val computadorId =intent.getIntExtra("id",-1)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val botonFecha = findViewById<Button>(R.id.btn_fecha)

        val listView = findViewById<ListView>(R.id.lv_componentes)
        val nombre = findViewById<EditText>(R.id.tf_componente_nombre)
        val precio = findViewById<EditText>(R.id.tf_componente_precio)
        val fecha = findViewById<EditText>(R.id.tf_componente_fecha)
        val marca = findViewById<EditText>(R.id.tf_componente_marca)
        val si = findViewById<RadioButton>(R.id.rb_si)
        val no = findViewById<RadioButton>(R.id.rb_no)

        botonFecha.setOnClickListener{
            val dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener{ _:DatePicker, mYear:Int, mMonth:Int, mDay:Int->
                val mes = mMonth+1
                fecha.setText("$mDay/$mes/$mYear")
            },year,month,day)
            dpd.show()
        }

        leerFichero(computadorId)

        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            componentes

        )

        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)

        val botonAddComponente = findViewById<Button>(R.id.btn_componente_guardar)
        botonAddComponente
            .setOnClickListener{
                crearComponente(computadorId,nombre,precio,fecha,marca,si,no,-1)
            }


    }

    fun crearComponente(
        computadorId: Int,
        nombre:EditText,
        precio:EditText,
        fecha:EditText,
        marca:EditText,
        si:RadioButton,
        no:RadioButton,
        id: Int
    ){
        val lector:LeerFichero = LeerFichero(applicationContext)
        val jsonArray: JSONArray = JSONArray(lector.leer())
        val seleccionado = jsonArray.getJSONObject(computadorId)
        val arregloComponentes = seleccionado.getJSONArray("componentes")

        println("Componentes $arregloComponentes")

        val jsonObject:JSONObject= JSONObject()

        jsonObject.put("nombre",nombre.text)
        jsonObject.put("precio",precio.text)
        jsonObject.put("fecha",fecha.text)
        jsonObject.put("marca",marca.text)

        if(no.isChecked)
            jsonObject.put("nuevo",false)
        else if(si.isChecked)
            jsonObject.put("nuevo",true)

        if(id>=0){

            jsonArray.getJSONObject(computadorId).getJSONArray("componentes").put(id,jsonObject)
        }
        else{
            //println("Objeto $jsonObject")
            //arregloComponentes.put(jsonObject)
            //println("Componentes $arregloComponentes")
            jsonArray.getJSONObject(computadorId).getJSONArray("componentes").put(jsonObject)
        }

        Log.i("Componentes reemplazo", jsonArray.toString())

        val escritor= GuardarFichero(applicationContext,jsonArray.toString())
        escritor.escribir()
        Log.i("A Guardar", jsonArray.toString())
    }

    fun leerFichero(
        computadorId: Int
    ){
        val lector:LeerFichero = LeerFichero(applicationContext)
        jsonArray = JSONArray(lector.leer())
        val seleccionado = jsonArray.getJSONObject(computadorId)
        val arregloComponentes = seleccionado.getJSONArray("componentes")
        for(i in 0 until arregloComponentes.length()){
            val jsonObject: JSONObject = arregloComponentes.getJSONObject(i)
            componentes.add(
                Componente(
                    jsonObject.getString("nombre"),
                    jsonObject.getString("precio").toFloat(),
                    jsonObject.getString("fecha"),
                    jsonObject.getString("marca"),
                    jsonObject.getBoolean("nuevo")
                )
            )
        }
    }
}