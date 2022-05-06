package com.uabc.computacion.jonathan1168659.primerexamen.model

import kotlinx.serialization.Serializable

@Serializable
data class Score(
    val tiempo : Long,
    val nivel : Int,
    val turno : Int
)
{
    override fun toString(): String
    {
        return "Tiempo: $tiempo segundos. Nivel: $nivel. Turno: $turno"
    }
}
