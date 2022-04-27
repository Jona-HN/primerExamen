package com.uabc.computacion.jonathan1168659.primerexamen.model

class Secuencia private constructor(private val elementos: Array<String>) : Cloneable
{
    val longitud = elementos.size
    private val elementosVisibles = elementos.clone()
    var fueSeleccionada = false
        private set

    fun compareTo(s : Secuencia) = this.elementos.contentEquals(s.elementos)

    override fun toString() = elementos.joinToString(separator = " ")

    fun elementosVisiblesToString() = elementosVisibles.joinToString(separator = " ")

    operator fun get(indice : Int) = elementosVisibles[indice]

    operator fun set(indice : Int, value : String)
    {
        elementosVisibles[indice] = value
    }

    override fun clone() : Secuencia
    {
        return Secuencia(elementos)
    }

    companion object
    {
        private val coleccion : HashMap<Int, Array<Secuencia>> = hashMapOf(
            1 to arrayOf(
                Secuencia(arrayOf("a", "b", "c", "d")),
                Secuencia(arrayOf("e", "f", "g", "h")),
                Secuencia(arrayOf("1", "2", "3", "4")),
                Secuencia(arrayOf("w", "x", "y", "z")),
                Secuencia(arrayOf("6", "7", "8", "9")),
                Secuencia(arrayOf("1", "0", "1", "0")),
                Secuencia(arrayOf("0", "1", "0", "1")),
                Secuencia(arrayOf("1", "2", "4", "8")),
                Secuencia(arrayOf("16", "32", "64", "128")),
                Secuencia(arrayOf("14", "11", "8", "5"))
            ),
            2 to arrayOf(
                // Escala mayor (tonos)
                Secuencia(arrayOf("do", "re", "mi", "fa", "sol")),
                Secuencia(arrayOf("fa", "sol", "la", "si", "do")),
                // Colores del arcoíris
                Secuencia(arrayOf("rojo", "naranja", "amarillo", "verde", "azul", "morado")),
                // Planetas del sistema solar
                Secuencia(arrayOf("mercurio", "venus", "tierra", "marte")),
                Secuencia(arrayOf("jupiter", "saturno", "urano", "neptuno")),
                // +2, +3, +4 ... +n
                Secuencia(arrayOf("4", "6", "9", "13", "18")),
                // Menos dos y por tres
                Secuencia(arrayOf("5", "3", "9", "7", "21", "19")),
                Secuencia(arrayOf("4", "2", "6", "4", "12", "10")),
                // Números romanos
                Secuencia(arrayOf("I", "II", "III", "IV", "V")),
                Secuencia(arrayOf("VI", "VII", "VIII", "IX", "X"))
            ),
            3 to arrayOf(
                // Nombre de la distribución del teclado
                Secuencia(arrayOf("q", "w", "e", "r", "t", "y")),
                // Peso de las posiciones en base binaria
                Secuencia(arrayOf("1", "2", "4", "8", "16", "32")),
                // Gases nobles
                Secuencia(arrayOf("He", "Ne", "Ar", "Kr", "Xe", "Rn", "Og")),
                // Serie de Fibonacci
                Secuencia(arrayOf("1", "2", "3", "5", "8", "13", "21", "34")),
                Secuencia(arrayOf("55", "89", "144", "233", "377", "610", "987")),
                // Entre dos y menos 2^n
                Secuencia(arrayOf("96", "48", "44", "22", "18", "9", "3")),
                Secuencia(arrayOf("108", "54", "52", "26", "22", "11", "5")),
                // Números primos
                Secuencia(arrayOf("1", "2", "3", "5", "7", "11")),
                Secuencia(arrayOf("13", "17", "19", "23", "29", "31")),
                // Alfabeto griego
                Secuencia(arrayOf("alfa", "beta", "gamma", "delta", "epsilon"))
            )
        )

        fun getNewInstance(nivel : Int) : Secuencia
        {
            if (nivel !in 1..coleccion.size)
            {
                throw Exception("Índice no válido")
            }
            else
            {
                val secuenciasDelNivel = coleccion[nivel]
                val numeroSecuencias = secuenciasDelNivel!!.size
                var secuencia : Secuencia

                do
                {
                    secuencia = secuenciasDelNivel[(0 until numeroSecuencias).random()]
                } while (secuencia.fueSeleccionada)

                secuencia.fueSeleccionada = true

                // Se crea una copia (instancia nueva) de la secuencia seleccionada
                secuencia = secuencia.clone()

                return secuencia
            }
        }

        fun resetearSeleccionDeSecuencias()
        {
            for (coleccionSecuencias in coleccion)
            {
                for (coleccionesPorNivel in coleccionSecuencias.value)
                {
                    coleccionesPorNivel.fueSeleccionada = false
                }
            }
        }
    }
}