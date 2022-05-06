package com.uabc.computacion.jonathan1168659.primerexamen.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.uabc.computacion.jonathan1168659.primerexamen.databinding.ActivityMainBinding
import com.uabc.computacion.jonathan1168659.primerexamen.viewmodel.JuegoSecuenciasViewModel

class MainActivity : AppCompatActivity()
{
    private lateinit var binding : ActivityMainBinding
    private val juegoSecuenciasViewModel : JuegoSecuenciasViewModel by viewModels()
    private lateinit var timer : CountDownTimer

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
                }

                override fun onFinish()
                {
                    binding.temporizador.text = ("¡se acabó!")
                    // TODO: registrar puntaje en el archivo
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
}