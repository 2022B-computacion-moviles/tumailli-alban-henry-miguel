package com.example.deber01

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class miSQLiteHelper(context: Context) : SQLiteOpenHelper(
    context, "deber", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val ordenCreacion =
            """
                CREATE TABLE COMPUTADOR(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre TEXT,
                    precio REAL,
                    stock INTEGER,
                    marca TEXT,
                    nuevo INTEGER
                )
            """
        db!!.execSQL(ordenCreacion)
    }

    override fun onUpgrade(db: SQLiteDatabase?,
               oldVersion: Int, newVersion: Int) {
        val ordenBorrado = ""
        db!!.execSQL(ordenBorrado)
        onCreate(db)
    }

    fun guardarComputador(
        nombre:String,
        precio: Double,
        stock:Int,
        marca:String,
        nuevo: Int
    ):Boolean{
        Log.i("Crear Com","Entre")
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre",nombre)
        valoresAGuardar.put("precio",precio)
        valoresAGuardar.put("stock",stock)
        valoresAGuardar.put("marca",marca)
        valoresAGuardar.put("nuevo",nuevo)

        val resultadoGuardar = basedatosEscritura
            .insert(
                "COMPUTADOR", // Tabla
                null, //
                valoresAGuardar // valores
            )
        basedatosEscritura.close()
        return  if(resultadoGuardar.toInt() == -1) false else true
    }

    fun consultarComputadoras():ArrayList<Computador>{
        val baseDatosLectura = readableDatabase
        val scriptConsultarUsuario = "SELECT * FROM  COMPUTADOR"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarUsuario,
            arrayOf()
        )
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val computador = Computador(0,"",0.0,0,"",0)
        val listaComputadores = arrayListOf<Computador>()
        do{
            listaComputadores
                .add(Computador(resultadoConsultaLectura.getInt(0),
                    resultadoConsultaLectura.getString(1),
                    resultadoConsultaLectura.getDouble(2),
                    resultadoConsultaLectura.getInt(3),
                    resultadoConsultaLectura.getString(4),
                    resultadoConsultaLectura.getInt(5)
                ))
        }while (resultadoConsultaLectura.moveToNext())
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return listaComputadores
    }

    fun actualizarComputador(
        nombre:String,
        precio: Double,
        stock:Int,
        marca:String,
        nuevo: Int,
        idActualizar:Int
    ): Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre",nombre)
        valoresAActualizar.put("precio",precio)
        valoresAActualizar.put("stock",stock)
        valoresAActualizar.put("marca",marca)
        valoresAActualizar.put("nuevo",nuevo)
        val resultadoActualizacion = conexionEscritura
            .update(
                "COMPUTADOR", //  tabla
                valoresAActualizar, // valores a actualizar
                "id=?", //clausula where
                arrayOf(
                    idActualizar.toString()
                ) // Parametros de la clausula where
            )
        conexionEscritura.close()
        return if (resultadoActualizacion == -1) false else true
    }

    fun consultarComputadoraPorId(id:Int):Computador{
        val baseDatosLectura = readableDatabase
        val scriptConsultarUsuario = "SELECT * FROM COMPUTADOR WHERE ID = ?"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarUsuario,
            arrayOf(
                id.toString()
            )
        )
        val existeComputador = resultadoConsultaLectura.moveToFirst()
        val computadorEncontrado = Computador(0,"",0.0,0,"",0)
        //Logica obtener usuario
        do{
            val id = resultadoConsultaLectura.getInt(0)
            val nombre = resultadoConsultaLectura.getString(1)
            val precio = resultadoConsultaLectura.getDouble(2)
            val stock = resultadoConsultaLectura.getInt(3)
            val marca = resultadoConsultaLectura.getString(4)
            val nuevo = resultadoConsultaLectura.getInt(5)

            if(id != null){
                computadorEncontrado.id=id
                computadorEncontrado.nombre= nombre
                computadorEncontrado.precio = precio
                computadorEncontrado.stock = stock
                computadorEncontrado.marca = marca
                computadorEncontrado.nuevo = nuevo
            }
        }while (resultadoConsultaLectura.moveToNext())
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return computadorEncontrado
    }

    fun eliminarComputador(id:Int):Boolean{
        val conexionEscritura = writableDatabase
        val resultadoEliminado = conexionEscritura
            .delete(
                "COMPUTADOR", //tabla
                "id=?", // id=? and nombre=? Where (podemos amdnar parametros en orden)
                arrayOf(
                    id.toString()
                )
            )
        conexionEscritura.close()
        return  if(resultadoEliminado.toInt() == -1) false else true
    }


}