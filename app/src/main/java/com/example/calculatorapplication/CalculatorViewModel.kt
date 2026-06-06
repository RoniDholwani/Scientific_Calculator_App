package com.example.calculatorapplication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CalculatorViewModel : ViewModel() {

    private val _state = MutableStateFlow("")
    val state: StateFlow<String> = _state

    fun onButtonClick(value: String) {

        when (value) {

            "C" -> _state.value = ""

            "DEL" -> {
                _state.value = _state.value.dropLast(1)
            }

            "=" -> {
                _state.value = try {
                    CalculatorLogic.evaluate(_state.value)
                } catch (e: Exception) {
                    "Error"
                }
            }

            "sin", "cos", "tan", "log" -> {
                _state.value = CalculatorLogic.applyScientific(_state.value, value)
            }

            else -> {
                if (_state.value == "Error") {
                    _state.value = value
                } else {
                    _state.value += value
                }
            }
        }
    }
}