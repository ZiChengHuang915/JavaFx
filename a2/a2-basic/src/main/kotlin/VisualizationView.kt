import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color

object VisualizationView: Canvas(), InvalidationListener {
    init {
        Model.addListener(this)
        invalidated(null)
    }

    override fun invalidated(observable: Observable?) {
        graphicsContext2D.apply {
            if (Model.currentViewType == Views.Line) {
//                fill = Color.BLACK
//                fillRect(0.0, 0.0, width, height)
            }
        }
    }
}