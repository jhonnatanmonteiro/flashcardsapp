package com.natansin.flashcardsapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Flashcard::class], version = 1)
abstract class FlashcardDatabase : RoomDatabase() {
    abstract fun flashcardDao(): FlashcardDao
}