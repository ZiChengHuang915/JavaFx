import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.TextField

class DatasetSelectorController: ChoiceBox<String>() {
    init {
        val datasetNames = Model.getDatasetNames()
        val orderDropDownOptions = FXCollections.observableArrayList(datasetNames)
        items.addAll(orderDropDownOptions)
        selectionModel.select(0)
        this.selectionModel.selectedIndexProperty().addListener {
            _, _, newValue ->
            Model.changeSelectedDataset(newValue as Int)
        }
        minWidth = 175.0
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