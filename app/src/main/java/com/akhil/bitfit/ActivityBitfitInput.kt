package com.akhil.bitfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.recyclerview.widget.RecyclerView

class ActivityBitfitInput : AppCompatActivity() {
    private lateinit var bitFitRV : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bitfit_input)
        val foodInputET : EditText = findViewById(R.id.foodInputET)
        val calorieCountET : EditText = findViewById(R.id.caloriesInputET)
        val recordInputBt : Button = findViewById<Button>(R.id.recordInputBtn)

        recordInputBt.setOnClickListener(View.OnClickListener {
            // Sending result back to main activity
            if(foodInputET.text.isEmpty() || calorieCountET.text.isEmpty() || !calorieCountET.text.isDigitsOnly())
            {
                Toast.makeText(this,"Please enter correct input!!", Toast.LENGTH_SHORT).show()
            }
            else {
                val resultIntent = Intent()
                resultIntent.putExtra(
                    "result",
                    BitFit(
                        foodName = foodInputET.text.toString(),
                        calorieCount = calorieCountET.text.toString()
                    )
                )
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        })
    }
}