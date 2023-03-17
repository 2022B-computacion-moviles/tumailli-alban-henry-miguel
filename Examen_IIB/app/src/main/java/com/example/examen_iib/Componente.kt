package com.example.examen_iib

class Componente(
    var id:Int,
    var idComputador: Int,
    var nombre:String,
    var precio:Double,
    var fechaFabricacion: String,
    var marca:String,
    var nuevo:Boolean

) {
    override fun toString(): String {
        return "Nombre: ${nombre}\n" +
                "Precio: ${precio}\n" +
                "Fecha de fabricación: ${fechaFabricacion}\n" +
                "Marca: ${marca}\n" +
                "Nuevo: ${nuevo}\n"+
                "Id: ${id}"
    }
}