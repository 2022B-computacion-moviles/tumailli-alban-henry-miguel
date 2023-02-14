package com.example.deber01

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
import java.util.*
import kotlin.collections.ArrayList

class ListaComponentes : AppCompatActivity() {

    lateinit var componenteDBHelper:miSQLiteHelperComponente
    var componentes: ArrayList<Componente> = arrayListOf<Componente>()
    var idItemSeleccionado = 0
    var compuId = -1
    var componenteEditar = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_componentes)
        componenteDBHelper = miSQLiteHelperComponente(this)
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

        lateinit var computadorDBHelper:miSQLiteHelper
        computadorDBHelper = miSQLiteHelper(this)
        computador.setText(computadorDBHelper.consultarComputadoraPorId(computadorId).nombre)

        botonFecha.setOnClickListener{
            val dpd = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener{ _: DatePicker, mYear:Int, mMonth:Int, mDay:Int->
                val mes = mMonth+1
                fecha.setText("$mDay/$mes/$mYear")
            },year,month,day)
            dpd.show()
        }

        componentes = componenteDBHelper.consultarComponentes(computadorId)

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
        nombre: EditText,
        precio: EditText,
        fecha: EditText,
        marca: EditText,
        si: RadioButton,
        no: RadioButton
    ){
        componenteDBHelper = miSQLiteHelperComponente(this)
        var nuevo = 0
        if(no.isChecked)
            nuevo = 0
        else if(si.isChecked)
            nuevo = 1

        if(componenteEditar >= 0){
            componenteDBHelper.actualizarComponente(
                nombre.text.toString(),
                precio.text.toString().toDouble(),
                fecha.text.toString(),
                marca.text.toString(),
                nuevo,
                componenteEditar,
            )

        }else{
            componenteDBHelper.guardarComponente(
                computadorId,
                nombre.text.toString(),
                precio.text.toString().toDouble(),
                fecha.text.toString(),
                marca.text.toString(),
                nuevo
            )
        }

        abrirActividadConParametos(ListaComponentes::class.java,compuId)
    }

    fun editar(
        id:Int
    ){
        componenteDBHelper = miSQLiteHelperComponente(this)
        var componente = componenteDBHelper.consultarComponentePorId(id)
        val nombre = findViewById<EditText>(R.id.tf_componente_nombre)
        val precio = findViewById<EditText>(R.id.tf_componente_precio)
        val fecha = findViewById<EditText>(R.id.tf_componente_fecha)
        val marca = findViewById<EditText>(R.id.tf_componente_marca)
        val si = findViewById<RadioButton>(R.id.rb_si)
        val no = findViewById<RadioButton>(R.id.rb_no)
        nombre.setText(componente.nombre)
        precio.setText(componente.precio.toString())
        marca.setText(componente.marca)
        fecha.setText(componente.fechaFabricacion)
        if(componente.nuevo == 1)
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
                componenteDBHelper = miSQLiteHelperComponente(this)
                componenteDBHelper.eliminarComponente(idItemSeleccionado)
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