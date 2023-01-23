package com.example.chatcompose.ui.components

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun CustomInputField(
    @StringRes label: Int,
    icon: ImageVector,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    value: String,
    onValueChange: (String) -> Unit,
    onClearClicked: () -> Unit,
    readOnly: Boolean = false,
    modifier: Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(imageVector = icon, contentDescription = "icon")
        },
        label = { Text(stringResource(label)) },
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        trailingIcon = {

            AnimatedVisibility(
                visible = value.isNotEmpty() && !readOnly,
                enter = scaleIn(),
                exit = scaleOut()
            ) {
                IconButton(onClick = onClearClicked) {
                    Icon(
                        imageVector = Icons.Filled.Cancel,
                        contentDescription = "clear text",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        },
        readOnly = readOnly,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        colors = TextFieldDefaults.outlinedTextFieldColors()
    )
}