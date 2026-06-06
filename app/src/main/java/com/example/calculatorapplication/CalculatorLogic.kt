package com.example.calculatorapplication

import kotlin.math.*

object CalculatorLogic {

    fun evaluate(expression: String): String {
        return try {
            val result = SimpleExpressionEvaluator.evaluate(expression)
            result.toString()
        } catch (e: Exception) {
            "Error"
        }
    }

    fun applyScientific(value: String, function: String): String {
        return try {
            val number = value.toDouble()

            when (function) {
                "sin" -> sin(Math.toRadians(number)).toString()
                "cos" -> cos(Math.toRadians(number)).toString()
                "tan" -> tan(Math.toRadians(number)).toString()
                "log" -> log10(number).toString()
                else -> value
            }

        } catch (e: Exception) {
            "Error"
        }
    }
}