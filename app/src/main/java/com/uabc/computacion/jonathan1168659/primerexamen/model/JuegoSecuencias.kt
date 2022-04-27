package com.uabc.computacion.jonathan1168659.primerexamen.model

import android.os.CountDownTimer
import kotlin.collections.ArrayList
import kotlin.math.floor

class JuegoSecuencias
{
    // TODO: darle funcionalidad al timer
    lateinit var timer : CountDownTimer
        private set
    var nivel = 1
    val secuenciasPorTurno = 3

    fun generarTresSecuencias() : ArrayList<Secuencia>
    {
        val secuencias = ArrayList<Secuencia>()
        var secuencia : Secuencia

        for (numSecuencias in 1..secuenciasPorTurno)
        {
            secuencia = Secuencia.getNewInstance(nivel)
            agregarIncognita(secuencia)
            secuencias.add(secuencia)
        }

        return secuencias
    }

    private fun agregarIncognita(secuencia : Secuencia)
    {
        var indiceIncognita : Int
        var numeroIncognitas = 0
        val maximoNumDeIncog = floor(secuencia.longitud / 2.0)

        while (numeroIncognitas < nivel && numeroIncognitas < maximoNumDeIncog)
        {
            do
            {
                indiceIncognita = (0 until secuencia.longitud).random()
            } while (secuencia[indiceIncognita] == "_")

            secuencia[indiceIncognita] = "_"

            numeroIncognitas++
        }
    }
}