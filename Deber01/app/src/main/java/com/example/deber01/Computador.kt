package com.example.deber01

class Computador(
    var id:Int,
    var nombre:String,
    var precio:Double,
    var stock: Int,
    var marca:String,
    var nuevo:Int

) {
    override fun toString(): String {
        return "Nombre: ${nombre}\n" +
                "Precio: ${precio}\n" +
                "Stock: ${stock}\n" +
                "Marca: ${marca}\n" +
                "Nuevo: ${nuevo}\n"
    }
}