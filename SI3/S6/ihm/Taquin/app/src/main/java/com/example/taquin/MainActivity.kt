package com.example.taquin

import android.animation.ObjectAnimator
import android.content.res.Resources.NotFoundException
import android.graphics.Path
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.GridLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import java.util.*
import java.util.stream.IntStream
import kotlin.collections.ArrayList

enum class Direction {
    UP, DOWN, LEFT, RIGHT
}

class MainActivity() : AppCompatActivity() {
    private lateinit var buttons: ArrayList<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttons = arrayListOf(
            findViewById(R.id.button1),
            findViewById(R.id.button2),
            findViewById(R.id.button3),
            findViewById(R.id.button4),
            findViewById(R.id.button5),
            findViewById(R.id.button6),
            findViewById(R.id.button7),
            findViewById(R.id.button8),
            findViewById(R.id.button9),
        )

        setEnabled(false)

        val startGameButton = findViewById<Button>(R.id.start_game)

        startGameButton.setOnClickListener {
            it.isVisible = false
            setEnabled(true)
            shuffleButtons()
        }

        for (button in buttons) {
            button.setOnClickListener {
                Log.d("Taquin", "Button clicked: ${button.text}, index: ${buttons.indexOf(button)}")
                val direction = getAvailableDirection(button)
                if (direction != null) {
                    moveButton(button, direction)
                }
            }
        }
    }

    private fun setEnabled(enabled: Boolean) {
        for (button in buttons) {
            button.isEnabled = enabled
        }
    }

    private fun getAvailableDirection(button: Button): Direction? {
        for (direction in Direction.values()) {
            if (isAvailable(button, direction)) {
                return direction
            }
        }
        return null
    }

    private fun nextIndex(idx: Int, direction: Direction): Int {
        return when (direction) {
            Direction.UP -> idx - 3
            Direction.DOWN -> idx + 3
            Direction.LEFT -> idx - 1
            Direction.RIGHT -> idx + 1
        }
    }

    private fun isAvailable(button: Button, direction: Direction): Boolean {
        val idx = buttons.indexOf(button)

        val row = idx / 3
        val col = idx % 3

        val onGrid = when (direction) {
            Direction.UP -> row > 0
            Direction.DOWN -> row < 2
            Direction.LEFT -> col > 0
            Direction.RIGHT -> col < 2
        }

        if (!onGrid) return false

        val buttonIdx = nextIndex(idx, direction)
        return !buttons[buttonIdx].isVisible
    }

    private fun moveButton(button: Button, direction: Direction) {
        val idx = buttons.indexOf(button)
        val nextButton = buttons[nextIndex(idx, direction)]

        val grid = findViewById<GridLayout>(R.id.main_grid)

        val translationX = when (direction) {
            Direction.LEFT -> -button.width.toFloat() + grid.paddingLeft
            Direction.RIGHT -> button.width.toFloat() + grid.paddingLeft
            else -> 0f
        }
        val translationY = when (direction) {
            Direction.UP -> -button.height.toFloat() + grid.paddingTop
            Direction.DOWN -> button.height.toFloat() + grid.paddingTop
            else -> 0f
        }

        val path = Path()
        path.lineTo(translationX, translationY)

        // play animation on X and Y
        ObjectAnimator.ofFloat(button, "translationX", "translationY", path).apply {
            duration = 300
            doOnEnd {
                // we play an animation, but we don't do actual moving. We swap the text of the buttons
                button.text = nextButton.text.also { nextButton.text = button.text }
                button.isVisible =
                    nextButton.isVisible.also { nextButton.isVisible = button.isVisible }

                // reset the translation
                button.translationX = 0f
                button.translationY = 0f
            }
            start()
        }
    }

    private fun shuffleButtons(level: Int = 100) {
        val random = Random()
        val buttonList = buttons.filter { it.isVisible }
        val buttonCount = buttonList.size

        for (i in 0..level) {
            val i1 = random.nextInt(buttonCount)
            val i2 = random.nextInt(buttonCount)

            val btn1 = buttonList[i1]
            val btn2 = buttonList[i2]

            // swap the text
            btn1.text = btn2.text.also { btn2.text = btn1.text }
        }
    }
}