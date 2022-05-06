package com.uabc.computacion.jonathan1168659.primerexamen.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uabc.computacion.jonathan1168659.primerexamen.model.JuegoSecuencias
import com.uabc.computacion.jonathan1168659.primerexamen.model.Secuencia

class JuegoSecuenciasViewModel : ViewModel()
{
    val juego = JuegoSecuencias()
    val secuenciasModel = MutableLiveData<ArrayList<Secuencia>>()
    val tiempoModel = MutableLiveData<Long>()
    val primeraRespuestaCorrecta = MutableLiveData<Boolean>()
    val segundaRespuestaCorrecta = MutableLiveData<Boolean>()
    val terceraRespuestaCorrecta = MutableLiveData<Boolean>()
    val jugadorAvanzoTurno = MutableLiveData<Boolean>()
    val jugadorAvanzoNivel = MutableLiveData<Boolean>()

    fun reiniciarJuego()
    {
        juego.turno = 0
        juego.nivel = 1
        juego.resetearRespuestas()
        primeraRespuestaCorrecta.postValue(juego.primeraRespuestaCorrecta)
        segundaRespuestaCorrecta.postValue(juego.segundaRespuestaCorrecta)
        terceraRespuestaCorrecta.postValue(juego.terceraRespuestaCorrecta)
        generarSecuencias()
        inicializarTiempo()
    }

    fun generarSecuencias()
    {
        val secuenciasNivel = juego.generarTresSecuencias()
        secuenciasModel.postValue(secuenciasNivel)
    }

    fun inicializarTiempo()
    {
        val tiempoActual = juego.inicializarTiempo()
        tiempoModel.postValue(tiempoActual)
    }

    fun comprobarRespuesta(numRespuesta : Int, respuestas : List<String>)
    {
        when (numRespuesta)
        {
            1 ->
            {
                juego.comprobarPrimeraRespuesta(respuestas)
                primeraRespuestaCorrecta.postValue(juego.primeraRespuestaCorrecta)
            }
            2 ->
            {
                juego.comprobarSegundaRespuesta(respuestas)
                segundaRespuestaCorrecta.postValue(juego.segundaRespuestaCorrecta)
            }
            3 ->
            {
                juego.comprobarTerceraRespuesta(respuestas)
                terceraRespuestaCorrecta.postValue(juego.terceraRespuestaCorrecta)
            }
        }

        jugadorAvanzoTurno.postValue(juego.jugadorAvanzoTurno)
        jugadorAvanzoNivel.postValue(juego.jugadorAvanzoNivel)

        if (juego.jugadorAvanzoTurno) generarSecuencias()

        if (juego.jugadorAvanzoNivel) inicializarTiempo()
    }
}