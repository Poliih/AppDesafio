package com.example.myapplication2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication2.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val wordsList = mutableListOf<String>()
    private val usedWords = mutableSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAdd.setOnClickListener {
            addWord()
        }

        binding.buttonGenerate.setOnClickListener {
            generateRandomWords()
        }
    }

    private fun addWord() {
        val word = binding.editTextWord.text?.toString()?.trim()
        if (!word.isNullOrEmpty()) {
            wordsList.add(word)
            binding.editTextWord.text?.clear()
        }
    }

    private fun generateRandomWords() {
        if (wordsList.size >= 2) {
            val random = Random()
            var index1 = random.nextInt(wordsList.size)
            var index2 = random.nextInt(wordsList.size)

            // Ensure index2 is different from index1
            while (index2 == index1) {
                index2 = random.nextInt(wordsList.size)
            }

            val randomWord1 = wordsList[index1]
            val randomWord2 = wordsList[index2]

            // Display the random words
            binding.textViewResult.text = "$randomWord1\n$randomWord2"

            // Add used words to set
            usedWords.add(randomWord1)
            usedWords.add(randomWord2)

            // Update history text
            updateHistory()
        } else {
            binding.textViewResult.text = "Adicione pelo menos duas palavras."
        }
    }

    private fun updateHistory() {
        binding.textViewHistory.text = "Histórico:\n"
        usedWords.forEachIndexed { index, word ->
            binding.textViewHistory.append("${index + 1}. $word\n")
        }
    }
}
