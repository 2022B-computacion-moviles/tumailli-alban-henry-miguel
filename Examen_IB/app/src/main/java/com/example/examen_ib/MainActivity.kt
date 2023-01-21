package com.example.examen_ib

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import org.json.JSONArray
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    var jsonArray: JSONArray  = JSONArray()
    val computadoras: ArrayList<Computador> = arrayListOf<Computador>()
    var idItemSeleccionado = 0

    val contenidoIntenetExplicito=
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    val data = result.data
                    Log.i("intent-epn","${data?.getStringExtra("nombreModificado")}")
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.lv_computadores)

        leerFichero()

        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            computadoras

        )

        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)

        val botonAddComputador = findViewById<Button>(R.id.btn_add_computador)
        botonAddComputador
            .setOnClickListener{

                abrirActividadConParametos(CrearComputador::class.java,-1)
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
        inflater.inflate(R.menu.menu,menu)
        //Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_editar->{
                "${idItemSeleccionado}"
                abrirActividadConParametos(CrearComputador::class.java,idItemSeleccionado)
                Log.i("Seleccionado", idItemSeleccionado.toString())
                return true
            }
            R.id.mi_eliminar->{
                abrirDialogo(idItemSeleccionado)
                "${idItemSeleccionado}"
                return true
            }
            R.id.mi_componentes->{
                "${idItemSeleccionado}"
                abrirActividadConParametos(ListComponentes::class.java,idItemSeleccionado)
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
                    jsonArray.remove(id)
                    val escritor= GuardarFichero(applicationContext,jsonArray.toString())
                    escritor.escribir()
                    abrirActividad(MainActivity::class.java)
            }
        )

        builder.setNegativeButton(
            "Cancelar",
            null
        )

        val dialogo = builder.create()
        dialogo.show()
    }

    fun abrirActividadConParametos(
        clase: Class<*>,
        id: Int
    ){
        val intentExplicito = Intent(this,clase)
        intentExplicito.putExtra("id",id)
        contenidoIntenetExplicito.launch(intentExplicito)
    }

    fun leerFichero(){
        val lector:LeerFichero = LeerFichero(applicationContext)
        jsonArray = JSONArray(lector.leer())
        for(i in 0 until jsonArray.length()){
            val jsonObject:JSONObject = jsonArray.getJSONObject(i)
            computadoras.add(
                Computador(
                    jsonObject.getString("nombre"),
                    jsonObject.getString("precio").toFloat(),
                    jsonObject.getString("stock").toInt(),
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

    fun mostrarSnackBar(texto:String){
        Snackbar.make(findViewById(R.id.ly_main),
            texto, Snackbar.LENGTH_LONG)
            .setAction("Action",null).show()
    }
}