import javafx.event.EventHandler
import javafx.scene.control.Button

class Controller: Button("Increment") {
    init {
        onAction = EventHandler {
            Model.increment()
        }
    }
}