package com.uabc.computacion.jonathan1168659.primerexamen.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.uabc.computacion.jonathan1168659.primerexamen.databinding.ActivityMainBinding
import com.uabc.computacion.jonathan1168659.primerexamen.model.Score
import com.uabc.computacion.jonathan1168659.primerexamen.viewmodel.JuegoSecuenciasViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.FileOutputStream
import java.io.OutputStreamWriter

class MainActivity : AppCompatActivity()
{
    private lateinit var binding : ActivityMainBinding
    private val juegoSecuenciasViewModel : JuegoSecuenciasViewModel by viewModels()
    private lateinit var timer : CountDownTimer
    private var tiempoTotal : Long = 0
    private var archivoScores = "scores.txt"

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // El botón de inicio empieza habilitado, mientras que
        // los textview de las respuestas empiezan deshabilitados
        habilitarComponentes(true)

        juegoSecuenciasViewModel.secuenciasModel.observe(this) { secuencias ->
            binding.secuenciaUno.text = secuencias[0].elementosVisiblesToString()
            binding.secuenciaDos.text = secuencias[1].elementosVisiblesToString()
            binding.secuenciaTres.text = secuencias[2].elementosVisiblesToString()
        }

        juegoSecuenciasViewModel.tiempoModel.observe(this) { tiempo ->
            timer = object : CountDownTimer(tiempo * 1000, 1000)
            {
                override fun onTick(millisUntilFinished: Long)
                {
                    binding.temporizador.text = ("tiempo restante: " + millisUntilFinished / 1000)
                    tiempoTotal++
                }

                override fun onFinish()
                {
                    binding.temporizador.text = ("¡se acabó!")
                    registrarScore(tiempoTotal, juegoSecuenciasViewModel.nivel.value as Int, juegoSecuenciasViewModel.turno.value as Int)
                    habilitarComponentes(true)
                }
            }.start()
        }
        
        juegoSecuenciasViewModel.turno.observe(this) { turno ->
            binding.turno.text = "Turno: ${turno}"
        }
        
        juegoSecuenciasViewModel.nivel.observe(this) { nivel ->
            binding.nivel.text = "Nivel: ${nivel}"
        }

        juegoSecuenciasViewModel.jugadorAvanzoNivel.observe(this) { avanzoNivel ->
            if (avanzoNivel) timer.cancel()
        }

        binding.botonInicio.setOnClickListener {
            juegoSecuenciasViewModel.reiniciarJuego()
            habilitarComponentes(false)
            limpiarRespuestas()
            tiempoTotal = 0
        }

        //TODO: agregar un check a cada respuesta
//        juegoSecuenciasViewModel.primeraRespuestaCorrecta.observe(this) { valida ->
//            Toast.makeText(this, "Respuesta 1 ${if (valida) "válida" else "inválida"}", Toast.LENGTH_SHORT).show()
//        }
//
//        juegoSecuenciasViewModel.segundaRespuestaCorrecta.observe(this) { valida ->
//            Toast.makeText(this, "Respuesta 2 ${if (valida) "válida" else "inválida"}", Toast.LENGTH_SHORT).show()
//        }
//
//        juegoSecuenciasViewModel.terceraRespuestaCorrecta.observe(this) { valida ->
//            Toast.makeText(this, "Respuesta 3 ${if (valida) "válida" else "inválida"}", Toast.LENGTH_SHORT).show()
//        }

        juegoSecuenciasViewModel.jugadorAvanzoTurno.observe(this) { jugadorAvanzoTurno ->
            if (jugadorAvanzoTurno)
            {
                limpiarRespuestas()
            }
        }

        binding.respuestaUno.setOnFocusChangeListener { view, hasFocus ->
            comprobarRespuesta(view, hasFocus)
        }

        binding.respuestaDos.setOnFocusChangeListener { view, hasFocus ->
            comprobarRespuesta(view, hasFocus)
        }

        binding.respuestaTres.setOnFocusChangeListener { view, hasFocus ->
            comprobarRespuesta(view, hasFocus)
        }
    }

    override fun onDestroy()
    {
        super.onDestroy()

        try {
            val fileOutputStream: FileOutputStream = openFileOutput(archivoScores, Context.MODE_PRIVATE)
            val outputWriter = OutputStreamWriter(fileOutputStream)
            outputWriter.write("")
            outputWriter.close()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun comprobarRespuesta(view : View, hasFocus : Boolean)
    {
        if (!hasFocus)
        {
            val respuestaJugador = (view as TextView).text.toString()

            if (respuestaJugador.isNotBlank())
            {
                val numRespuesta = view.tag.toString().toInt()
                juegoSecuenciasViewModel.comprobarRespuesta(numRespuesta, respuestaJugador.split(" "))
            }
        }
    }

    private fun habilitarComponentes(activo : Boolean)
    {
        binding.botonInicio.isEnabled = activo
        binding.respuestaUno.isEnabled = !activo
        binding.respuestaDos.isEnabled = !activo
        binding.respuestaTres.isEnabled = !activo
        binding.turno.isVisible = !activo
        binding.nivel.isVisible = !activo
    }

    private fun limpiarRespuestas()
    {
        binding.respuestaUno.text.clear()
        binding.respuestaDos.text.clear()
        binding.respuestaTres.text.clear()
    }

    private fun registrarScore(tiempo : Long, nivel : Int, turno : Int)
    {
        val score = Score(tiempo, nivel, turno)
        val intentScore = Intent(this, ScoresActivity::class.java)
        val scoreJson = Json.encodeToString(score)

        intentScore.putExtra("score", scoreJson)
        startActivity(intentScore)
    }
}