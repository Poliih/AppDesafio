package com.example.myapplication2

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication2.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val wordsList = mutableListOf<String>()
    private val usedWords = mutableSetOf<String>()
    private var challengeCounter = 1

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

        binding.menuAddWord.setOnClickListener {
            showAddWordSection()
        }

        binding.menuGenerateChallenge.setOnClickListener {
            showGenerateChallengeSection()
        }

        binding.menuHistory.setOnClickListener {
            showHistorySection()
        }

        // Reset para normal quando clicar na tela verde
        binding.greenScreen.setOnClickListener {
            resetView()
        }

        // Exibir a seção de adicionar palavra ao iniciar
        showAddWordSection()
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

            // Garantir que index2 é diferente de index1
            while (index2 == index1) {
                index2 = random.nextInt(wordsList.size)
            }

            val randomWord1 = wordsList[index1]
            val randomWord2 = wordsList[index2]

            // Exibir as palavras aleatórias na tela verde
            binding.greenScreenText.text = "$randomWord1\n$randomWord2"
            binding.greenScreen.visibility = View.VISIBLE

            // Adicionar palavras usadas ao conjunto
            usedWords.add(randomWord1)
            usedWords.add(randomWord2)

            // Atualizar histórico
            updateHistory(randomWord1, randomWord2)

            // Incrementar contador de desafios
            challengeCounter++
        } else {
            binding.textViewResult.text = "Adicione pelo menos duas palavras."
        }
    }

    private fun updateHistory(word1: String, word2: String) {
        binding.textViewHistory.append("Desafio $challengeCounter: $word1 $word2\n")
    }

    private fun showAddWordSection() {
        binding.editTextWord.visibility = View.VISIBLE
        binding.buttonAdd.visibility = View.VISIBLE
        binding.buttonGenerate.visibility = View.GONE
        binding.textViewResult.visibility = View.GONE
        binding.textViewHistory.visibility = View.GONE
        binding.greenScreen.visibility = View.GONE
    }

    private fun showGenerateChallengeSection() {
        binding.editTextWord.visibility = View.GONE
        binding.buttonAdd.visibility = View.GONE
        binding.buttonGenerate.visibility = View.VISIBLE
        binding.textViewResult.visibility = View.VISIBLE
        binding.textViewHistory.visibility = View.GONE
        binding.greenScreen.visibility = View.GONE
    }

    private fun showHistorySection() {
        binding.editTextWord.visibility = View.GONE
        binding.buttonAdd.visibility = View.GONE
        binding.buttonGenerate.visibility = View.GONE
        binding.textViewResult.visibility = View.GONE
        binding.textViewHistory.visibility = View.VISIBLE
        binding.greenScreen.visibility = View.GONE
    }

    private fun resetView() {
        binding.greenScreen.visibility = View.GONE
        showGenerateChallengeSection()
    }
}
