package com.example.chatcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import com.example.chatcompose.ui.screens.ChatApp
import com.example.chatcompose.ui.theme.ChatComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatComposeTheme {
                ChatApp(modifier = Modifier)
            }
        }
    }
}

