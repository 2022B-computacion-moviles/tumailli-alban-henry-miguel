package com.example.examen_iib

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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class ListaComponentes : AppCompatActivity() {

    val db: FirebaseFirestore = Firebase.firestore
    var componentes: ArrayList<Componente> = arrayListOf<Componente>()
    var idItemSeleccionado = 0
    var compuId = -1
    var componenteEditar = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_componentes)

        val computadorId =intent.getIntExtra("id",-1)
        compuId = computadorId

        var nombreCompu = ""

        db.collection("computadores")
            .document(compuId.toString())
            .get()
            .addOnSuccessListener{ computador ->
                nombreCompu = computador["nombre"].toString()

            }

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
        val id = findViewById<EditText>(R.id.tf_componente_id)


        computador.setText(nombreCompu)


        botonFecha.setOnClickListener{
            val dpd = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener{ _: DatePicker, mYear:Int, mMonth:Int, mDay:Int->
                val mes = mMonth+1
                fecha.setText("$mDay/$mes/$mYear")
            },year,month,day)
            dpd.show()
        }

        db.collection("componentes")
            .whereEqualTo("idComputador",compuId)
            .get()
            .addOnSuccessListener{resultado ->
                for (componente in resultado){
                    componentes.add(
                        Componente(
                            componente.id.toInt(),
                            componente["idComputador"].toString().toInt(),
                            componente["nombre"].toString(),
                            componente["precio"].toString().toDouble(),
                            componente["fecha"].toString(),
                            componente["marca"].toString(),
                            componente["nuevo"].toString().toBoolean())
                    )
                }

                val adaptador = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    componentes
                )

                listView.adapter = adaptador
                adaptador.notifyDataSetChanged()
                registerForContextMenu(listView)
            }
            .addOnFailureListener{ _ ->
                Log.i("ERROR F","No se ha podido conectar")
            }

        val botonAddComponente = findViewById<Button>(R.id.btn_componente_guardar)
        botonAddComponente
            .setOnClickListener{
                crearComponente(id,computadorId,nombre,precio,fecha,marca,si,no)
            }

        val botonComputadores = findViewById<Button>(R.id.btn_computadores)
        botonComputadores
            .setOnClickListener{
                abrirActividad(MainActivity::class.java)
            }
    }

    fun crearComponente(
        id: EditText,
        computadorId: Int,
        nombre: EditText,
        precio: EditText,
        fecha: EditText,
        marca: EditText,
        si: RadioButton,
        no: RadioButton
    ){

        var nuevo = false
        if(no.isChecked)
            nuevo = false
        else if(si.isChecked)
            nuevo = true

        val dato = hashMapOf(
            "nombre" to nombre.text.toString(),
            "precio" to precio.text.toString().toDouble(),
            "marca" to marca.text.toString(),
            "fecha" to fecha.text.toString(),
            "nuevo" to nuevo,
            "idComputador" to computadorId.toString().toInt()
        )

        db.collection("componentes")
            .document(id.text.toString())
            .set(dato)

        abrirActividadConParametos(ListaComponentes::class.java,compuId)
    }

    fun editar(
        id:Int
    ){
        val nombre = findViewById<EditText>(R.id.tf_componente_nombre)
        val precio = findViewById<EditText>(R.id.tf_componente_precio)
        val fecha = findViewById<EditText>(R.id.tf_componente_fecha)
        val marca = findViewById<EditText>(R.id.tf_componente_marca)
        val si = findViewById<RadioButton>(R.id.rb_si)
        val no = findViewById<RadioButton>(R.id.rb_no)
        val id_et = findViewById<EditText>(R.id.tf_componente_id)

        db.collection("componentes")
            .document(id.toString())
            .get()
            .addOnSuccessListener { computador ->
                nombre.setText(computador["nombre"].toString())
                precio.setText(computador["precio"].toString())
                marca.setText(computador["marca"].toString())
                fecha.setText(computador["fecha"].toString())
                id_et.setText(computador.id)
                if(computador["nuevo"].toString().toBoolean())
                    si.setChecked(true)
                else
                    no.setChecked(true)
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
        inflater.inflate(R.menu.menu_componente,menu)
        //Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = componentes[id].id
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
                db.collection("componentes")
                    .document(idItemSeleccionado.toString())
                    .delete()
                abrirActividadConParametos(ListaComponentes::class.java,compuId)
            }
        )

        builder.setNegativeButton(
            "Cancelar",
            null
        )

        val dialogo = builder.create()
        dialogo.show()
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