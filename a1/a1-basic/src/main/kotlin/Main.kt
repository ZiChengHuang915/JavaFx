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
import javafx.scene.paint.Color
import javafx.stage.Stage


class Main : Application()  {

    fun CreateNoteListView(text: String): AnchorPane {
        var noteLabel = Label(text)
        noteLabel.isWrapText = true
        noteLabel.prefWidth = 500.0

        var archiveCheckBox = CheckBox()

        var noteAnchorPane = AnchorPane(noteLabel, archiveCheckBox, Label("Archived"))

        noteAnchorPane.background = Background(BackgroundFill(Color.LIGHTYELLOW, CornerRadii(10.0), null))
        archiveCheckBox.selectedProperty().addListener {
                _, _, newValue -> noteAnchorPane.background = Background(BackgroundFill(if (newValue.not()) Color.LIGHTYELLOW else Color.LIGHTGRAY, CornerRadii(10.0), null))
        }
        noteAnchorPane.minHeight = 40.0
        AnchorPane.setLeftAnchor(noteAnchorPane.children[0], 10.0)
        AnchorPane.setRightAnchor(noteAnchorPane.children[0], 110.0)
        AnchorPane.setTopAnchor(noteAnchorPane.children[0], 10.0)
        AnchorPane.setRightAnchor(noteAnchorPane.children[1], 70.0)
        AnchorPane.setTopAnchor(noteAnchorPane.children[1], 10.0)
        AnchorPane.setRightAnchor(noteAnchorPane.children[2], 10.0)
        AnchorPane.setTopAnchor(noteAnchorPane.children[2], 10.0)

        return noteAnchorPane
    }

    override fun start(stage: Stage) {
        val root = BorderPane()

        // status bar
        val statusBar = Label("status bar")

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

        // notes list view
        var notePane = VBox()
        var notes = mutableListOf<AnchorPane>(
            CreateNoteListView("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut sed magna sed elit molestie rutrum nec sed purus. Etiam scelerisque magna orci, et blandit nunc egestas sagittis. Aliquam nulla purus, malesuada in pretium vitae, commodo facilisis nunc. Aenean vel lacus at sapien facilisis mattis non quis diam. Quisque et velit ipsum. Suspendisse molestie pharetra nisi a egestas. Pellentesque justo elit, mollis ac nulla ultrices, consequat aliquam nisi. Aenean euismod sodales commodo. Donec congue pharetra purus ut sollicitudin. Sed porta enim vel justo finibus rhoncus sit amet nec dui. Cras feugiat, turpis in rutrum lacinia, elit odio imperdiet neque, ac venenatis dui metus ut sapien. Donec vulputate, nisl a placerat sagittis, augue felis ornare nisl, viverra varius tortor lorem eu est."),
            CreateNoteListView("note2"))
        for (note in notes) {
            notePane.children.add(note)
        }
        notePane.spacing = 10.0

        // root pane
        BorderPane.setAlignment(toolBar, Pos.TOP_CENTER)
        BorderPane.setAlignment(statusBar, Pos.BOTTOM_CENTER)
        BorderPane.setAlignment(notePane, Pos.TOP_CENTER)
        BorderPane.setMargin(toolBar, Insets(10.0, 10.0, 10.0, 10.0))
        root.top = toolBar
        root.bottom = statusBar
        root.center = notePane

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