package com.example.gpa

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var cgpaButton: Button
    private lateinit var gpaButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cgpaButton=findViewById(R.id.cgpa)
        gpaButton=findViewById(R.id.gpa)

        gpaButton.setOnClickListener{
            val intent = Intent(this, Gpacalculate::class.java)
            startActivity(intent)
        }
        cgpaButton.setOnClickListener{
            val intent1 =Intent(this, Cgpacalculate::class.java)
            startActivity(intent1)
        }



    }



}
