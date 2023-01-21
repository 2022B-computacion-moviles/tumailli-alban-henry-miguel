package com.example.examen_ib

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.PrintWriter

class GuardarFichero(
    var context: Context,
    var textoAGuardar:String
) {
    fun escribir(){
        try {
            val stream: FileOutputStream = context.openFileOutput("datos.txt",Context.MODE_PRIVATE)
            val fichero = PrintWriter(stream)
            fichero.println(textoAGuardar)
            fichero.close()
        }catch (e: Exception){

        }
    }

}