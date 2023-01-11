package com.example.chatcompose

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

enum class ChatComposeScreens {
    WELCOME, PROFILE,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatApp(
    modifier: Modifier,
    navHostController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navHostController.currentBackStackEntryAsState()
    val currentScreen =
        ChatComposeScreens.valueOf(backStackEntry?.destination?.route
            ?: ChatComposeScreens.WELCOME.name)

    val title = when (currentScreen) {
        ChatComposeScreens.WELCOME -> "Welcome to ChatApp"
        ChatComposeScreens.PROFILE -> "Profile"
    }
    var pickedPhoto: Uri? by remember { mutableStateOf(null) }
    val pickPhoto =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                pickedPhoto = uri
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                title = {
                    Text(text = title)
                }
            )

        }
    ) { innerPadding ->
        NavHost(
            navController = navHostController,
            startDestination = ChatComposeScreens.WELCOME.name,
            modifier = modifier.padding(innerPadding),
        ) {
            composable(
                route = ChatComposeScreens.WELCOME.name,
            ) {
                WelcomeScreen(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                    navHostController.navigate(ChatComposeScreens.PROFILE.name)
                }
            }
            composable(
                route = ChatComposeScreens.PROFILE.name,
            ) {
                ProfileScreen(
                    onSaveClicked = {},
                    onPhotoClicked = {
                        pickPhoto.launch(PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly))
                    },
                    photoUri = pickedPhoto,
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                )
            }
        }
    }
}


