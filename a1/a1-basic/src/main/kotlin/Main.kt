import javafx.application.Application
import javafx.collections.FXCollections
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
        val statusBar = Label("temp label")

        val viewLabel = Label("View: ")
        val viewListButton = Button("List")
        val viewGridButton = Button("Grid")
        val viewGroup = HBox(viewLabel, viewListButton, viewGridButton)

        val archivedLabel = Label("Show archived: ")
        val archivedCheckBox = CheckBox()
        val archivedGroup = HBox(archivedLabel, archivedCheckBox)

        val orderLabel = Label("Order by: ")
        val orderDropDownOptions = FXCollections.observableArrayList("Length (asc)", "Length (desc)")
        val orderDropDown = ChoiceBox(orderDropDownOptions)
        val orderGroup = HBox(orderLabel, orderDropDown)

        val region = Region()
        HBox.setHgrow(region, Priority.ALWAYS)

        val clearButton = Button("Clear")

        val toolBar = HBox(viewGroup, archivedGroup, orderGroup, region, clearButton)

        BorderPane.setAlignment(toolBar, Pos.TOP_CENTER)
        BorderPane.setAlignment(statusBar, Pos.BOTTOM_CENTER)
        root.top = toolBar
        root.bottom = statusBar

        stage.title = "CS349 - A1 Notes - zc3huang"
        stage.scene = Scene(root, 800.0, 600.0)
        stage.show()
        stage.apply {
            minWidth = 640.0
            minHeight = 480.0
        }
    }
}