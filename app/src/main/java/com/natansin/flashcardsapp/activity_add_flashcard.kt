package com.natansin.flashcardsapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.natansin.flashcardsapp.Flashcard

class activity_add_flashcard : AppCompatActivity() {

    private lateinit var database: FlashcardDatabase
    private lateinit var questionEditText: TextView
    private lateinit var answerEditText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_flashcard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main1)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets // Retorna os insets para manter a consistência com o tipo esperado
        }

        // Inicialize o banco de dados usando o singleton DatabaseInitializer
        database = DatabaseInitializer.getDatabase(applicationContext)

        val buttonSave = findViewById<Button>(R.id.buttonsalvar)
        questionEditText = findViewById<TextView>(R.id.editTextQuestion)
        answerEditText = findViewById<TextView>(R.id.editTextAnswer)

        buttonSave.setOnClickListener {
            val question = questionEditText.text.toString()
            val answer = answerEditText.text.toString()

            val flashcard = Flashcard(question = question, answer = answer)
            insertFlashcard(flashcard)
            finish()
        }

    }

    private fun insertFlashcard(flashcard: Flashcard) {
        Thread {
            database.flashcardDao().insertFlashcard(flashcard)
        }.start()
    }
}
