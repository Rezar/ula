package com.example.ula_app.android.ui.welcome

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ula_app.android.R
import com.example.ula_app.android.ui.component.IconButton

@Composable
fun WelcomePage1(
    // A callback function when next button is clicked.
    onNextButtonClicked: () -> Unit
) {
    // A state
    // True if agree the terms and conditions, False if disagree. Initially, false.
    val checkedState = remember {
        mutableStateOf(false)
    }

    // Read icon for next button from drawable folder.
    val nextButtonIcon = painterResource(id = R.drawable.next)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .padding(
                    start = Default.padding.start,
                    end = Default.padding.end,
                    top = Default.padding.top,
                    bottom = Default.padding.bottom
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "WELCOME!",
                    style = MaterialTheme.typography.h1
                )
            }

            Spacer(modifier = Modifier.height(60.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "A magic has happened and now you own an egg of a monster, ULA!",
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.height(60.dp))
                Text(
                    text = "As much as you stay committed to your daily walk/run routine your ULA is happy, being lazy makes the ULA unhappy.",
                    style = MaterialTheme.typography.body1
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    // property represents the checkbox is checked or not.
                    checked = checkedState.value,
                    // Actions triggered when checkbox state is changed.
                    onCheckedChange = {
                        checkedState.value = it
                    }
                )
                Text(
                    text = "I agree with Terms of Service / Privacy Policy",
                    style = MaterialTheme.typography.caption
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    enabled = checkedState.value,
                    Icon = {
                        Icon(
                            painter = nextButtonIcon,
                            contentDescription = "Next"
                        )
                    },
                    onClick = {
                        Log.i("WelcomePage_Page1", "Click Next on the Page 1")
                        if (checkedState.value) {
                            onNextButtonClicked()
                            Log.i("WelcomePage_Page1", "Move to next page.")
                        } else {
                            /* TODO */
//                        Toast.makeText(this, "Agree on the Terms and Condition before using the App!", Toast.LENGTH_LONG).show()
                            Log.i("WelcomePage_Page1", "CheckBox is Unchecked.")
                        }
                    }
                )
            }
        }
    }
}