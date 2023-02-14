package com.example.deber01

class Componente(
    var nombre:String,
    var precio:Float,
    var fechaFabricacion: String,
    var marca:String,
    var nuevo:Boolean

) {
    override fun toString(): String {
        return "Nombre: ${nombre}\n" +
                "Precio: ${precio}\n" +
                "Fecha de fabricación: ${fechaFabricacion}\n" +
                "Marca: ${marca}\n" +
                "Nuevo: ${nuevo}\n"
    }
}