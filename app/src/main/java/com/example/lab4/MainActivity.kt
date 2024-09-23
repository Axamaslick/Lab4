package com.example.lab4

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var questionTextView: TextView
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button

    private val questionBank = arrayOf(
        Question(R.string.question_1, true),
        Question(R.string.question_2, false),
        Question(R.string.question_3, true)
    )
    private var currentIndex = 0
    private var counter = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        questionTextView = findViewById(R.id.question_text_view)
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)

        updateQuestion()

        trueButton.setOnClickListener {
            checkAnswer(true)
            trueButton.visibility = View.INVISIBLE
            falseButton.visibility = View.INVISIBLE
            nextButton.visibility = View.VISIBLE
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
            trueButton.visibility = View.INVISIBLE
            falseButton.visibility = View.INVISIBLE
            nextButton.visibility = View.VISIBLE
        }

        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1)
            updateQuestion()
            trueButton.visibility = View.VISIBLE
            falseButton.visibility = View.VISIBLE
            nextButton.visibility = View.INVISIBLE
        }
    }

    private fun updateQuestion() {
        if (currentIndex == questionBank.size){
            val toast = Toast.makeText(applicationContext, "Количество правильных ответов: "+ counter, Toast.LENGTH_LONG)
            toast.show()
            counter = 0
            currentIndex = 0
            val question = questionBank[currentIndex]
            questionTextView.setText(question.textResId)
        }
        else{
            val question = questionBank[currentIndex]
            questionTextView.setText(question.textResId)
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if (userAnswer == correctAnswer) {
            counter = counter +1
        } else {
            counter = counter
        }
    }


}