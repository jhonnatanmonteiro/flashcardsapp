package com.natansin.flashcardsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_choose_option : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_choose_option)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainaa)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnCreateFlashcards = findViewById<Button>(R.id.btnCreateFlashcards)

// Configurando um ouvinte de clique para o botão "Criar Flashcards"
        btnCreateFlashcards.setOnClickListener {
            // Criando uma intenção para abrir a atividade de adicionar flashcards
            val intent = Intent(this, activity_add_flashcard::class.java)
            startActivity(intent)
        }

        val btnminhasflashcards = findViewById<Button>(R.id.btnMyFlashcards)

// Configurando um ouvinte de clique para o botão "Meus Flashcards"
        btnminhasflashcards.setOnClickListener {
            // Criando uma intenção para abrir a MainActivity
            val intent1 = Intent(this, MainActivity::class.java)
            startActivity(intent1)
        }
    }
}
