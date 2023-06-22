package com.example.ula_app.android.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ula_app.android.data.DataSource

private const val TAG = "DebugScreen"

private const val leftWeight = 0.3f
private const val rightWeight = 1 - leftWeight

@Composable
fun DebugScreen() {
    var ageOptionExpanded by remember {
        mutableStateOf(false)
    }

    var bodyStatusOptionExpanded by remember {
        mutableStateOf(false)
    }

    var ageOptionSelectedIndex by remember {
        mutableStateOf(0)
    }
    var bodyStatusOptionSelectedIndex by remember {
        mutableStateOf(0)
    }

    val ageOptions = listOf(
        DataSource.MonsterAgeOptions.Egg.name,
        DataSource.MonsterAgeOptions.Child.name,
        DataSource.MonsterAgeOptions.Adult.name
    )

    val bodyStatusOptions = listOf(
        DataSource.MonsterBodyStatusOptions.NA.name,
        DataSource.MonsterBodyStatusOptions.Normal.name,
        DataSource.MonsterBodyStatusOptions.Overweight.name,
        DataSource.MonsterBodyStatusOptions.Fat.name,
        DataSource.MonsterBodyStatusOptions.Fit.name,
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "This is $TAG", fontSize = 18.sp)

        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Dropdown(
                initialDropdownOption = ageOptions[ageOptionSelectedIndex],
                initialExpand = ageOptionExpanded,
                dropdownTitle = "Age",
                dropdownOptions = ageOptions,
                onDropdownClicked = {
                    ageOptionExpanded = it
                },
                onDropdownItemClicked = {
                    ageOptionSelectedIndex = it
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Dropdown(
                initialDropdownOption = bodyStatusOptions[ageOptionSelectedIndex],
                initialExpand = bodyStatusOptionExpanded,
                dropdownTitle = "Body Status",
                dropdownOptions = bodyStatusOptions,
                onDropdownClicked = {
                    bodyStatusOptionExpanded = it
                },
                onDropdownItemClicked = {
                    bodyStatusOptionSelectedIndex = it
                }
            )

        }
    }
}

@Composable
fun Dropdown(
    initialDropdownOption: String,
    initialExpand: Boolean,
    dropdownTitle: String,
    dropdownOptions: List<String>,
    onDropdownClicked: (Boolean) -> Unit,
    onDropdownItemClicked: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(leftWeight)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "${dropdownTitle}")
        }
        Column(
            modifier = Modifier
                .weight(rightWeight)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ClickableText(
                text = AnnotatedString(initialDropdownOption),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    color = Color.White
                ),
                onClick = {
                    onDropdownClicked(true)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.DarkGray)
            )
            DropdownMenu(
                expanded = initialExpand,
                onDismissRequest = { onDropdownClicked(false) },
                modifier = Modifier
                    .fillMaxWidth(0.56f)
                    .background(Color.White)
            ) {

                dropdownOptions.forEachIndexed { index, s ->
                    DropdownMenuItem(
                        onClick = {
                            onDropdownItemClicked(index)
                            onDropdownClicked(false)
                        },
                        modifier = Modifier
                            .fillMaxWidth()

                    ) {
                        Text(text = "$s")
                    }
                }
            }
        }
    }
}