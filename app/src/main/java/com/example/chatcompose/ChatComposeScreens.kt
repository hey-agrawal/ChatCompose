package com.example.chatcompose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
){
    val backStackEntry by navHostController.currentBackStackEntryAsState()
    val currentScreen =
        ChatComposeScreens.valueOf(backStackEntry?.destination?.route
            ?: ChatComposeScreens.WELCOME.name)

    val title = when (currentScreen) {
        ChatComposeScreens.WELCOME -> "Welcome to ChatApp"
        ChatComposeScreens.PROFILE -> "Profile"
    }

        Scaffold(
        topBar = {
            TopAppBar {
                Text(text = title)
            }
        }
    ) {innerPadding ->
        NavHost(
            navController = navHostController,
            startDestination = ChatComposeScreens.WELCOME.name,
            modifier = modifier.padding(innerPadding),
        ) {
            composable(
                route = ChatComposeScreens.WELCOME.name,
            ) {
                WelcomeScreen(modifier = Modifier) {
                    navHostController.navigate(ChatComposeScreens.PROFILE.name)
                }
            }
            composable(
                route = ChatComposeScreens.PROFILE.name,
            ) {
                ProfileScreen()
            }

        }
    }
}


