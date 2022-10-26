import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.TextField

object DatasetSelectorController: ChoiceBox<String>() {
    init {
        loadDatasets()
        selectionModel.select(0)
        this.selectionModel.selectedIndexProperty().addListener {
            _, _, newValue ->
            if (newValue as Int >= 0) {
                Model.changeSelectedDataset(newValue)
            }
        }
        minWidth = 175.0
    }

    fun loadDatasets() {
        val datasetNames = Model.getDatasetNames()
        val orderDropDownOptions = FXCollections.observableArrayList(datasetNames)
        items.clear()
        items.addAll(orderDropDownOptions)
    }
}

object DatasetCreatorTextFieldController: TextField() {
    init {
        maxWidth = 175.0
        textProperty().addListener {
            _, _, newValue ->
            Model.changeCurrentNewDatasetName(newValue)
        }
    }
}

object DatasetCreatorButtonController: Button("Create") {
    init {
        onAction = EventHandler {
            Model.createNewDataset()
        }
    }
}

object DatasetVisualizerLineButtonController: Button("Line") {
    init {
        onAction = EventHandler {
            Model.visualizeDataset(Views.Line)
        }
    }
}

object DatasetVisualizerBarButtonController: Button("Bar") {
    init {
        onAction = EventHandler {
            Model.visualizeDataset(Views.Bar)
        }
    }
}

object DatasetVisualizerBarSEMButtonController: Button("Bar (SEM)") {
    init {
        onAction = EventHandler {
            Model.visualizeDataset(Views.BarSEM)
        }
    }
}

object DatasetVisualizerPieButtonController: Button("Pie") {
    init {
        onAction = EventHandler {
            Model.visualizeDataset(Views.Pie)
        }
    }
}