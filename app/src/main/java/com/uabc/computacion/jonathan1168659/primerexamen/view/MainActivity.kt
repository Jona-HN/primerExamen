package com.uabc.computacion.jonathan1168659.primerexamen.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uabc.computacion.jonathan1168659.primerexamen.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}