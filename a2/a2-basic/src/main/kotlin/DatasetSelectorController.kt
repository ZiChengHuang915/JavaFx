import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.ChoiceBox


class DatasetSelectorController: ChoiceBox<String>() {
    init {
//        onAction = EventHandler {
//            Model.increment()
//        }
        val orderDropDownOptions = FXCollections.observableArrayList("Length (asc)", "Length (desc)")
        val list: ObservableList<String> = this.items
        items.addAll(orderDropDownOptions)
        this.selectionModel.selectedItemProperty().addListener {
            _, _, newValue ->
            Model.selectedDataset = newValue
            println(Model.selectedDataset)
        }
    }
}