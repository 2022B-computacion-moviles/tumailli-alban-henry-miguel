package com.example.examen_ib
import android.content.Context
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.InputStreamReader


class LeerFichero(
    var context: Context
) {
    fun leer(): String{
        val fichero = BufferedReader( InputStreamReader(context.openFileInput("datos.txt")) )
        val texto = fichero.use { it.readText() }
        fichero.close()
        return texto
    }
}