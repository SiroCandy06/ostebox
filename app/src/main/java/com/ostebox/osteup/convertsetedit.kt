package com.ostebox.osteup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [convertsetedit.newInstance] factory method to
 * create an instance of this fragment.
 */
class convertsetedit : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_convertsetedit, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment convertsetedit.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            convertsetedit().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}


class MainConvert : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_convertsetedit)

        val options = arrayOf("secure", "global", "system")
        val spinnerOptions = findViewById<Spinner>(R.id.spinnerOptions)



        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerOptions.adapter = adapter

        spinnerOptions.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedOption = options[position]
                customGenerateCode(selectedOption)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        val generateButton = findViewById<Button>(R.id.generateButton)

        generateButton.setOnClickListener {
            val selectedOption = spinnerOptions.selectedItem.toString()
            customGenerateCode(selectedOption)
        }
    }

    private fun customGenerateCode(selectOption: String) {

        val custom_outputCode = findViewById<EditText>(R.id.custom_outputCode)
        val custom_inputCode = findViewById<EditText>(R.id.custom_inputCode)
        val inputCode = custom_inputCode.text.toString()

        val lines = inputCode.split("\n")
        var outputCode = ""

        lines.forEach { line ->
            val trimmedLine = line.trim()
            if (trimmedLine.isNotEmpty()) {
                val key: String
                var value: String
                if (trimmedLine.contains('=')) {
                    val parts = trimmedLine.split('=')
                    key = parts[0].trim()
                    value = parts.drop(1).joinToString("=").trim()
                    if (value.startsWith('"') && value.endsWith('"')) {
                        value = value.substring(1, value.length - 1)
                    }
                } else if (trimmedLine.contains('"')) {
                    val parts = trimmedLine.split('"')
                    key = parts[0].trim()
                    value = parts.drop(1).joinToString("").trim()
                } else {
                    key = trimmedLine
                    value = ""
                }

                outputCode += "settings put $selectOption $key $value\n"
            }
        }

        custom_outputCode.setText(outputCode)
    }
}





