package com.example.ula_app.android.ui.game

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.ula_app.android.ULAApplication
import com.example.ula_app.android.ui.component.SettingFieldsRow
import com.example.ula_app.android.ui.viewmodel.UserPreferencesViewModel
import java.io.IOException

@Composable
fun SettingAdvancedScreen(
    onBackClicked: () -> Unit = {}
) {

    val context = LocalContext.current
    val userPreferencesViewModel: UserPreferencesViewModel = ULAApplication.getInstance<UserPreferencesViewModel>()
    val userPreUiState by userPreferencesViewModel.userPreferencesState.collectAsState()

    var maxThreshold by remember {
        mutableStateOf(TextFieldValue(userPreUiState.maxThreshold.toString()))
    }

    var minThreshold by remember {
        mutableStateOf(TextFieldValue(userPreUiState.minThreshold.toString()))
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {

        BackHandler(true) {
            onBackClicked()
        }
        IconButton(
            onClick = {
                onBackClicked()
            }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null
            )
        }

        SettingFieldsRow(
            text = "Max Threshold (Percentage)",
            input = {
                TextField(
                    modifier = Modifier.width(80.dp),
                    value = maxThreshold,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    onValueChange = { newText ->
                        maxThreshold = newText
                    }
                )
            }
        )

        SettingFieldsRow(
            text = "Min Threshold (Percentage)",
            input = {
                TextField(
                    modifier = Modifier.width(80.dp),
                    value = minThreshold,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    onValueChange = { newText ->
                        minThreshold = newText
                    }
                )
            }
        )

        SettingFieldsRow(
            text = "",
            input = {
                Button(
                    onClick = {

                        when {
                            maxThreshold.text.toDouble() in 0.0..0.5
                                    && minThreshold.text.toDouble() in 0.0..0.5 -> {
                                try {
                                    // Update datastore
                                    userPreferencesViewModel.setMaxThreshold(maxThreshold.text.toDouble())
                                    userPreferencesViewModel.setMinThreshold(minThreshold.text.toDouble())
                                    Toast.makeText(
                                        context,
                                        "Setting saved successfully",
                                        Toast.LENGTH_LONG,
                                    ).show()
                                } catch (e: IOException) {
                                    Toast.makeText(
                                        context,
                                        "Setting saved failure",
                                        Toast.LENGTH_LONG,
                                    ).show()
                                }
                            }
                            else -> {
                                Toast.makeText(
                                    context,
                                    "Please enter a decimal number between 0 and 0.5 for the min and max thresholds! ",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
                ){
                    Text(
                        text = "Save!",
                        style = MaterialTheme.typography.caption
                    )
                }
            }
        )


/*        Row(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {

        }*/
    }


}