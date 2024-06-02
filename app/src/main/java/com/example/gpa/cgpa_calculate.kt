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

class Cgpacalculate : AppCompatActivity() {
    private lateinit var numSemEditText: EditText
    private lateinit var semCreditsEditText: EditText
    private lateinit var semGradeEditText: EditText
    private lateinit var addSemButton: Button
    private lateinit var calculateCGPAButton: Button
    private lateinit var resultCGPATextView: TextView
    private lateinit var numSubEditText: EditText
    private var numSem: Int = 0
    private var numSub: Int=0
    private var totalCredits: Float = 0.0f
    private var totalGradePoints: Float = 0.0f
    private var currentSemCount: Int = 0
    private var currentSubCount : Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cgpa)
        numSemEditText = findViewById(R.id.numSem)
        semCreditsEditText = findViewById(R.id.semCredits)
        semGradeEditText = findViewById(R.id.semGrade)
        addSemButton = findViewById(R.id.addSem)
        calculateCGPAButton = findViewById(R.id.calculateCGPA)
        resultCGPATextView = findViewById(R.id.resultCGPA)
        numSubEditText=findViewById(R.id.numSub)

        addSemButton.setOnClickListener {
            addSem()
            hideKeyboard(it)
        }

        calculateCGPAButton.setOnClickListener {
            calculateCGPA()
            hideKeyboard(it)
        }
    }
    private fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun addSem() {
        if (numSem == 0 ) {
            numSem = numSemEditText.text.toString().toIntOrNull() ?: 0
            if (numSem <= 0) {
                Toast.makeText(this, "Enter a valid number of sem", Toast.LENGTH_SHORT).show()
                return
            }
        }
        if(numSub==0){
            numSub=numSubEditText.text.toString().toIntOrNull() ?: 0
            if (numSub <= 0) {
                Toast.makeText(this, "Enter a valid number of subjects", Toast.LENGTH_SHORT).show()
                return
            }
        }
        if (currentSemCount == numSem) {
            Toast.makeText(this, "All sems added. Now calculate CGPA.", Toast.LENGTH_SHORT).show()
        }
        else {


            val credits = semCreditsEditText.text.toString().toFloatOrNull()
            val gradePoint = semGradeEditText.text.toString().toFloatOrNull()

            if (credits == null || credits <= 0 || gradePoint == null || gradePoint < 0.0) {
                Toast.makeText(
                    this, "Enter valid credits and grade points", Toast.LENGTH_SHORT).show()
                return
            }
            totalCredits += credits
            totalGradePoints += gradePoint * credits
            currentSubCount++
            if(currentSemCount==numSem && currentSubCount==numSub){
                Toast.makeText(this, "All sems added. Now calculate CGPA.", Toast.LENGTH_SHORT).show()
                numSemEditText.text.clear()
            }
            else if(currentSubCount==numSub){
                currentSemCount++
                currentSubCount=0
                numSub=0
                if(currentSemCount==numSem){
                    Toast.makeText(this, "All sems added. Now calculate CGPA.", Toast.LENGTH_SHORT).show()
                    numSemEditText.text.clear()
                }
                else {
                    Toast.makeText(this, "Sem added. Add next sem.", Toast.LENGTH_SHORT).show()
                }
                numSubEditText.text.clear()
            }
            else{
                Toast.makeText(this, "Subject added. Add next subject.",Toast.LENGTH_SHORT).show()
            }

            semCreditsEditText.text.clear()
            semGradeEditText.text.clear()
        }

    }

    private fun calculateCGPA() {
        if (currentSemCount != numSem) {
            Toast.makeText(this, "Add all sems first", Toast.LENGTH_SHORT).show()
            return
        }

        val cgpa = (totalGradePoints / totalCredits)
        resultCGPATextView.text = String.format(Locale.getDefault(), "Your CGPA is %.2f", cgpa)
        numSemEditText.text.clear()
        totalCredits= 0F
        totalGradePoints=0F
        currentSemCount=0
        numSem=0
    }
}