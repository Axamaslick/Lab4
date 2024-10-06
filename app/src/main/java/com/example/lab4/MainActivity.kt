package com.example.lab4

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

data class Question(val textResId: Int, val answer: Boolean)

class MainActivity : AppCompatActivity() {
    private lateinit var questionTextView: TextView
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var cheatButton: Button

    private val questionBank = arrayOf(
        Question(R.string.question_1, true),
        Question(R.string.question_2, false),
        Question(R.string.question_3, true)
    )

    private val hints = arrayOf(
        listOf("Подумай о Европе", "Вспомни Рататуй", "Ну, жалюзи"),
        listOf("Это не звучит странно?", "Русские побеждали Японию?", "До сих пор не понятно?"),
        listOf("Звучит логично, разве нет?", "Не, ну правда", "Не слишком ли много нажатий для такого вопроса?")
    )

    private var currentIndex = 0
    private var counter = 0
    private var hintIndex = 0 // Индекс для отслеживания подсказок

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
        cheatButton = findViewById(R.id.cheat_button)

        updateQuestion()

        trueButton.setOnClickListener {
            checkAnswer(true)
            toggleButtons(false)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
            toggleButtons(false)
        }

        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            hintIndex = 0 // Сбросить индекс подсказок при переходе к следующему вопросу
            updateQuestion()
            toggleButtons(true)
        }

        cheatButton.setOnClickListener {
            if (hintIndex < hints[currentIndex].size) {
                val hint = hints[currentIndex][hintIndex]
                hintIndex++
                val toast = Toast.makeText(applicationContext, hint + "\nAndroid API: " + Build.VERSION.SDK_INT, Toast.LENGTH_SHORT)
                toast.show()
            } else {
                val toast = Toast.makeText(applicationContext, "А все!" + "\nAndroid API: " + Build.VERSION.SDK_INT, Toast.LENGTH_SHORT)
                toast.show()
            }
        }
    }

    private fun updateQuestion() {
        val question = questionBank[currentIndex]
        questionTextView.setText(question.textResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        if (userAnswer == correctAnswer) {
            counter++
        }
    }

    private fun toggleButtons(show: Boolean) {
        trueButton.visibility = if (show) View.VISIBLE else View.INVISIBLE
        falseButton.visibility = if (show) View.VISIBLE else View.INVISIBLE
        nextButton.visibility = if (show) View.INVISIBLE else View.VISIBLE
    }
}
