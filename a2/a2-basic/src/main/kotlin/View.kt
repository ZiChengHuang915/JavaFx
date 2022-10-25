import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.scene.control.Label

class View() : Label(), InvalidationListener {
    init {
        Model.addListener(this)
        invalidated(null)
    }
    override fun invalidated(observable: Observable?) {
        text = Model.get().toString()
    }
}
