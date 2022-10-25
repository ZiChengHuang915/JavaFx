import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.scene.control.Label

object DataEntryNameView : Label(), InvalidationListener {
    init {
        Model.addListener(this)
        invalidated(null)
    }
    override fun invalidated(observable: Observable?) {
        text = "Dataset name: " + Model.getCurrentDatasetName()
    }
}
