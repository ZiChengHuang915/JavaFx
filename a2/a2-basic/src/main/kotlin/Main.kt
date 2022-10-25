import javafx.application.Application
import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.stage.Stage

class Main : Application()  {
    val root = BorderPane()
    override fun start(stage: Stage) {
        val view = View()

        // toolbar
        val datasetSelectorController = DatasetSelectorController()
        val datasetCreatorTextFieldController = DatasetCreatorTextFieldController()
        val datasetCreatorButtonController = DatasetCreatorButtonController()
        val datasetVisualizerLineButtonController = DatasetVisualizerLineButtonController()
        val datasetVisualizerBarButtonController = DatasetVisualizerBarButtonController()
        val datasetVisualizerBarSEMButtonController = DatasetVisualizerBarSEMButtonController()
        val datasetVisualizerPieButtonController = DatasetVisualizerPieButtonController()

        val selector = HBox(datasetSelectorController)
        val creator = HBox(datasetCreatorTextFieldController, datasetCreatorButtonController)
        val visualizer = HBox(datasetVisualizerLineButtonController, datasetVisualizerBarButtonController, datasetVisualizerBarSEMButtonController, datasetVisualizerPieButtonController)
        val toolbar = HBox(selector, creator, visualizer).apply {
            spacing = 30.0
        }
        val content = BorderPane()

        root.top = toolbar
        root.bottom = content

        stage.apply {
            title = "CS349 - A2 Graphs - zc3huang"
            scene = Scene(root , 800.0, 600.0)
            minWidth = 640.0
            minHeight = 480.0
        }.show ()
    }
}

object Model: Observable {
    private val listeners =
        mutableListOf<InvalidationListener?>()

    var selectedDataset = ""
    var currentNewDatasetName = ""
    fun changeSelectedDataset(newDataset: String) {
        selectedDataset = newDataset
        listeners.forEach { it?.invalidated(this) }
    }

    fun changeCurrentNewDatasetName(newDataset: String) {
        currentNewDatasetName = newDataset
        listeners.forEach { it?.invalidated(this) }
    }

    fun createNewDataset() {
        println("create new dataset with name: " + currentNewDatasetName)
        listeners.forEach { it?.invalidated(this) }
    }

    fun visualizeDataset(dataset: String) {
        listeners.forEach { it?.invalidated(this) }
    }

    override fun addListener(listener: InvalidationListener?) {
        listeners.add(listener)
    }
    override fun removeListener(listener: InvalidationListener?) {
        listeners.remove(listener)
    }
}

/*
// toolbar
        val viewLabel = Label("View: ")
        var viewGridButton = Button()
        val viewListButton = Button("List").apply {
            this.isDisable = isListView
            this.onAction = EventHandler {
                isListView = true
                this.isDisable = isListView
                viewGridButton.isDisable = !isListView
                reloadRoot()
            }
        }
        viewGridButton = Button("Grid").apply {
            this.isDisable = !isListView
            this.onAction = EventHandler {
                isListView = false
                this.isDisable = !isListView
                viewListButton.isDisable = isListView
                reloadRoot()
            }
        }
        val viewGroup = HBox(viewLabel, viewListButton, viewGridButton).apply {
            this.spacing = 10.0
        }

        val archivedLabel = Label("Show archived: ")
        val archivedCheckBox = CheckBox()
        archivedCheckBox.isSelected = isArchivedChecked
        archivedCheckBox.selectedProperty().addListener {
            _, _, newValue ->
            isArchivedChecked = newValue
            reloadRoot()
        }
        val archivedGroup = HBox(archivedLabel, archivedCheckBox).apply {
            this.spacing = 10.0
        }

        val orderLabel = Label("Order by: ")
        val orderDropDownOptions = FXCollections.observableArrayList("Length (asc)", "Length (desc)")
        val orderDropDown = ChoiceBox(orderDropDownOptions)
        orderDropDown.selectionModel.selectedItemProperty().addListener {
            _, _, newValue ->
            if (newValue == orderDropDownOptions[0]) {
                sortAscending = true
            } else if (newValue == orderDropDownOptions[1]) {
                sortAscending = false
            }
            reloadRoot()
        }
        orderDropDown.selectionModel.select(0)
        val orderGroup = HBox(orderLabel, orderDropDown).apply {
            this.spacing = 10.0
        }

        val region = Region()
        HBox.setHgrow(region, Priority.ALWAYS)

        val clearButton = Button("Clear")
        clearButton.onAction = EventHandler {
            notes.clear()
            statusBar.text = getStatusString()
            reloadRoot()
        }

        val toolBar = HBox(viewGroup, archivedGroup, orderGroup, region, clearButton).apply {
            this.spacing = 10.0
        }
 */