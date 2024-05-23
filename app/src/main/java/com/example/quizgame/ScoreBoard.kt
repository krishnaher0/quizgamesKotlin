package com.example.quizgame

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizgame.databinding.ActivityScoreBinding

class ScoreBoard : AppCompatActivity() {
    private lateinit var scoreBinding: ActivityScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        scoreBinding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(scoreBinding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val solvedQuestions = intent.getIntExtra("solvedQuestions", 0)
        val totalQuestions = intent.getIntExtra("totalQuestions", 0)
        val finalScore = intent.getIntExtra("finalScore", 0)

        scoreBinding.textView4.text = getString(R.string.solved_questions, solvedQuestions, totalQuestions)
        scoreBinding.textView18.text = getString(R.string.final_score, finalScore)

        scoreBinding.button.setOnClickListener {
            val intent = Intent(this@ScoreBoard, GameLoader::class.java)
            startActivity(intent)
            finish()
        }
    }
}
