package com.example.numberconversioncalculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var outputTextViews: List<TextView>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)
        outputTextViews = listOf(
            findViewById(R.id.output1),
            findViewById(R.id.output2),
            findViewById(R.id.output3),
            findViewById(R.id.output4)
        )

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val input = s.toString()

                val converter = Converter()
                if (input.isEmpty()) {
                    outputTextViews[0].text = " :p"
                    outputTextViews[1].text = " :p"
                    outputTextViews[2].text = " :p"
                    outputTextViews[3].text = " :p"
                } else {
                    val decOutput = converter.getNumber(Type.HEX, Type.DEC, input)
                    outputTextViews[0].text = decOutput

                    val binOutput = converter.getNumber(Type.HEX, Type.BIN, input)
                    outputTextViews[1].text = binOutput

                    val hexOutput = converter.getNumber(Type.HEX, Type.HEX, input)
                    outputTextViews[2].text = hexOutput

                    val octOutput = converter.getNumber(Type.HEX, Type.OCT, input)
                    outputTextViews[3].text = octOutput
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun getInputType(input: String): Type {
        return when {
            input.matches("[01]+".toRegex()) -> Type.BIN
            input.matches("[0-7]+".toRegex()) -> Type.OCT
            input.matches("[0-9]+".toRegex()) -> Type.DEC
            input.matches("[0-9A-Fa-f]+".toRegex()) -> Type.HEX
            else -> Type.DEC
        }
    }
}