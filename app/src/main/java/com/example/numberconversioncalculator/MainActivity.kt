package com.example.numberconversioncalculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var editTextDec: EditText
    private lateinit var editTextBin: EditText
    private lateinit var editTextHex: EditText
    private lateinit var editTextOct: EditText
    private lateinit var outputeditTV: List<EditText>

    @SuppressLint("MissingInflatedId", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextHex = findViewById(R.id.editTextHex)
        editTextBin = findViewById(R.id.editTextBin)
        editTextDec = findViewById(R.id.editTextDec)
        editTextOct = findViewById(R.id.editTextOct)

        outputeditTV = listOf(
            findViewById(R.id.editTextHex),
            findViewById(R.id.editTextBin),
            findViewById(R.id.editTextDec),
            findViewById(R.id.editTextOct)
        )

        on_change(editTextHex, outputeditTV, Type.HEX)
        on_change(editTextBin, outputeditTV, Type.BIN)
        on_change(editTextDec, outputeditTV, Type.DEC)
        on_change(editTextOct, outputeditTV, Type.OCT)


    }

    fun on_change(editText: EditText, outputText: List<EditText>, type: Type) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val input = s.toString()

                val converter = Converter()
                val decOutput = converter.getNumber(type, Type.DEC, input)
                outputText[0].setText(decOutput)

                val binOutput = converter.getNumber(type, Type.BIN, input)
                outputText[1].setText(binOutput)

                val hexOutput = converter.getNumber(type, Type.HEX, input)
                outputText[2].setText(hexOutput)

                val octOutput = converter.getNumber(type, Type.OCT, input)
                outputText[3].setText(octOutput)
            }
        })
    }

    private fun getInputType(input: String): Type {
        return when {
            input.matches("[01]+".toRegex()) -> Type.BIN
            input.matches("[0-7]+".toRegex()) -> Type.OCT
//            input.matches("[0-9]+".toRegex()) -> Type.DEC
            input.matches("[0-9A-Fa-f]+".toRegex()) -> Type.HEX
            else -> Type.DEC
        }
    }
}