package com.example.book_lib_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers

class BookViewModel(private val bookDao: BookDao) : ViewModel() {

    private val searchQuery = MutableStateFlow("")
    private val allBooks = bookDao.getAllBooksFlow()

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books

    init {
        viewModelScope.launch {
            combine(allBooks, searchQuery) { books, query ->
                if (query.isBlank()) {
                    books
                } else {
                    books.filter {
                        it.title.contains(query, ignoreCase = true) ||
                                it.author.contains(query, ignoreCase = true)
                    }
                }
            }
                .flowOn(Dispatchers.IO) // This is CRITICAL for Room
                .collect { filteredBooks ->
                    _books.value = filteredBooks
                }
        }
    }

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }

    fun addBook(title: String, author: String) {
        viewModelScope.launch(Dispatchers.IO) { // Run in background
            bookDao.insertBook(
                Book(
                    title = title,
                    author = author,
                    createdDate = System.currentTimeMillis()
                )
            )
        }
    }

    fun deleteBook(book: Book) {
        viewModelScope.launch(Dispatchers.IO) { // Run in background
            bookDao.deleteBook(book)
        }
    }
}