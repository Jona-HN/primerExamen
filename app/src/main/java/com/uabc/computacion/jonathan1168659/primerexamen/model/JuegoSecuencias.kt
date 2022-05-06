package com.uabc.computacion.jonathan1168659.primerexamen.model

import kotlin.collections.ArrayList
import kotlin.math.floor

class JuegoSecuencias
{
    var nivel = 1
    var turno = 0
    var jugadorAvanzoTurno = false
        private set
    var jugadorAvanzoNivel = false
        private set
    var primeraRespuestaCorrecta = false
        private set
    var segundaRespuestaCorrecta = false
        private set
    var terceraRespuestaCorrecta = false
        private set
    val secuenciasPorTurno = 3
    val turnosPorNivel = 5
    private lateinit var secuencias : ArrayList<Secuencia>

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

        Secuencia.resetearSeleccionDeSecuencias()
        this.secuencias = secuencias
        return this.secuencias
    }

    private fun agregarIncognita(secuencia : Secuencia)
    {
        var indiceIncognita : Int
        var numeroIncognitas = 0
        val maximoNumDeIncog = floor(secuencia.longitud / 2.0)
        val respuestas = ArrayList<String>()

        while (numeroIncognitas < nivel && numeroIncognitas < maximoNumDeIncog)
        {
            do
            {
                indiceIncognita = (0 until secuencia.longitud).random()
            } while (secuencia[indiceIncognita] == "_")

            respuestas.add(secuencia[indiceIncognita])
            secuencia[indiceIncognita] = "_"

            numeroIncognitas++
        }

        secuencia.respuestas = respuestas
    }

    fun inicializarTiempo() : Long
    {
        /*
         * 15 secuencias en total (3 secuencias y 5 turnos por nivel)
         * Nivel 1 = 10 segundos por secuencia = 150 segundos en total
         * Nivel 2 = 8 segundos por secuencia = 120 segundos en total
         * Nivel 3 = 6 segundos por secuencia = 90 segundos en total
         */

        return (secuenciasPorTurno * turnosPorNivel) * 10 - ((nivel - 1) * 30).toLong()
    }

    fun comprobarPrimeraRespuesta(respuestasJugador : List<String>)
    {
        primeraRespuestaCorrecta = secuencias[0].respuestas.containsAll(respuestasJugador)
        avanzarTurno()
    }

    fun comprobarSegundaRespuesta(respuestasJugador : List<String>)
    {
        segundaRespuestaCorrecta = secuencias[1].respuestas.containsAll(respuestasJugador)
        avanzarTurno()
    }

    fun comprobarTerceraRespuesta(respuestasJugador : List<String>)
    {
        terceraRespuestaCorrecta = secuencias[2].respuestas.containsAll(respuestasJugador)
        avanzarTurno()
    }

    fun avanzarTurno()
    {
        jugadorAvanzoTurno = primeraRespuestaCorrecta && segundaRespuestaCorrecta && terceraRespuestaCorrecta

        if (jugadorAvanzoTurno)
        {
            turno++
            resetearRespuestas()

            jugadorAvanzoNivel = turno == turnosPorNivel

            if (jugadorAvanzoNivel)
            {
                turno = 0
                nivel++
            }
        }
    }

    fun resetearRespuestas()
    {
        primeraRespuestaCorrecta = false
        segundaRespuestaCorrecta = false
        terceraRespuestaCorrecta = false
    }
}