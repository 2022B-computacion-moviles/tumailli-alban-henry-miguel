package com.example.examen_ib

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import org.json.JSONArray
import org.json.JSONObject
import java.util.Calendar

class ListComponentes : AppCompatActivity() {
    var jsonArray: JSONArray = JSONArray()
    val componentes: ArrayList<Componente> = arrayListOf<Componente>()
    var idItemSeleccionado = 0
    var compuId = -1
    var componenteEditar = -1

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_componentes)
        val computadorId =intent.getIntExtra("id",-1)
        compuId = computadorId

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
        val computador = findViewById<TextView>(R.id.tv_computador)

        val lector:LeerFichero = LeerFichero(applicationContext)
        val jsonArray: JSONArray = JSONArray(lector.leer())
        computador.setText(jsonArray.getJSONObject(computadorId).get("nombre").toString())

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
                crearComponente(computadorId,nombre,precio,fecha,marca,si,no)
            }

        val botonComputadores = findViewById<Button>(R.id.btn_computadores)
        botonComputadores
            .setOnClickListener{
               abrirActividad(MainActivity::class.java)
            }
    }

    fun crearComponente(
        computadorId: Int,
        nombre:EditText,
        precio:EditText,
        fecha:EditText,
        marca:EditText,
        si:RadioButton,
        no:RadioButton
    ){
        val lector:LeerFichero = LeerFichero(applicationContext)
        val jsonArray: JSONArray = JSONArray(lector.leer())

        val jsonObject:JSONObject= JSONObject()

        jsonObject.put("nombre",nombre.text)
        jsonObject.put("precio",precio.text)
        jsonObject.put("fecha",fecha.text)
        jsonObject.put("marca",marca.text)

        if(no.isChecked)
            jsonObject.put("nuevo",false)
        else if(si.isChecked)
            jsonObject.put("nuevo",true)

        if(componenteEditar>=0){
            jsonArray.getJSONObject(computadorId).getJSONArray("componentes").put(componenteEditar,jsonObject)
        }
        else{
            jsonArray.getJSONObject(computadorId).getJSONArray("componentes").put(jsonObject)
        }

        val escritor= GuardarFichero(applicationContext,jsonArray.toString())
        escritor.escribir()
        Log.i("A Guardar", jsonArray.toString())
        abrirActividadConParametos(ListComponentes::class.java,compuId)
    }

    fun editar(
        id:Int
    ){
        val lector:LeerFichero = LeerFichero(applicationContext)
        val jsonArray: JSONArray = JSONArray(lector.leer())

        var objeto = jsonArray.getJSONObject(compuId).getJSONArray("componentes").getJSONObject(id)
        val nombre = findViewById<EditText>(R.id.tf_componente_nombre)
        val precio = findViewById<EditText>(R.id.tf_componente_precio)
        val fecha = findViewById<EditText>(R.id.tf_componente_fecha)
        val marca = findViewById<EditText>(R.id.tf_componente_marca)
        val si = findViewById<RadioButton>(R.id.rb_si)
        val no = findViewById<RadioButton>(R.id.rb_no)
        nombre.setText(objeto.get("nombre").toString())
        precio.setText(objeto.get("precio").toString())
        marca.setText(objeto.get("marca").toString())
        fecha.setText(objeto.get("fecha").toString())
        if(objeto.get("nuevo") as Boolean)
            si.setChecked(true)
        else
            no.setChecked(true)

        componenteEditar = id

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        //Lenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_componente,menu)
        //Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_comp_editar->{
                "${idItemSeleccionado}"
                editar(idItemSeleccionado)
                Log.i("Seleccionado", idItemSeleccionado.toString())
                return true
            }
            R.id.mi_comp_eliminar->{
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
                val lector:LeerFichero = LeerFichero(applicationContext)
                jsonArray = JSONArray(lector.leer())
                jsonArray.getJSONObject(compuId).getJSONArray("componentes").remove(id)
                val escritor= GuardarFichero(applicationContext,jsonArray.toString())
                escritor.escribir()
                abrirActividadConParametos(ListComponentes::class.java,compuId)
            }
        )

        builder.setNegativeButton(
            "Cancelar",
            null
        )

        val dialogo = builder.create()
        dialogo.show()
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

    private fun abrirActividad(
        clase: Class<*>,
    ) {
        val i = Intent(this, clase)
        startActivity(i);
    }

    fun abrirActividadConParametos(
        clase: Class<*>,
        id: Int
    ){
        val intentExplicito = Intent(this,clase)
        intentExplicito.putExtra("id",id)
        startActivity(intentExplicito);
    }
}