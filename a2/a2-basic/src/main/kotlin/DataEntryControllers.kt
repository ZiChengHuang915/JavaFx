import javafx.event.EventHandler
import javafx.scene.control.Button

object DataEntryAddButtonController: Button("Add Entry") {
    init {
        onAction = EventHandler {
            Model.addEntryToCurrentDataSet()
        }
    }
}