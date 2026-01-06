package com.folius.calc

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var display: TextView
    private var currentNumber = ""
    private var previousNumber = ""
    private var operation = ""
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        display = findViewById(R.id.display)
        display.text = "0"

        // Number buttons
        val button0 = findViewById<Button>(R.id.button0)
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)
        val button5 = findViewById<Button>(R.id.button5)
        val button6 = findViewById<Button>(R.id.button6)
        val button7 = findViewById<Button>(R.id.button7)
        val button8 = findViewById<Button>(R.id.button8)
        val button9 = findViewById<Button>(R.id.button9)

        // Operation buttons
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        val buttonSubtract = findViewById<Button>(R.id.buttonSubtract)
        val buttonMultiply = findViewById<Button>(R.id.buttonMultiply)
        val buttonDivide = findViewById<Button>(R.id.buttonDivide)
        val buttonEquals = findViewById<Button>(R.id.buttonEquals)
        val buttonClear = findViewById<Button>(R.id.buttonClear)
        val buttonDot = findViewById<Button>(R.id.buttonDot)

        // Set click listeners for number buttons
        button0.setOnClickListener { numberPressed("0") }
        button1.setOnClickListener { numberPressed("1") }
        button2.setOnClickListener { numberPressed("2") }
        button3.setOnClickListener { numberPressed("3") }
        button4.setOnClickListener { numberPressed("4") }
        button5.setOnClickListener { numberPressed("5") }
        button6.setOnClickListener { numberPressed("6") }
        button7.setOnClickListener { numberPressed("7") }
        button8.setOnClickListener { numberPressed("8") }
        button9.setOnClickListener { numberPressed("9") }

        // Set click listeners for operation buttons
        buttonAdd.setOnClickListener { operationPressed("+") }
        buttonSubtract.setOnClickListener { operationPressed("-") }
        buttonMultiply.setOnClickListener { operationPressed("×") }
        buttonDivide.setOnClickListener { operationPressed("÷") }
        buttonEquals.setOnClickListener { equalsPressed() }
        buttonClear.setOnClickListener { clearPressed() }
        buttonDot.setOnClickListener { dotPressed() }
    }

    private fun numberPressed(number: String) {
        if (isNewOperation) {
            currentNumber = number
            isNewOperation = false
        } else {
            // Prevent leading zeros
            if (currentNumber == "0") {
                currentNumber = number
            } else {
                currentNumber += number
            }
        }
        display.text = currentNumber
    }

    private fun dotPressed() {
        if (isNewOperation) {
            currentNumber = "0."
            isNewOperation = false
        } else if (!currentNumber.contains(".")) {
            currentNumber += "."
        }
        display.text = currentNumber
    }

    private fun operationPressed(op: String) {
        if (currentNumber.isNotEmpty()) {
            if (previousNumber.isNotEmpty() && operation.isNotEmpty() && !isNewOperation) {
                equalsPressed()
            }
            previousNumber = currentNumber
            operation = op
            isNewOperation = true
        }
    }

    private fun equalsPressed() {
        if (previousNumber.isNotEmpty() && currentNumber.isNotEmpty() && operation.isNotEmpty()) {
            val prev = previousNumber.toDoubleOrNull()
            val curr = currentNumber.toDoubleOrNull()
            
            if (prev == null || curr == null) {
                display.text = "Error"
                resetCalculator()
                return
            }
            
            val result = when (operation) {
                "+" -> prev + curr
                "-" -> prev - curr
                "×" -> prev * curr
                "÷" -> {
                    if (curr == 0.0) {
                        display.text = "Error"
                        resetCalculator()
                        return
                    } else {
                        prev / curr
                    }
                }
                else -> curr
            }
            
            // Format result to avoid unnecessary decimal places
            currentNumber = if (result == result.toLong().toDouble()) {
                result.toLong().toString()
            } else {
                result.toString()
            }
            
            display.text = currentNumber
            operation = ""
            previousNumber = ""
            isNewOperation = true
        }
    }

    private fun clearPressed() {
        resetCalculator()
        display.text = "0"
    }

    private fun resetCalculator() {
        currentNumber = ""
        previousNumber = ""
        operation = ""
        isNewOperation = true
    }
}
