import java.util.*
import kotlin.collections.ArrayList

fun main(args: Array<String>) {
    println("Hello World!")

    //Tipos de variables
    //Inmutables: no se puedes reasignar o cambiar su contenido

    val inmutable: String  = "Henry";

    //Mutables : se puede reasigar

    var mutable: String = "Henry"
    mutable = "Miguel"

    //Duck Typing

    val ejemploVariable = "Ejemplo"
    ejemploVariable.trim()

    val edadEjemplo = 24

    //Variable primitivas
    val nombreProfesor:String = "Un nombre"
    val sueldo :Double = 1.23
    val estadoCivil:Char = 'S'
    val mayorEdad:Boolean = true

    //Clases
    val fechaNacimiento: Date = Date()

    //Switch no existe
    val estadoCivilWhen = "S"
    when(estadoCivilWhen){
        ("S")->{
            println("Soltero")
        }
        "C"-> println("Casado")
        else -> println("Desconocido")
    }

    val coqueto = if(estadoCivilWhen == "S") "Si" else "No"

    val sumaUno = Suma(1,2)
    val sumaDos = Suma(1,null)
    val sumaTres = Suma(null,2)
    val sumaCuatro = Suma(null,null)

    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()

    println("Historial: ${Suma.historialSumas}")
}

//Funciones
fun imprimirNombre(nombre:String): Unit{
    println("Nombre: ${nombre}")
}

fun calcularSueldo(
    sueldo:Double, //requerido
    tasa: Double = 12.00, //opcional por defecto
    bonoEspecial: Double? = null //opcional nulo
):Double{
    if(bonoEspecial != null){
        return sueldo * tasa *bonoEspecial
    }
    return sueldo*tasa
}

abstract class NumeroJava{
    protected  val numeroUno: Int
    private val numeroDos: Int
    constructor(
        uno:Int,
        dos: Int
    ){
        this.numeroUno = uno
        this.numeroDos = dos
        println("Iniciando")
    }
}

abstract  class Numeros( //Constructor primario
    protected  val numeroUno: Int,
    protected  val numeroDos: Int
){
    init { //Bloque de codigo del constructor primario
        this.numeroUno //this es opcional
        numeroDos
        println("Iniciando")
    }
}

class Suma( //Constructor primario Suma
    uno:Int ,
    dos: Int
):Numeros(//Heredamos de la clase Numeros
    //Super constructor numeros
    uno,
    dos
){
    init {//Bloque constructor primario
        this.numeroUno
        this.numeroDos
    }
    constructor(//Segundo constructor
        uno:Int?,
        dos: Int
    ):this(
        if(uno==null) 0 else uno,
        dos
    )

    constructor(//Tercer constructor
        uno:Int,
        dos: Int?
    ):this(
        uno,
        if(dos==null) 0 else dos
    )

    constructor(//Cuarto constructor
        uno:Int?,
        dos: Int?
    ):this(
        if(uno==null) 0 else uno,
        if(dos==null) 0 else dos
    ){}

    fun sumar():Int{
        val total = numeroDos + numeroUno
        agregarHistorial(total)
        return total
    }

    companion object {
        val pi = 3.14
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorNuevaSuma:Int){
            historialSumas.add(valorNuevaSuma)
        }
    }
}