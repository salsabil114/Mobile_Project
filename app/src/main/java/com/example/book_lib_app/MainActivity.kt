package com.example.book_lib_app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.book_lib_app.ui.theme.Book_lib_appTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = BookDatabase.getDatabase(this)
        val bookDao = db.bookDao()
        val viewModel = ViewModelProvider(
            this,
            BookViewModelFactory(bookDao)
        )[BookViewModel::class.java]

        setContent {
            Book_lib_appTheme {
                BookListScreen(viewModel)
            }
        }
    }
}

@Composable
fun BookListScreen(viewModel: BookViewModel) {

    val books by viewModel.books.collectAsState()
    val context = LocalContext.current

    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5E8FF))
            .padding(20.dp)
    ) {

        Text(
            text = "My Book Library",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFF6A1B9A)
        )

        Spacer(modifier = Modifier.height(12.dp))

        //  Search Bar
        TextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.updateSearchQuery(it)
            },
            placeholder = { Text("Search by book title") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFFBA68C8),
                unfocusedIndicatorColor = Color(0xFFCE93D8),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        //  Book Title
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Book Title") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFFBA68C8),
                focusedLabelColor = Color(0xFF6A1B9A),
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // ✍️ Author
        TextField(
            value = author,
            onValueChange = { author = it },
            label = { Text("Author") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFFBA68C8),
                focusedLabelColor = Color(0xFF6A1B9A),
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        //  Add Book
        Button(
            onClick = {
                if (title.isNotBlank() && author.isNotBlank()) {
                    viewModel.addBook(title, author)
                    title = ""
                    author = ""
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8E24AA))
        ) {
            Text("Add Book", color = Color.White)
        }

        Spacer(modifier = Modifier.height(25.dp))

        //  Book List
        LazyColumn {
            items(books) { book ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "${book.title} — ${book.author}",
                        fontSize = 18.sp,
                        color = Color(0xFF4A148C),
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                val intent = Intent(
                                    context,
                                    BookDetailsActivity::class.java
                                )
                                intent.putExtra("title", book.title)
                                intent.putExtra("author", book.author)
                                context.startActivity(intent)
                            }
                    )

                    Button(
                        onClick = { viewModel.deleteBook(book) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD81B60)
                        )
                    ) {
                        Text("Delete", color = Color.White)
                    }
                }

                Divider(
                    color = Color(0xFFCE93D8),
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
