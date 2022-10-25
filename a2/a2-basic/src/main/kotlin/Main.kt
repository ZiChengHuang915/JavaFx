import javafx.application.Application
import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.scene.Scene
import javafx.scene.control.ScrollPane
import javafx.scene.control.SplitPane
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.Stage

class Main : Application()  {
    val root = BorderPane()
    override fun start(stage: Stage) {
        // toolbar
        val selector = HBox(DatasetSelectorController)
        val creator = HBox(DatasetCreatorTextFieldController, DatasetCreatorButtonController)
        val visualizer = HBox(DatasetVisualizerLineButtonController, DatasetVisualizerBarButtonController, DatasetVisualizerBarSEMButtonController, DatasetVisualizerPieButtonController)
        val toolbar = HBox(selector, creator, visualizer).apply {
            spacing = 30.0
        }

        // data entry section on left
        val dataEntryItems = VBox(DataEntryNameView)

        val scroll = ScrollPane(dataEntryItems).apply {
            vbarPolicy = ScrollPane.ScrollBarPolicy.ALWAYS
        }

        // visualization section on right
        val pane = Pane()

        val content = SplitPane(scroll, pane)

        root.top = toolbar
        root.center = content

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
    val datasets = mutableListOf<Dataset>()

    var selectedDatasetIndex = 0
    var currentNewDatasetName = ""

    init {
        // manually creating datasets
        val quadratic = Dataset("quadratic", mutableListOf(0.1, 1.0, 4.0, 9.0, 16.0))
        val negativeQuadratic = Dataset("negative quadratic", mutableListOf(-0.1, -1.0, -4.0, -9.0, -16.0))
        datasets.add(quadratic)
        datasets.add(negativeQuadratic)
    }

    fun getDatasetNames(): MutableList<String> {
        val names = mutableListOf<String>()

        for (dataset in datasets) {
            names.add(dataset.datasetName)
        }

        return names
    }

    fun changeSelectedDataset(newDatasetIndex: Int) {
        selectedDatasetIndex = newDatasetIndex
        listeners.forEach { it?.invalidated(this) }
    }

    fun changeCurrentNewDatasetName(newDatasetName: String) {
        currentNewDatasetName = newDatasetName
        listeners.forEach { it?.invalidated(this) }
    }

    fun createNewDataset() {
        println("create new dataset with name: " + currentNewDatasetName)
        val newDataset = Dataset(currentNewDatasetName, mutableListOf())
        datasets.add(newDataset)
        DatasetSelectorController.loadDatasets()

        DatasetCreatorTextFieldController.text = ""
        DatasetSelectorController.selectionModel.select(datasets.size - 1)
        changeSelectedDataset(datasets.size - 1)

        listeners.forEach { it?.invalidated(this) }
    }

    fun visualizeDataset(dataset: String) {
        listeners.forEach { it?.invalidated(this) }
    }

    fun getCurrentDatasetName(): String {
        return datasets[selectedDatasetIndex].datasetName
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