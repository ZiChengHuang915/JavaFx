import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color
import javafx.scene.shape.ArcType
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.sqrt

object VisualizationView: Canvas(), InvalidationListener {
    private const val paddingMultiplier = 0.9

    init {
        Model.addListener(this)
        invalidated(null)
    }

    // Draw the visualization depending on the currently selected view mode
    override fun invalidated(observable: Observable?) {
        val entries = Model.getCurrentDatasetEntries()
        val numEntries = entries.size
        val maxY = getMaxValue(entries)
        val minY = getMinValue(entries)
        val sum = getSum(entries)

        graphicsContext2D.apply {
            this.clearRect(0.0, 0.0, width, height)

            if (Model.currentViewType == Views.Line) {
                // Draw dots
                stroke = Color.RED
                if (numEntries == 1) {
                    val xCoord = width / numEntries * 0.5
                    val yCoord = height * paddingMultiplier + (1 - paddingMultiplier) / 2 * height
                    strokeOval(xCoord, yCoord, 2.0, 2.0)
                } else {
                    for (i in 0 until numEntries) {
                        val xCoord = i * width / numEntries + width / numEntries * 0.5
                        val yCoord = (height - height * (entries[i] - minY) / (maxY - minY)) * paddingMultiplier + (1 - paddingMultiplier) / 2 * height
                        strokeOval(xCoord, yCoord, 2.0, 2.0)
                    }
                }

                // Draw lines connecting the dots
                stroke = Color.BLACK
                for (i in 0 until numEntries - 1) {
                    val xCoord1 = i * width / numEntries + width / numEntries * 0.5
                    val yCoord1 = (height - height * (entries[i] - minY) / (maxY - minY)) * paddingMultiplier + (1 - paddingMultiplier) / 2 * height
                    val xCoord2 = (i + 1) * width / numEntries + width / numEntries * 0.5
                    val yCoord2 = (height - height * (entries[i + 1] - minY) / (maxY - minY)) * paddingMultiplier + (1 - paddingMultiplier) / 2 * height
                    strokeLine(xCoord1, yCoord1, xCoord2, yCoord2)
                }
            } else if (Model.currentViewType == Views.Bar) {
                // Draw the Y = 0 line
                stroke = Color.BLACK
                val zeroYCoord = (height - height * (0 - minY) / (maxY - minY)) * paddingMultiplier + (1 - paddingMultiplier) / 2 * height
                strokeLine(0.0, zeroYCoord, width, zeroYCoord + 2)

                // Draw each bar
                for (i in 0 until numEntries) {
                    // Simulating my rainbow color (this is not a real rainbow by the way)
                    val rgbValue = i * 255 / numEntries
                    fill = Color.rgb(rgbValue, rgbValue / 2, 255 - rgbValue)

                    val xCoord = i * width / numEntries + width / numEntries * 0.5
                    val yCoord = (height - height * (entries[i] - minY) / (maxY - minY)) * paddingMultiplier + (1 - paddingMultiplier) / 2 * height
                    fillRect(xCoord, min(zeroYCoord, yCoord), width / numEntries * 0.4, abs(yCoord - zeroYCoord))
                }
            } else if (Model.currentViewType == Views.BarSEM) {
                // Draw the Y = 0 line
                stroke = Color.BLACK
                val zeroYCoord = (height - height * (0 - minY) / (maxY - minY)) * paddingMultiplier + (1 - paddingMultiplier) / 2 * height
                strokeLine(0.0, zeroYCoord, width, zeroYCoord + 2)

                // Draw each bar
                for (i in 0 until numEntries) {
                    // Simulating my rainbow color (this is not a real rainbow by the way)
                    val rgbValue = i * 255 / numEntries
                    fill = Color.rgb(rgbValue, rgbValue / 2, 255 - rgbValue)

                    val xCoord = i * width / numEntries + width / numEntries * 0.5
                    val yCoord = (height - height * (entries[i] - minY) / (maxY - minY)) * paddingMultiplier + (1 - paddingMultiplier) / 2 * height
                    fillRect(xCoord, min(zeroYCoord, yCoord), width / numEntries * 0.4, abs(yCoord - zeroYCoord))
                }

                // Drawing the 3 additional lines
                val mean = sum / numEntries
                val SEM = getStandardDeviation(entries) / sqrt(numEntries * 1.0)
                val meanYCoord = (height - height * (mean - minY) / (maxY - minY)) * paddingMultiplier + (1 - paddingMultiplier) / 2 * height
                val SEMUpperYCoord = (height - height * (mean + SEM - minY) / (maxY - minY)) * paddingMultiplier + (1 - paddingMultiplier) / 2 * height
                val SEMLowerYCoord = (height - height * (mean - SEM - minY) / (maxY - minY)) * paddingMultiplier + (1 - paddingMultiplier) / 2 * height
                strokeLine(0.0, meanYCoord, width, meanYCoord + 2)
                setLineDashes(2.0)
                strokeLine(0.0, SEMUpperYCoord, width, SEMUpperYCoord + 2)
                strokeLine(0.0, SEMLowerYCoord, width, SEMLowerYCoord + 2)
                setLineDashes(0.0)

                // Writing the mean and SEM
                fill = Color.WHITE
                opacity = 0.1
                fillRect(15.0, 15.0, 175.0, 40.0)
                opacity = 1.0
                strokeText("mean: $mean\nSEM: $SEM", 30.0, 30.0)
            } else if (Model.currentViewType == Views.Pie) {
                val arcCenterX = width / 2 - min(width, height) / 4
                val arcCenterY = height / 2 - min(width, height) / 4
                val arcRadius = min(width, height) / 2
                var currentAngle = 0.0

                // Drawing each arc
                for (i in 0 until numEntries) {
                    // Simulating my rainbow color (this is not a real rainbow by the way)
                    val rgbValue = i * 255 / numEntries
                    fill = Color.rgb(rgbValue, rgbValue / 2, 255 - rgbValue)

                    val extent = 360 * entries[i] / sum
                    fillArc(arcCenterX, arcCenterY, arcRadius, arcRadius, currentAngle, extent, ArcType.ROUND)

                    currentAngle += extent
                }
            }
        }
    }

    // Helper functions
    private fun getMaxValue(numbers: MutableList<Double>): Double {
        var curMax = Double.MIN_VALUE
        for (num in numbers) {
            if (num > curMax) {
                curMax = num
            }
        }

        return curMax
    }

    private fun getMinValue(numbers: MutableList<Double>): Double {
        var curMin = Double.MAX_VALUE
        for (num in numbers) {
            if (num < curMin) {
                curMin = num
            }
        }

        return curMin
    }

    private fun getSum(numbers: MutableList<Double>): Double {
        var sum = 0.0
        for (num in numbers) {
            sum += num
        }

        return sum
    }

    private fun getStandardDeviation(numbers: MutableList<Double>): Double {
        val mean = getSum(numbers) / numbers.size
        var temp = 0.0

        for (num in numbers) {
            temp += (num - mean) * (num - mean)
        }
        temp /= numbers.size
        return sqrt(temp)
    }
}