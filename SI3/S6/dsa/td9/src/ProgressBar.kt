package graphs.roadApplication

import java.time.LocalTime
import kotlin.math.ceil
import kotlin.math.roundToInt

class ProgressBar(private val total: Int, private val message: String = "") {
    private var current = 0
    private val deltaUpdate = ceil(total / 200.0).toInt() // print every 0.5%

    private val startTime = LocalTime.now().toNanoOfDay()
    private var currentTime = startTime

    fun print() {
        val fraction = current.toDouble() / total

        val spentTimeMilli = (currentTime - startTime) / 1_000_000

        var etaMilli = 0
        if (fraction > 0) // avoid division by zero
            etaMilli = ((1.0 / fraction - 1) * spentTimeMilli).roundToInt()

        val percent = "%.1f".format(fraction * 100)
        println("$message - $percent% [Spent: ${spentTimeMilli / 1000}s][ETA: ${etaMilli / 1000}s]")
    }

    fun step() {
        current += 1

        if (current % deltaUpdate == 0) {
            currentTime = LocalTime.now().toNanoOfDay()
            print()
        }
    }

    fun complete() {
        current = total
        currentTime = LocalTime.now().toNanoOfDay()
        print()
    }
}