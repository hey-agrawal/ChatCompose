package com.example.chatcompose.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

enum class ComposeScreens {
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
        ComposeScreens.valueOf(
            backStackEntry?.destination?.route
                ?: ComposeScreens.WELCOME.name
        )

    val title = when (currentScreen) {
        ComposeScreens.WELCOME -> "Welcome to ChatApp"
        ComposeScreens.PROFILE -> "Profile"
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
        var user by rememberSaveable { mutableStateOf(Firebase.auth.currentUser) }
        val startDestination = if(user != null ) ComposeScreens.PROFILE.name else ComposeScreens.WELCOME.name
        NavHost(
            navController = navHostController,
            startDestination = startDestination,
            modifier = modifier.padding(innerPadding),
        ) {

            composable(
                route = ComposeScreens.WELCOME.name,
            ) {
                WelcomeScreen(modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                    navigateToProfile = {
                        navHostController.navigate(ComposeScreens.PROFILE.name){
                           popUpTo(ComposeScreens.PROFILE.name){
                               inclusive = false
                           }
                        }
                    }
                )
            }
            composable(
                route = ComposeScreens.PROFILE.name,
            ) {
                EditProfileScreen(
                    onSaveClicked = {},
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                )
            }
        }
    }
}


