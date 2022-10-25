import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.TextField

class DatasetSelectorController: ChoiceBox<String>() {
    init {
//        onAction = EventHandler {
//            Model.increment()
//        }
        val orderDropDownOptions = FXCollections.observableArrayList("dataset 1", "dataset 2")
        items.addAll(orderDropDownOptions)
        this.selectionModel.selectedItemProperty().addListener {
            _, _, newValue ->
            Model.changeSelectedDataset(newValue)
        }
    }
}

class DatasetCreatorTextFieldController: TextField() {
    init {
        maxWidth = 175.0
        textProperty().addListener {
            _, _, newValue ->
            Model.changeCurrentNewDatasetName(newValue)
        }
    }
}

class DatasetCreatorButtonController: Button("Create") {
    init {
        onAction = EventHandler {
            Model.createNewDataset()
        }
    }
}

class DatasetVisualizerLineButtonController: Button("Line") {
    init {
        onAction = EventHandler {
            Model.visualizeDataset("Line")
        }
    }
}

class DatasetVisualizerBarButtonController: Button("Bar") {
    init {
        onAction = EventHandler {
            Model.visualizeDataset("Bar")
        }
    }
}

class DatasetVisualizerBarSEMButtonController: Button("Bar (SEM)") {
    init {
        onAction = EventHandler {
            Model.visualizeDataset("Bar (SEM)")
        }
    }
}

class DatasetVisualizerPieButtonController: Button("Pie") {
    init {
        onAction = EventHandler {
            Model.visualizeDataset("Pie")
        }
    }
}