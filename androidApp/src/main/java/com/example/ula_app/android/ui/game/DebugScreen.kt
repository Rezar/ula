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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ula_app.android.data.DataSource
import com.example.ula_app.android.ui.viewmodel.DebugViewModel
import com.example.ula_app.android.ui.viewmodel.HomeViewModel

private const val TAG = "DebugScreen"

private const val leftWeight = 0.3f
private const val rightWeight = 1 - leftWeight

@Composable
fun DebugScreen(
        homeViewModel: HomeViewModel = viewModel(),
        debugViewModel: DebugViewModel = viewModel()
) {
    val ageOptions = listOf(
        "",
        DataSource.MonsterAgeOptions.Egg.name,
        DataSource.MonsterAgeOptions.Child.name,
        DataSource.MonsterAgeOptions.Adult.name
    )

    val bodyStatusOptions = listOf(
        "",
        DataSource.MonsterBodyStatusOptions.NA.name,
        DataSource.MonsterBodyStatusOptions.Normal.name,
        DataSource.MonsterBodyStatusOptions.Overweight.name,
        DataSource.MonsterBodyStatusOptions.Fat.name,
        DataSource.MonsterBodyStatusOptions.Fit.name,
    )


    val movieIdsOptions = mutableListOf<String>()
    movieIdsOptions.add("")
    movieIdsOptions += DataSource.eggRule + DataSource.childNormalRule + DataSource.childFatRule +
            DataSource.childOverweightRule + DataSource.adultNormalRule + DataSource.adultFatRule +
            DataSource.adultFitRule + DataSource.adultOverweightRule

    var ageOptionExpanded by remember {
        mutableStateOf(false)
    }

    var bodyStatusOptionExpanded by remember {
        mutableStateOf(false)
    }

    var movieIdOptionExpanded by remember {
        mutableStateOf(false)
    }

    var ageOptionSelectedIndex by remember {
        mutableStateOf(ageOptions[0])
    }
    var bodyStatusOptionSelectedIndex by remember {
        mutableStateOf(bodyStatusOptions[0])
    }
    var movieIdOptionSelectedIndex by remember {
        mutableStateOf(movieIdsOptions[0])
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
//        horizontalArrangement = Arrangement.Center,
    ) {

        // Specify the title
        Text(
            text = "Debug and Stay ALIVE!",
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(35.dp))

        // Create dropdown boxes
        Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Dropdown(
                selectedIndex = ageOptionSelectedIndex,
                expand = ageOptionExpanded,
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
                selectedIndex = bodyStatusOptionSelectedIndex,
                expand = bodyStatusOptionExpanded,
                dropdownTitle = "Body Status",
                dropdownOptions = bodyStatusOptions,
                onDropdownClicked = {
                    bodyStatusOptionExpanded = it
                },
                onDropdownItemClicked = {
                    bodyStatusOptionSelectedIndex = it
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Dropdown(
                selectedIndex = movieIdOptionSelectedIndex,
                expand = movieIdOptionExpanded,
                dropdownTitle = "movie Id",
                dropdownOptions = movieIdsOptions,
                onDropdownClicked = {
                    movieIdOptionExpanded = it
                },
                onDropdownItemClicked = {
                    movieIdOptionSelectedIndex = it
                }
            )

            Button(
                onClick = {
                    if(debugViewModel.isOnlyChangeId(ageOptionSelectedIndex, bodyStatusOptionSelectedIndex, movieIdOptionSelectedIndex)){
                        // change the movie with id only
                        homeViewModel.setId(movieIdOptionSelectedIndex)
                    } else {
                        // change the age and body status
                        homeViewModel.setAgeAndBodyStatus(ageOptionSelectedIndex, bodyStatusOptionSelectedIndex)
                    }
                },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
                enabled = debugViewModel.isEnabledButton(ageOptionSelectedIndex, bodyStatusOptionSelectedIndex, movieIdOptionSelectedIndex)

            ) {
                Text(text = "Change the movie! ")
            }
        }
    }
}

@Composable
fun Dropdown(
    selectedIndex: String,
    expand: Boolean,
    dropdownTitle: String,
    dropdownOptions: List<String>,
    onDropdownClicked: (Boolean) -> Unit,
    onDropdownItemClicked: (String) -> Unit,
    enable: Boolean = true
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
                //text = AnnotatedString(dropdownOptions[selectedIndex]), // get the options text using the index
                text = AnnotatedString(selectedIndex),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    color = Color.White
                ),
                onClick = {

                    if(enable) {
                        onDropdownClicked(true)
                    }
                },
                modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(Color.DarkGray)
            )
            DropdownMenu(
                expanded = expand,
                onDismissRequest = { onDropdownClicked(false) },
                modifier = Modifier
                        .fillMaxWidth(0.56f)
                        .background(Color.White)
            ) {

                dropdownOptions.forEachIndexed { index, s ->
                    DropdownMenuItem(
                        onClick = {
                            onDropdownItemClicked(s)
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