package com.natansin.flashcardsapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query


@Entity(tableName = "flashcards")
data class Flashcard(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L, // Defina o ID como 0L para inicialização automática
    val question: String,
    val answer: String
)

@Dao
interface FlashcardDao {
    @Insert
    fun insertFlashcard(flashcard: Flashcard)

    @get:Query("SELECT * FROM flashcards")
    val allFlashcards: List<Flashcard?>?

    @Delete
    fun deleteFlashcard(flashcard: Flashcard)

    @Query("DELETE FROM flashcards WHERE id = :flashcardId")
    fun deleteFlashcardById(flashcardId: Long)


    @Query("SELECT id FROM flashcards WHERE question = :question AND answer = :answer")
    suspend fun getFlashcardIdByQuestionAndAnswer(question: String, answer: String): Long?

}
