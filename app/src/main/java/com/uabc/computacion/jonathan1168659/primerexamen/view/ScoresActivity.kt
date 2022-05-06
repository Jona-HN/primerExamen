package com.uabc.computacion.jonathan1168659.primerexamen.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.uabc.computacion.jonathan1168659.primerexamen.databinding.ActivityScoresBinding
import com.uabc.computacion.jonathan1168659.primerexamen.model.Score
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.*

class ScoresActivity : AppCompatActivity()
{
    private var archivoScores = "scores.txt"
    private lateinit var binding : ActivityScoresBinding
    lateinit var editText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityScoresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editText = binding.scores
        val scoreJson = intent.getStringExtra("score").toString()
        val score = Json.decodeFromString<Score>(scoreJson)

        saveTextFile(score)
        readTextFile()
    }

    private fun saveTextFile(score : Score) {
        val stringBuilder = StringBuilder()
        try {
            val fileOutputStream: FileOutputStream = openFileOutput(archivoScores, MODE_APPEND or Context.MODE_PRIVATE)
            val outputWriter = OutputStreamWriter(fileOutputStream)
            stringBuilder.append(score.toString()).append("\n")
            val string = stringBuilder.toString()
            outputWriter.write(string)
            stringBuilder.clear()
            outputWriter.close()
            //display file saved message
            Toast.makeText(baseContext, "File saved successfully!", Toast.LENGTH_SHORT).show()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun readTextFile() {
        val stringBuilder = StringBuilder()
        val `is`: InputStream = openFileInput(archivoScores)
        val reader = BufferedReader(InputStreamReader(`is`))
        while (true) {
            try {
                if (reader.readLine().also { string ->
                        if (string != null) stringBuilder.append(string).append("\n")
                    } == null) break
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        binding.scores.text = stringBuilder
        `is`.close()
    }
}