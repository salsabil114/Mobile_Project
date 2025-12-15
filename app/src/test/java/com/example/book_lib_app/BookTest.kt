package com.example.book_lib_app

import org.junit.Test
import org.junit.Assert.*
import org.hamcrest.CoreMatchers.*



class BookTest {

    @Test
    fun testBookGetters() {

        val book = Book(
            id = 1,
            title = "The Great Gatsby",
            author = "Scott",
            createdDate = 2222
        )

        // test getters
        assertEquals(1, book.id)
        assertEquals("The Great Gatsby", book.title)
        assertEquals("Scott", book.author)
        assertEquals(2222, book.createdDate)
    }

    @Test
    fun testBookDefaultId() {
        // test that id defaults to 0 when not provided
        val book = Book(
            title = "test book",
            author = "test author",
            createdDate = 1111
        )

        assertEquals(0, book.id)
    }

    @Test
    fun testBookEquality() {
        // Test that two books with same data are equal
        val book1 = Book(
            title = "Titanic",
            author = "James Cameron",
            createdDate = 1000
        )

        val book2 = Book(
            title = "Titanic",
            author = "James Cameron",
            createdDate = 1000
        )


        assertEquals(book1, book2)
    }

    @Test
    fun testBookCopyFunction() {
        // Test the copy() function of data class
        val original = Book(
            title = "Original Title",
            author = "Original Author",
            createdDate = 5000L
        )

        // Create a copy with changed title
        val modified = original.copy(title = "Modified Title")

        assertEquals("Modified Title", modified.title)
        assertEquals("Original Author", modified.author)  // Should stay same
        assertEquals(5000L, modified.createdDate)  // Should stay same
    }

    @Test
    fun testBookToString() {
        // Test toString() contains book info
        val book = Book(
            id = 99,
            title = "Test Book",
            author = "Test Author",
            createdDate = 7878
        )

        val result = book.toString()


    }
}