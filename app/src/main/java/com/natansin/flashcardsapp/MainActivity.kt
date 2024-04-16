package com.natansin.flashcardsapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.text.toLongOrNull

class MainActivity : AppCompatActivity() {
    private lateinit var questionAdapter: QuestionAdapter
    private lateinit var flashcardDao: FlashcardDao
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DatabaseInitializer.getDatabase(this)

        flashcardDao = DatabaseInitializer.getDatabase(this).flashcardDao()

        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        questionAdapter = QuestionAdapter(mutableListOf())
        recyclerView.adapter = questionAdapter

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT // Permitir swipe para a esquerda ou direita
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false // Não permitir mover os itens
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                deleteItem(position)
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)

        lifecycleScope.launch(Dispatchers.IO) {
            val questionsFromDatabase = flashcardDao.allFlashcards?.mapNotNull { flashcard ->
                flashcard?.let { Pair(it.question ?: "", it.answer ?: "") }
            }

            launch(Dispatchers.Main) {
                if (questionsFromDatabase != null) {
                    questionAdapter.setData(questionsFromDatabase)
                }
            }
        }
    }

    private fun deleteItem(position: Int) {
        // Tentar obter o flashcard (pergunta e resposta) da posição
        val flashcardToDelete = questionAdapter.getItemAtPosition(position)

        // Verificar se o flashcard foi obtido
        if (flashcardToDelete != null) {
            val (question, answer) = flashcardToDelete

            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    // Recuperar o ID do flashcard do banco de dados
                    val flashcardId =
                        flashcardDao.getFlashcardIdByQuestionAndAnswer(question, answer)

                    // Verifica se o ID é válido e exclui o flashcard
                    if (flashcardId != null) {
                        flashcardDao.deleteFlashcardById(flashcardId)
                        Log.d("FlashcardApp", "Flashcard \"$question\" excluído com sucesso!")
                    } else {
                        Log.e("FlashcardApp", "ID do flashcard não encontrado para exclusão")
                    }
                } catch (e: Exception) {
                    Log.e("FlashcardApp", "Erro ao excluir flashcard: ${e.message}")
                }
            }
        } else {
            Log.e("FlashcardApp", "Flashcard não encontrado na posição $position")
        }

        // Remove o flashcard do adapter (após a exclusão do banco de dados)
        questionAdapter.removeItemAtPosition(position)
    }
}