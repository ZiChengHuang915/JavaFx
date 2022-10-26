import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color
import kotlin.math.abs

object VisualizationView: Canvas(), InvalidationListener {
    val paddingMultiplier = 0.9

    init {
        Model.addListener(this)
        invalidated(null)
    }

    override fun invalidated(observable: Observable?) {
        println("VisualizationView invalidated")
        val entries = Model.getCurrentDatasetEntries()
        val numEntries = entries.size
        val maxY = getMaxValue(entries)
        val minY = getMinValue(entries)

        graphicsContext2D.apply {
            this.clearRect(0.0, 0.0, width, height)
            if (Model.currentViewType == Views.Line) {
                stroke = Color.RED
                for (i in 0 until numEntries) {
                    val xCoord = i * width / numEntries + width / numEntries * 0.5
                    val yCoord = (height - height * (entries[i] - minY) / (maxY - minY)) * paddingMultiplier + (1 - paddingMultiplier) / 2 * height
                    strokeOval(xCoord, yCoord, 2.0, 2.0)
                }

                stroke = Color.BLACK
                for (i in 0 until numEntries - 1) {
                    val xCoord1 = i * width / numEntries + width / numEntries * 0.5
                    val yCoord1 = (height - height * (entries[i] - minY) / (maxY - minY)) * paddingMultiplier + (1 - paddingMultiplier) / 2 * height
                    val xCoord2 = (i + 1) * width / numEntries + width / numEntries * 0.5
                    val yCoord2 = (height - height * (entries[i + 1] - minY) / (maxY - minY)) * paddingMultiplier + (1 - paddingMultiplier) / 2 * height
                    strokeLine(xCoord1, yCoord1, xCoord2, yCoord2)
                }
            }
        }
    }

    fun getMaxValue(numbers: MutableList<Double>): Double {
        var curMax = Double.MIN_VALUE
        for (num in numbers) {
            if (num > curMax) {
                curMax = num
            }
        }

        return curMax
    }

    fun getMinValue(numbers: MutableList<Double>): Double {
        var curMin = Double.MAX_VALUE
        for (num in numbers) {
            if (num < curMin) {
                curMin = num
            }
        }

        return curMin
    }

}