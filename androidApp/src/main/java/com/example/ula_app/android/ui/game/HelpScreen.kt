package com.example.ula_app.android.ui.game

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.res.stringResource
import com.example.ula_app.data.DataSource
import com.example.ula_app.presentation.UiFAQ

private const val TAG = "HelpScreen"

@ExperimentalMaterialApi
@Composable
@Preview
fun HelpScreen() {


    /*
    * TODO: remove the UiFAQ, CommonFAQ. We will iterate to display the questions and answers
    *  for Android and IOS separately
    * */
//    var answers = listOf(
//        Answer1(),
//        Answer2(),
//        Answer3(),
//        Answer4(),
//        Answer5(),
//        Answer6()
//    )


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "FAQs",
            style = MaterialTheme.typography.h1
        )
        LazyColumn {
            itemsIndexed(UiFAQ.allFAQs) { index, questionAnswer ->
                ExpandableCard(
                    id = index,
                    title = questionAnswer.faq.question,
                    description = questionAnswer.faq.answer
//                    description = answers.get(index) // TODO: After change the UiFAQ and CommonFAQ
                )
            }
        }

    }

}

@ExperimentalMaterialApi
@Composable
fun ExpandableCard(
    id: Int,
    title: String,
//    description: @Composable () -> Unit,   // TODO: After change the UiFAQ and CommonFAQ
    description: String,
    titleFontSize: TextUnit = MaterialTheme.typography.h6.fontSize,
    titleFontWeight: FontWeight = FontWeight.Bold,
    descriptionFontSize: TextUnit = MaterialTheme.typography.subtitle1.fontSize,
    descriptionFontWeight: FontWeight = FontWeight.Normal,
    descriptionMaxLines: Int = 4,
    shape: Shape = RoundedCornerShape(4.dp),
    padding: Dp = 12.dp
){

    var expandedState by remember {
        mutableStateOf(false)
    }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = shape,
        onClick = {
            expandedState = !expandedState
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(6f),
                    text = title,
                    fontSize = titleFontSize,
                    fontWeight = titleFontWeight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(
                    modifier = Modifier
                        .weight(1f)
                        .alpha(ContentAlpha.medium)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }
            if (expandedState) {
                /*
                *TODO: Write different composable function for each questions here!
                * if we have some design or drawings for some answers.
                * */

                Text(
                    text = description,
                    fontSize = descriptionFontSize,
                    fontWeight = descriptionFontWeight,
                    maxLines = descriptionMaxLines,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun Answer1(){

}

@Composable
fun Answer2(){

}

@Composable
fun Answer3(){

}

@Composable
fun Answer4(){

}

@Composable
fun Answer5(){

}

@Composable
fun Answer6(){

}



