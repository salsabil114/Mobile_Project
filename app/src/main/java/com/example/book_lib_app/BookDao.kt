package com.example.book_lib_app

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Insert
    suspend fun insertBook(book: Book)

    @Query("SELECT * FROM books")
    fun getAllBooksFlow(): Flow<List<Book>>

    @Delete
    suspend fun deleteBook(book: Book)
}
