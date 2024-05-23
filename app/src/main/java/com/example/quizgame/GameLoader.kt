package com.example.quizgame

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizgame.databinding.ActivityGameBinding

class GameLoader : AppCompatActivity() {

    private lateinit var gameBinding: ActivityGameBinding
    private var selectedOperation: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        gameBinding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(gameBinding.root)

        ViewCompat.setOnApplyWindowInsetsListener(gameBinding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        gameBinding.cardView1.setOnClickListener {
            selectOperation("sum")
        }
        gameBinding.cardView2.setOnClickListener {
            selectOperation("remain")
        }
        gameBinding.cardView3.setOnClickListener {
            selectOperation("product")
        }

        gameBinding.button.setOnClickListener {
            val intent = Intent(this@GameLoader, PlayScreen::class.java)
            intent.putExtra("operation", selectedOperation)
            startActivity(intent)
        }
    }

    private fun selectOperation(operation: String) {
        selectedOperation = operation
        gameBinding.cardView1.isChecked = operation == "sum"
        gameBinding.cardView2.isChecked = operation == "remain"
        gameBinding.cardView3.isChecked = operation == "product"
    }
}
