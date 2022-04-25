package com.uabc.computacion.jonathan1168659.primerexamen.model

class Secuencia private constructor(private val elementos: ArrayList<String>)
{
    val longitud = elementos.size

    fun compareTo(s : Secuencia) = this.elementos == s.elementos

    override fun toString() = elementos.joinToString(separator = " ")

    companion object
    {
        private val coleccion : HashMap<Int, HashMap<Int, Secuencia>> = hashMapOf(
            // TODO: agregar más secuencias
            1 to hashMapOf(
                1 to Secuencia(arrayListOf("a", "b", "c", "d")),
                2 to Secuencia(arrayListOf("1", "2", "3", "4")),
                3 to Secuencia(arrayListOf("w", "x", "y", "z")),
                4 to Secuencia(arrayListOf("6", "7", "8", "9")),
                5 to Secuencia(arrayListOf("1", "0", "1", "0"))
            ),
            // TODO: agregar las secuencias para los otros niveles
            2 to hashMapOf(),
            3 to hashMapOf()
        )

        fun getNew(nivel : Int) : Secuencia
        {
            if (nivel !in 1..coleccion.size)
            {
                throw Exception("Índice no válido")
            }
            else
            {
                val secuenciasDelNivel = coleccion[nivel]
                val numeroSecuencias = secuenciasDelNivel!!.size

                return secuenciasDelNivel[(1..numeroSecuencias).random()] as Secuencia
            }
        }
    }
}