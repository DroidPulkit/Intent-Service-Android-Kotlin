package com.example.intentservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.editTextInput)
    }

    public fun startServiceFun(){
        val intent = Intent(this, ExampleIntentService::class.java)
        intent.putExtra("data", editText.text.toString().trim())
        ContextCompat.startForegroundService(this, intent)
    }
}
