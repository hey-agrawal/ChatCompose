package com.example.chatcompose

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.chatcompose.components.CustomInputField
import com.example.chatcompose.components.GenderRow
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun ProfileScreen(
    onSaveClicked: () -> Unit,
    onPhotoClicked: () -> Unit,
    photoUri: Uri?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val focusManager = LocalFocusManager.current
        var name by rememberSaveable { mutableStateOf("") }
        var bio by rememberSaveable { mutableStateOf("") }
        var dob by rememberSaveable { mutableStateOf("") }
        var gender by rememberSaveable { mutableStateOf("") }

        Button(
            onClick = onPhotoClicked,
            shape = CircleShape,
            modifier = Modifier.size(80.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Box(modifier = Modifier,
                contentAlignment = Alignment.Center
            ) {
                if (photoUri != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(photoUri)
                            .crossfade(true)
                            .build(),
                        modifier = Modifier
                            .size(80.dp),
                        contentScale = ContentScale.Crop,
                        contentDescription = "image",
                    )
                }
                else {
                    Column(
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(imageVector = Icons.Rounded.AddAPhoto, contentDescription = "Icon")
                        androidx.compose.material3.Text(modifier = Modifier
                            .fillMaxWidth(),
                            text = "Add photo",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Normal,
                                letterSpacing = 0.1.sp,
                                textAlign = TextAlign.Center
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
        CustomInputField(
            label = R.string.name_label,
            icon = Icons.Rounded.Person,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words,
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            value = name,
            onValueChange = { name = it },
            onClearClicked = { name = "" },
            modifier = Modifier.fillMaxWidth()
        )
        CustomInputField(
            label = R.string.email,
            icon = Icons.Rounded.Email,
            readOnly = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            value = Firebase.auth.currentUser?.email.toString(),
            onValueChange = {},
            onClearClicked = {},
            modifier = Modifier.fillMaxWidth()
        )
        CustomInputField(
            label = R.string.bio,
            icon = Icons.Rounded.Info,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Words,
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            value = bio,
            onValueChange = { bio = it },
            onClearClicked = { bio = "" },
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 12.dp)
                .background(color = MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialTheme.shapes.small),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            val genderList = listOf("Male", "Female")
            var selectedGender by rememberSaveable { mutableStateOf("") }

            Text(text = "Gender", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp))
            Spacer(modifier = Modifier.height(12.dp))
            genderList.forEach { item ->
                GenderRow(
                    gender = item,
                    selected = selectedGender == item,
                    onClicked = {
                        selectedGender = item
                        gender = item
                    },
                    modifier = Modifier.selectable(
                        selected = selectedGender == item,
                        onClick = {
                            selectedGender = item
                            gender = item
                        },
                        role = Role.RadioButton
                    )
                )
            }

        }
        CustomInputField(
            label = R.string.dob,
            icon = Icons.Rounded.CalendarMonth,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Words,
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            value = dob,
            onValueChange = { dob = it },
            onClearClicked = { dob = "" },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onSaveClicked,
            colors = ButtonDefaults.buttonColors(),
            modifier = Modifier.fillMaxWidth(),
            enabled = name.isNotEmpty() && gender.isNotEmpty() && dob.isNotEmpty() && bio.isNotEmpty()
        ) {
            Text(text = "SAVE")
        }
    }
}

