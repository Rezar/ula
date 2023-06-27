/*
package com.example.ula_app.android.ui.game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import com.example.ula_app.android.R

private const val TAG = "HelpScreenSearchBox"

@Composable
fun HelpScreenSearchBox() {

    // Obtain the questions from res
    val allQuestions = stringArrayResource(R.array.FAQs).asList()

    // This is the state of inputting text by user
    val searchText = remember {
        mutableStateOf("")
    }
    // This is a list of questions that filtered by the searchText
    val questions = remember {
        mutableStateOf(allQuestions)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = searchText.value,
            onValueChange = {
                searchText.value = it

                // Use filter to change the list of "questions" variable
                questions.value = if (searchText.value.isBlank())
                    allQuestions
                else allQuestions.filter {
                    it.contains(searchText.value, ignoreCase = true)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Search questions") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn (
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(questions.value) {
                Text(
                    text = "$it",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )
            }
        }
    }
}*/
