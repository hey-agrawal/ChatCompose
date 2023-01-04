package com.example.chatcompose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GenderRow(gender: String, selected: Boolean, onClicked: () -> Unit, modifier: Modifier){
    val color = if(selected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.outline
    Row(modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start) {
        RadioButton(selected = selected, onClick = onClicked, colors = RadioButtonDefaults.colors(
            selectedColor = MaterialTheme.colorScheme.onPrimaryContainer,
            unselectedColor = MaterialTheme.colorScheme.outline
        ))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = gender, color = color)
    }
}