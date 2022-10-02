import javafx.application.Application
import javafx.collections.FXCollections
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.layout.*
import javafx.stage.Stage


class Main : Application()  {
    override fun start(stage: Stage) {
        val root = BorderPane()

        // status bar
        val statusBar = Label("temp label")

        // toolbar
        val viewLabel = Label("View: ")
        val viewListButton = Button("List")
        val viewGridButton = Button("Grid")
        val viewGroup = HBox(viewLabel, viewListButton, viewGridButton).apply {
            this.spacing = 10.0
        }

        val archivedLabel = Label("Show archived: ")
        val archivedCheckBox = CheckBox()
        val archivedGroup = HBox(archivedLabel, archivedCheckBox).apply {
            this.spacing = 10.0
        }

        val orderLabel = Label("Order by: ")
        val orderDropDownOptions = FXCollections.observableArrayList("Length (asc)", "Length (desc)")
        val orderDropDown = ChoiceBox(orderDropDownOptions)
        val orderGroup = HBox(orderLabel, orderDropDown).apply {
            this.spacing = 10.0
        }

        val region = Region()
        HBox.setHgrow(region, Priority.ALWAYS)

        val clearButton = Button("Clear")

        val toolBar = HBox(viewGroup, archivedGroup, orderGroup, region, clearButton).apply {
            this.spacing = 10.0
        }

        // root pane
        BorderPane.setAlignment(toolBar, Pos.TOP_CENTER)
        BorderPane.setAlignment(statusBar, Pos.BOTTOM_CENTER)
        BorderPane.setMargin(toolBar, Insets(10.0, 10.0, 10.0, 10.0))
        root.top = toolBar
        root.bottom = statusBar

        // set stage
        stage.title = "CS349 - A1 Notes - zc3huang"
        stage.scene = Scene(root, 800.0, 600.0)
        stage.show()
        stage.apply {
            minWidth = 640.0
            minHeight = 480.0
        }
    }
}