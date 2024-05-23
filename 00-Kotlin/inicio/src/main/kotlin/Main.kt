import java.util.*

fun main() {
    println("Hola Mundo")
    //Inmutables (No se puede re asignar)
    val inmutable: String = "Kevin"
    // inmutable = "Otro nombre" //Error

    //Mutables
    var mutable: String = "Kevin"
    mutable = "Mateo" //Correcto
    //VAL > VAR

    //Duck Typing
    var ejemploVariable = "Kevin Cano"
    val edadEjemplo = 21
    ejemploVariable.trim()
    //ejemploVariable = edadEjemplo //Error de tipos

    //Variables primitivas
    val nombre: String = "Kevin"
    val sueldo: Double = 1.21
    val estadoCivil: Char = 'S'
    val mayorEdad: Boolean = true

    //Clases en Java
    val fechaNacimiento: Date = Date()

    //when switch
    val estadoCivilWhen = "C"
    when (estadoCivilWhen) {
        ("C") -> {
            println("Casado")
        }
        "S" ->{
            println("Soltero")
        }
    }
    val esSoltero = (estadoCivilWhen == "S")
    val coqueteo = if (esSoltero) "Si" else "No" //If else chiquito

    calcularSueldo(10.00)
    calcularSueldo(10.00, 15.00, 20.00)
    // Named Parameters
    // calcularSueldo(sueldo, tasa, bonoEspecial)
    calcularSueldo(10.00, bonoEspecial = 15.00)
    calcularSueldo(bonoEspecial = 20.00, sueldo=10.00, tasa=14.00)


    //Uso de clases
    val sumaUno = Suma(1,2)
    val sumaDos = Suma(null, 1)
    val sumaTres = Suma(1, null)
    val sumaCuatro = Suma(null, null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    //Arreglos
    //Arreglo Estatico
    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3)
    println(arregloEstatico)
    //Arreglo Dinamico
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(1,2,3,4,5,6,7,8,9,10)
    println(arregloDinamico)

    println(arregloDinamico.add(11))
    println(arregloDinamico.add(12))
    println(arregloDinamico)

    //Operadores
    //For each Iterar el arreglo
    val respuestaForEach: Unit = arregloDinamico
        .forEach { valorActual: Int ->
            println("Valor actual: ${valorActual}")
        }
    // "It" es el valor por defecto significa ese elemento
    arregloDinamico.forEach{ println("Valor actual con it: ${it}") }

    //MAP modifica el arreglo
    val respuestaMap: List<Double> = arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble() + 100.00
        }
    println(respuestaMap)
    val respuestaMap2 = arregloDinamico.map { it + 15 }
    println(respuestaMap2)

    //Filter Filtra el arreglo
    val respuestaFilter: List<Int> = arregloDinamico
        .filter { valorActual: Int ->
            val mayoresACinco: Boolean = valorActual > 5
            return@filter mayoresACinco
        }

    val respuestaFilterDos = arregloDinamico.filter { it <= 5 }
    println(respuestaFilter)
    println(respuestaFilterDos)

    // OR AND
    // OR -> ANY ALGUNO CUMPLE?
    // AND -> ALL TODOS CUMPLEN?
    val respuestaAny: Boolean = arregloDinamico
        .any { valorActual: Int ->
            return@any valorActual > 5
        }
    println(respuestaAny) // true
    val respuestaAll: Boolean = arregloDinamico
        .all { valorActual: Int ->
            return@all valorActual > 5
        }
    println(respuestaAll) // false

    //Reduce Acumula el valor
    // Valor acumulado = 0 siempre empieza en 0 en kotlin
    // [1,2,3,4,5] -> Acumular SUMAR estos valores del arreglo
    // valorIteracion1 = valorEmpieza + 1 = 0 + 1 = 1 -> Iteracion1
    // valorIteracion2 = valorAcumuladoIteracion1 + 2 = 1 + 2 = 3 -> Iteracion2
    // valorIteracion3 = valorAcumuladoIteracion2 + 3 = 3 + 3 = 6 -> Iteracion3
    // valorIteracion4 = valorAcumuladoIteracion3 + 4 = 6 + 4 = 10 -> Iteracion4
    // valorIteracion5 = valorAcumuladoIteracion4 + 5 = 10 + 5 = 15 -> Iteracion5
    val respuestaReduce: Int = arregloDinamico
        .reduce { acumulado: Int, valorActual: Int ->
            return@reduce (acumulado + valorActual)
        }
    println(respuestaReduce)



}

fun imprimirNombre(nombre: String): Unit {
    println("Nombre: ${nombre}")
}

fun calcularSueldo(
    sueldo: Double, //Requerido
    tasa: Double = 12.00, //Opcional
    bonoEspecial: Double? = null //Opcional (nullable)
    // Variable ? -> "?" Es nullable osea que puede en algun momento ser nulo

    ):Double {
        if(bonoEspecial == null){
            return sueldo * (100/tasa)
    }else {
        return sueldo * (100/tasa) * bonoEspecial

    }
}

abstract class NumerosJava {
    protected val numeroUno: Int
    private val numeroDos: Int

    constructor(
        uno: Int,
        dos: Int
    ) {
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializamos")
    }
}

abstract class Numeros(
    // Caso 1: Parametro Normal
    // uno:Int , parametro sin modificar acceso

    // Caso 2: Parametro y propiedad atributo private
    // private var uno: Int propiedad instancia.uno

    protected val numeroUno: Int, // instancia.numeroUno
    protected val numeroDos: Int // instancia.numeroDos
    //parametroInutil: String, //Parametro
) {
    init {
        this.numeroUno
        this.numeroDos
        //this.parametroInutil
        println("Inicializamos")
    }
}

class Suma(
    unoParametro: Int,
    dosParametro: Int,
): Numeros(
    unoParametro,
    dosParametro,
) {
    public val soyPublicoExplicito:String = "Explicito" // Publico
    val soyPublicoImplicito:String = "Implicito" // Publico (propiedades, metodos)
    init {
        this.numeroUno
        this.numeroDos
        numeroUno // this. OPCIONAL (propiedades, metodos)
        numeroDos // this. OPCIONAL (propiedades, metodos)
        this.soyPublicoExplicito
        soyPublicoImplicito // this. OPCIONAL (propiedades, metodos)
    }

    constructor(
        uno: Int?,
        dos: Int
    ):this(
        if(uno == null) 0 else uno,
        dos
    )
    constructor(
        uno: Int,
        dos: Int?
    ):this(
        uno,
        if(dos == null) 0 else dos,
    )
    constructor(
        uno: Int?,
        dos: Int?
    ):this(
        if(uno == null) 0 else uno,
        if(dos == null) 0 else dos,
    )

    fun sumar(): Int {
        val total = numeroUno + numeroDos
        //Suma.agregarHistorial(total) ("Suma" o "Nombreclase" es OPCIONAL)
        agregarHistorial(total)
        return total
    }
    companion object{ // Comparte entre todos las instancias, somilar al static
        //funciones y variables
        val pi = 3.14
        fun elevarAlCuadrado(num:Int):Int{
            return num * num
        }
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorTotalSuma:Int){
            historialSumas.add(valorTotalSuma)
        }
    }

}