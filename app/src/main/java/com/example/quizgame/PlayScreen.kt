package com.example.quizgame

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizgame.databinding.ActivityPlayBinding
import kotlin.random.Random

class PlayScreen : AppCompatActivity() {

    private lateinit var playBinding: ActivityPlayBinding
    private lateinit var countDownTimer: CountDownTimer
    private var correctAnswer: Int = 0
    private var answerSubmitted = false
    private var remainingLives: Int = 3
    private var finalScore: Int = 0
    private var totalQuestions: Int = 0
    private var solvedQuestions: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        playBinding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(playBinding.root)

        ViewCompat.setOnApplyWindowInsetsListener(playBinding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupGame()
    }

    private fun setupGame() {
        playBinding.textView3.text = remainingLives.toString()
        playBinding.textView10.text = finalScore.toString()
        displayRandomNumbersWithOperation()
        startCountDown()

        playBinding.button.setOnClickListener {
            answerSubmitted = true
            validateAnswer()
        }
    }

    private fun displayRandomNumbersWithOperation() {
        totalQuestions++
        val num1 = Random.nextInt(1, 100)
        val num2 = Random.nextInt(1, 100)
        val operation = intent.getStringExtra("operation")

        val operator = when (operation) {
            "sum" -> "+"
            "remain" -> "-"
            "product" -> "*"
            else -> ""
        }

        val resultString = "$num1 $operator $num2"
        playBinding.textView15.text = "What is $resultString?"

        correctAnswer = when (operation) {
            "sum" -> num1 + num2
            "remain" -> num1 - num2
            "product" -> num1 * num2
            else -> 0
        }
    }

    private fun validateAnswer() {
        val userAnswer = playBinding.editTextText.text.toString()
        if (userAnswer.isEmpty()) {
            Toast.makeText(this, "Please enter an answer", Toast.LENGTH_SHORT).show()
            return
        }
        if (userAnswer.toInt() == correctAnswer) {
            finalScore += 10
            playBinding.textView10.text = finalScore.toString()
            solvedQuestions += 1
        } else {
            remainingLives -= 1
            playBinding.textView3.text = remainingLives.toString()

            if (remainingLives <= 0) {
                Toast.makeText(this, "Game Over!", Toast.LENGTH_SHORT).show()
                endGame()
                return
            }
        }
        resetForNextQuestion()
    }

    private fun startCountDown() {
        countDownTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                playBinding.textView12.text = "00:0$secondsRemaining"
            }

            override fun onFinish() {
                if (!answerSubmitted) {
                    remainingLives -= 1
                    playBinding.textView3.text = remainingLives.toString()
                    if (remainingLives <= 0) {
                        endGame()
                        return
                    } else {
                        Toast.makeText(this@PlayScreen, "Time's up! You lost a life.", Toast.LENGTH_SHORT).show()
                    }
                }
                resetForNextQuestion()
            }
        }.start()
    }

    private fun resetForNextQuestion() {
        answerSubmitted = false
        playBinding.editTextText.text.clear()
        displayRandomNumbersWithOperation()
        countDownTimer.cancel()
        startCountDown()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }

    private fun endGame() {
        Toast.makeText(this, "Game Over!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, ScoreBoard::class.java)
        intent.putExtra("totalQuestions", totalQuestions)
        intent.putExtra("solvedQuestions", solvedQuestions)
        intent.putExtra("finalScore", finalScore)
        startActivity(intent)
        finish()
    }
}
