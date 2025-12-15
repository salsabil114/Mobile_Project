package com.example.book_lib_app

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BookListEspressoTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun appLaunchesSuccessfully() {
        //test if app launches
    }

    @Test
    fun testMainTitleExists() {

        composeTestRule.onNodeWithText("My Book Library")
            .assertExists()

    }

    @Test
    fun testSearchHintExists() {
        composeTestRule.onNodeWithText("Search by book title")
            .assertExists()
    }

    @Test
    fun testAddButtonExistsAndClickable() {
        //test the Add button
        composeTestRule.onNodeWithText("Add Book")
            .assertExists()
            .performClick()
    }

    @Test
    fun testPlaceholderTextExists() {

        composeTestRule.onNodeWithText("Book Title")
            .assertExists()


        composeTestRule.onNodeWithText("Author")
            .assertExists()
    }
}