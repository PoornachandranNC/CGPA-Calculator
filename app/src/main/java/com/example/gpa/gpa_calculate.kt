package com.example.gpa

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class Gpacalculate : AppCompatActivity() {
    private lateinit var numSubjectsEditText: EditText
    private lateinit var creditsEditText: EditText
    private lateinit var gradePointEditText: EditText
    private lateinit var addSubjectButton: Button
    private lateinit var calculateGPAButton: Button
    private lateinit var resultTextView: TextView
    private var numSubjects: Int = 0
    private var totalCredits: Float = 0.0f
    private var totalGradePoints: Float = 0.0f
    private var currentSubjectCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gpa)
        numSubjectsEditText = findViewById(R.id.numSubjects)
        creditsEditText = findViewById(R.id.credits)
        gradePointEditText = findViewById(R.id.gradePoint)
        addSubjectButton = findViewById(R.id.addSubject)
        calculateGPAButton = findViewById(R.id.calculateGPA)
        resultTextView = findViewById(R.id.result)

        addSubjectButton.setOnClickListener {
            addSubject()
            hideKeyboard(it)
        }

        calculateGPAButton.setOnClickListener {
            calculateGPA()
            hideKeyboard(it)
        }
    }
    private fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun addSubject() {
        if (numSubjects == 0) {
            numSubjects = numSubjectsEditText.text.toString().toIntOrNull() ?: 0
            if (numSubjects <= 0) {
                Toast.makeText(this, "Enter a valid number of subjects", Toast.LENGTH_SHORT).show()
                return
            }
        }
        if (currentSubjectCount == numSubjects) {
            Toast.makeText(this, "All subjects added. Now calculate GPA.", Toast.LENGTH_SHORT).show()
        }
        else {


            val credits = creditsEditText.text.toString().toFloatOrNull()
            val gradePoint = gradePointEditText.text.toString().toFloatOrNull()

            if (credits == null || credits <= 0 || gradePoint == null || gradePoint < 0.0 || gradePoint > 10.0 || numSubjects <= 0) {
                Toast.makeText(
                    this,
                    "Enter valid credits, number of subjects and grade points",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }

            totalCredits += credits
            totalGradePoints += gradePoint * credits
            currentSubjectCount++

            creditsEditText.text.clear()
            gradePointEditText.text.clear()
            if (currentSubjectCount == numSubjects) {
                Toast.makeText(this, "All subjects added. Now calculate GPA.", Toast.LENGTH_SHORT).show()
                numSubjectsEditText.text.clear()
            }
            else {
                Toast.makeText(this, "Subject added. Add next subject.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun calculateGPA() {
        if (currentSubjectCount != numSubjects) {
            Toast.makeText(this, "Add all subjects first", Toast.LENGTH_SHORT).show()
            return
        }

        val gpa = totalGradePoints / totalCredits
        resultTextView.text = String.format(Locale.getDefault(), "Your GPA is %.2f", gpa)
        numSubjectsEditText.text.clear()
        totalCredits= 0F
        totalGradePoints=0F
        currentSubjectCount=0
        numSubjects=0
    }
}