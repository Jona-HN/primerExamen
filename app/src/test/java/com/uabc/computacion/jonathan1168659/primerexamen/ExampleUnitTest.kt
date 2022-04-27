package com.uabc.computacion.jonathan1168659.primerexamen

import com.uabc.computacion.jonathan1168659.primerexamen.model.JuegoSecuencias
import com.uabc.computacion.jonathan1168659.primerexamen.model.Secuencia
import org.junit.Test

import org.junit.Assert.*
import java.lang.Exception

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest
{
    @Test
    fun addition_isCorrect()
    {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun pruebaSecuencia()
    {
        val secuencia = Secuencia.getNewInstance(1)
        println(secuencia.toString())
        val secuencia2 = Secuencia.getNewInstance(1)
        println(secuencia2.toString())
        println("secuencia == secuencia2 ? ${secuencia.compareTo(secuencia2)}")
        try
        {
            val secuencia3 = Secuencia.getNewInstance(0)
        }
        catch (e : Exception) { e.printStackTrace() }
        try
        {
            val secuencia4 = Secuencia.getNewInstance(4)
        }
        catch (e : Exception) { e.printStackTrace() }
    }

    @Test
    fun pruebaGenerarTresSecuencias()
    {
        val juego = JuegoSecuencias()

        for (i in 1..5)
        {
            println("Iteración $i")
            for (secuencia in juego.generarTresSecuencias())
            {
                println(secuencia.elementosVisiblesToString())
            }
            Secuencia.resetearSeleccionDeSecuencias()
        }

        juego.nivel = 2
        for (i in 1..5)
        {
            println("Iteración $i")
            val secuenciasGeneradas = juego.generarTresSecuencias()
            for (secuencia in secuenciasGeneradas)
            {
                println(secuencia.elementosVisiblesToString())
            }
            Secuencia.resetearSeleccionDeSecuencias()
        }

        juego.nivel = 3
        for (i in 1..5)
        {
            println("Iteración $i")
            val secuenciasGeneradas = juego.generarTresSecuencias()
            for (secuencia in secuenciasGeneradas)
            {
                println(secuencia.elementosVisiblesToString())
            }
            Secuencia.resetearSeleccionDeSecuencias()
        }
    }
}