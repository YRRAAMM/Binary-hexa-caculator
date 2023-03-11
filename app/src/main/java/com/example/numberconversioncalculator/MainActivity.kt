package com.example.numberconversioncalculator


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    private lateinit var editTextDec: EditText
    private lateinit var editTextBin: EditText
    private lateinit var editTextHex: EditText
    private lateinit var editTextOct: EditText
    private lateinit var outputeditTV: List<EditText>

    private var ignoreChanges = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextHex = findViewById(R.id.editTextHex)
        editTextBin = findViewById(R.id.editTextBin)
        editTextDec = findViewById(R.id.editTextDec)
        editTextOct = findViewById(R.id.editTextOct)

        outputeditTV = listOf(
            editTextHex,
            editTextBin,
            editTextDec,
            editTextOct
        )

        setupTextWatchers()
    }

    private fun setupTextWatchers() {
        for (i in outputeditTV.indices) {
            val editText = outputeditTV[i]
            val type = Type.values()[i]

            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (ignoreChanges) return


                    val input = s.toString()

                    val converter = Converter()
                    var isError = false // track if input is invalid

                    ignoreChanges = true

                    // Check if input is valid
                    when (type) {
                        Type.BIN -> isError = !input.matches("[01]+".toRegex())
                        Type.OCT -> isError = !input.matches("[0-7]+".toRegex())
                        Type.HEX -> isError = !input.matches("[0-9A-Fa-f]+".toRegex())
                        Type.DEC -> isError = input.isEmpty()
                    }

                    // If input is invalid, show error message and don't update other fields
                    if (isError) {
                        if (input.isEmpty()) { // Reset all fields if input is empty
                            for (j in outputeditTV.indices) {
                                val outputEditText = outputeditTV[j]
                                outputEditText.setText("")
                                outputEditText.error = null
                            }
                        } else { // Show error message and don't update other fields
                            editText.error = "Invalid input"
                        }
                    } else {
                        // Update other fields
                        for (j in outputeditTV.indices) {
                            if (i == j) continue

                            val outputEditText = outputeditTV[j]
                            val outputType = Type.values()[j]
                            val output = converter.getNumber(type, outputType, input)
                            outputEditText.setText(output)
                            outputEditText.error = null // remove error message from other fields
                        }
                    }

                    ignoreChanges = false
                }
            })
        }
    }

}