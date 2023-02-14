package com.example.movcomphmta

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts


class MainActivity : AppCompatActivity() {
    val contenidoIntenetExplicito=
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    val data = result.data
                    Log.i("intent-epn","${data?.getStringExtra("nombreModificado")}")
                }
            }
        }

    val contenidoIntenetImplicito=
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    if(result.data!!.data != null){
                        val uri: Uri = result.data!!.data!!
                        val cursor = contentResolver.query(
                            uri,
                            null,
                            null,
                            null,
                            null,
                            null
                        )
                        cursor?.moveToFirst()
                        val indiceTelefono = cursor?.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                        )
                        val telefono = cursor?.getString(
                            indiceTelefono!!
                        )
                        cursor?.close()
                        Log.i("intent-epn","Telefono ${telefono}")
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val botonCicloVida = findViewById<Button>(R.id.btn_ciclo_vida)
        botonCicloVida
            .setOnClickListener{
                irActividad(AClicloVida::class.java)
        }

        val botonListView = findViewById<Button>(R.id.btn_ir_list_view)
        botonListView
            .setOnClickListener{
                irActividad(BListView::class.java)
            }

        val botonIntentImplicito = findViewById<Button>(R.id.btn_ir_intent_implicito)
        botonIntentImplicito
            .setOnClickListener{
                val intentConRespuesta = Intent(
                    Intent.ACTION_PICK,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                )
                contenidoIntenetImplicito.launch(intentConRespuesta)
            }

        val botonIntent = findViewById<Button>(R.id.btn_intent)
        botonIntent
            .setOnClickListener{
                abrirActividadConParametos(CIntentExplicitoParametros::class.java)
            }

        val botonSQLite = findViewById<Button>(R.id.btn_sqlite)
        botonSQLite
            .setOnClickListener{
                irActividad(ECrudEntrenador::class.java)
            }

        val botonRView = findViewById<Button>(R.id.btn_revcycler_view)
        botonRView
            .setOnClickListener{
                irActividad(GRecyclerView::class.java)
            }

        val botonGmaps = findViewById<Button>(R.id.btn_google_maps)
        botonGmaps
            .setOnClickListener{
                irActividad(HGoogleMaps::class.java)
            }
    }

    fun abrirActividadConParametos(
        clase: Class<*>,
    ){
        val intentExplicito = Intent(this,clase)
        intentExplicito.putExtra("nombre","Henry")
        intentExplicito.putExtra("apellido","Tumailli")
        intentExplicito.putExtra("edad","24")
        intentExplicito.putExtra("entrenador",
            BEntrenador(
                1,
                "ash",
                "pueblo paleta"
            )
        )
        contenidoIntenetExplicito.launch(intentExplicito)
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent (this,clase)
        startActivity(intent)
    }
}

