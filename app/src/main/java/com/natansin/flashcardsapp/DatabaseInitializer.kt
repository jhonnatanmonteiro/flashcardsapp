package com.natansin.flashcardsapp

import android.content.Context
import androidx.room.Room


object DatabaseInitializer {
    private var database: FlashcardDatabase? = null

    fun getDatabase(context: Context): FlashcardDatabase {
        if (database == null) {
            database = Room.databaseBuilder(
                context.applicationContext,
                FlashcardDatabase::class.java,
                "flashcard_database"
            ).build()
        }
        return database!!
    }
}