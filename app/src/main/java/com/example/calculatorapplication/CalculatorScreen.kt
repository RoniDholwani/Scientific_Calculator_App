package com.example.calculatorapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester

@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val state by viewModel.state.collectAsState()
    val focusRequester = remember { FocusRequester() }

    // Auto-focus so keyboard works immediately
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(12.dp)
            .focusRequester(focusRequester)

            // 🔥 KEYBOARD SUPPORT (FINAL)
            .onPreviewKeyEvent { event ->

                if (event.type == KeyEventType.KeyDown) {

                    val keyEvent = event.nativeKeyEvent
                    val char = keyEvent.unicodeChar.toChar()

                    when {

                        // ENTER → =
                        event.key == Key.Enter -> {
                            viewModel.onButtonClick("=")
                        }

                        // BACKSPACE → DEL
                        event.key == Key.Backspace -> {
                            viewModel.onButtonClick("DEL")
                        }

                        // ESC → Clear
                        event.key == Key.Escape -> {
                            viewModel.onButtonClick("C")
                        }

                        // Numbers
                        char.isDigit() -> {
                            viewModel.onButtonClick(char.toString())
                        }

                        // Operators
                        char in listOf('+', '-', '*', '/', '.', '(', ')') -> {
                            viewModel.onButtonClick(char.toString())
                        }

                        // Scientific shortcuts
                        char == 's' -> viewModel.onButtonClick("sin")
                        char == 'c' -> viewModel.onButtonClick("cos")
                        char == 't' -> viewModel.onButtonClick("tan")
                        char == 'l' -> viewModel.onButtonClick("log")

                        else -> {}
                    }
                }
                true
            },

        verticalArrangement = Arrangement.SpaceBetween
    ) {

        // 🔢 DISPLAY
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = if (state.isEmpty()) "0" else state,
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge
            )
        }

        // 🔘 BUTTONS
        val buttons = listOf(
            listOf("C", "(", ")", "/"),
            listOf("7", "8", "9", "*"),
            listOf("4", "5", "6", "-"),
            listOf("1", "2", "3", "+"),
            listOf("0", ".", "=", "sin"),
            listOf("cos", "tan", "log", "DEL")
        )

        Column {
            buttons.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { symbol ->
                        CalculatorButton(symbol) {
                            viewModel.onButtonClick(symbol)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(symbol: String, onClick: () -> Unit) {

    val bgColor = when (symbol) {
        "=", "+", "-", "*", "/", "sin", "cos", "tan", "log" -> Color(0xFF754502)
        "C" -> Color.Gray
        else -> Color.DarkGray
    }

    Button(
        onClick = onClick,
        shape = CircleShape,
        modifier = Modifier
            .padding(6.dp)
            .size(70.dp),
        colors = ButtonDefaults.buttonColors(containerColor = bgColor)
    ) {
        Text(
            text = symbol,
            color = Color.White
        )
    }
}