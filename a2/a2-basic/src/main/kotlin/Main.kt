import javafx.application.Application
import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.ScrollPane
import javafx.scene.control.SplitPane
import javafx.scene.layout.*
import javafx.stage.Stage

enum class Views {
    Line, Bar, BarSEM, Pie
}

class Main : Application()  {
    private val root = BorderPane()
    override fun start(stage: Stage) {
        // toolbar
        val selector = HBox(DatasetSelectorController)
        val creator = HBox(DatasetCreatorTextFieldController, DatasetCreatorButtonController)
        val visualizer = HBox(DatasetVisualizerLineButtonController, DatasetVisualizerBarButtonController, DatasetVisualizerBarSEMButtonController, DatasetVisualizerPieButtonController)
        val toolbar = HBox(selector, creator, visualizer).apply {
            spacing = 30.0
        }

        // data entry section on left
        val dataEntryItems = VBox(DataEntryNameView, DataEntryRowView, DataEntryAddButtonController).apply {
            alignment = Pos.CENTER
        }

        val scroll = ScrollPane(dataEntryItems).apply {
            vbarPolicy = ScrollPane.ScrollBarPolicy.ALWAYS
            minWidth = 250.0
        }

        // visualization section on right
        val pane = Pane(VisualizationView)
        VisualizationView.widthProperty().bind(pane.widthProperty())
        VisualizationView.heightProperty().bind(pane.heightProperty())
        pane.widthProperty().addListener {
                _, _, _ ->
            Model.refreshView()
        }
        pane.heightProperty().addListener {
                _, _, _ ->
            Model.refreshView()
        }

        // main content
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
    private var currentNewDatasetName = ""
    var currentViewType = Views.Line

    init {
        // manually creating datasets
        val quadratic = Dataset("quadratic", mutableListOf(0.1, 1.0, 4.0, 9.0, 16.0))
        val negativeQuadratic = Dataset("negative quadratic", mutableListOf(-0.1, -1.0, -4.0, -9.0, -16.0))
        val alternating = Dataset("alternating", mutableListOf(-1.0, 3.0, -1.0, 3.0, -1.0, 3.0))
        val inflation = Dataset("inflation", mutableListOf(4.8, 5.6, 1.5, 1.9, 0.2, 2.1, 1.6, 1.6, 1.0, 1.7, 2.7, 2.5, 2.3, 2.8, 1.9, 2.2, 2.0, 2.1, 2.4, 0.3, 1.8, 2.9, 1.5, 0.9, 1.9, 1.1, 1.4, 1.6, 2.3, 1.9, 0.7, 3.4, 6.8))
        datasets.add(quadratic)
        datasets.add(negativeQuadratic)
        datasets.add(alternating)
        datasets.add(inflation)
    }

    fun refreshView() {
        listeners.forEach { it?.invalidated(this) }
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
        val newDataset = Dataset(currentNewDatasetName, mutableListOf(0.0))
        datasets.add(newDataset)
        DatasetSelectorController.loadDatasets()

        DatasetCreatorTextFieldController.text = ""
        DatasetSelectorController.selectionModel.select(datasets.size - 1)
        changeSelectedDataset(datasets.size - 1)

        listeners.forEach { it?.invalidated(this) }
    }

    fun visualizeDataset(dataset: Views) {
        currentViewType = dataset

        listeners.forEach { it?.invalidated(this) }
    }

    fun getCurrentDatasetName(): String {
        return datasets[selectedDatasetIndex].datasetName
    }

    fun getCurrentDatasetEntries(): MutableList<Double> {
        return datasets[selectedDatasetIndex].entries
    }

    fun addEntryToCurrentDataSet() {
        datasets[selectedDatasetIndex].addEntry(0.0)

        listeners.forEach { it?.invalidated(this) }
    }

    override fun addListener(listener: InvalidationListener?) {
        listeners.add(listener)
    }
    override fun removeListener(listener: InvalidationListener?) {
        listeners.remove(listener)
    }
}