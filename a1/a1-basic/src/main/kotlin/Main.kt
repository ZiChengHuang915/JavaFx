import javafx.application.Application
import javafx.collections.FXCollections
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.stage.Stage


class Main : Application()  {
    class Note(var text: String, var isArchived: Boolean = false) {
    }

    fun CreateNotes(notes: MutableList<Note>, isListView: Boolean): MutableList<AnchorPane> {
        var noteList = mutableListOf<AnchorPane>()
        for (note in notes) {
            if (isListView) {
                noteList.add(CreateNoteListView(note))
            } else {
                noteList.add(CreateNoteGridView(note))
            }
        }

        return noteList
    }

    fun CreateNoteListView(note: Note): AnchorPane {
        var noteLabel = Label(note.text)
        noteLabel.isWrapText = true
        noteLabel.prefWidth = 500.0

        var archiveCheckBox = CheckBox()
        archiveCheckBox.isSelected = note.isArchived

        var noteAnchorPane = AnchorPane(noteLabel, archiveCheckBox, Label("Archived"))

        if (note.isArchived) {
            noteAnchorPane.background = Background(BackgroundFill(Color.LIGHTGRAY, CornerRadii(10.0), null))
        } else {
            noteAnchorPane.background = Background(BackgroundFill(Color.LIGHTYELLOW, CornerRadii(10.0), null))
        }

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

    fun CreateNoteGridView(note: Note): AnchorPane {
        var noteLabel = Label(note.text)
        noteLabel.isWrapText = true
        noteLabel.setPrefSize(225.0, 180.0)

        var archiveCheckBox = CheckBox()
        archiveCheckBox.isSelected = note.isArchived

        var noteAnchorPane = AnchorPane(noteLabel, archiveCheckBox, Label("Archived"))

        if (note.isArchived) {
            noteAnchorPane.background = Background(BackgroundFill(Color.LIGHTGRAY, CornerRadii(10.0), null))
        } else {
            noteAnchorPane.background = Background(BackgroundFill(Color.LIGHTYELLOW, CornerRadii(10.0), null))
        }

        archiveCheckBox.selectedProperty().addListener {
                _, _, newValue -> noteAnchorPane.background = Background(BackgroundFill(if (newValue.not()) Color.LIGHTYELLOW else Color.LIGHTGRAY, CornerRadii(10.0), null))
        }
        noteAnchorPane.setPrefSize(225.0, 225.0)
        AnchorPane.setLeftAnchor(noteAnchorPane.children[0], 10.0)
        AnchorPane.setRightAnchor(noteAnchorPane.children[0], 10.0)
        AnchorPane.setTopAnchor(noteAnchorPane.children[0], 10.0)
        AnchorPane.setBottomAnchor(noteAnchorPane.children[1], 10.0)
        AnchorPane.setLeftAnchor(noteAnchorPane.children[1], 10.0)
        AnchorPane.setBottomAnchor(noteAnchorPane.children[2], 10.0)
        AnchorPane.setLeftAnchor(noteAnchorPane.children[2], 35.0)

        return noteAnchorPane
    }

    override fun start(stage: Stage) {
        // states
        var isListView = true

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

        // notes
        var notes = mutableListOf<Note>(
            Note("note1 Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut sed magna sed elit molestie rutrum nec sed purus. Etiam scelerisque magna orci, et blandit nunc egestas sagittis. Aliquam nulla purus, malesuada in pretium vitae, commodo facilisis nunc. Aenean vel lacus at sapien facilisis mattis non quis diam. Quisque et velit ipsum. Suspendisse molestie pharetra nisi a egestas. Pellentesque justo elit, mollis ac nulla ultrices, consequat aliquam nisi. Aenean euismod sodales commodo. Donec congue pharetra purus ut sollicitudin. Sed porta enim vel justo finibus rhoncus sit amet nec dui. Cras feugiat, turpis in rutrum lacinia, elit odio imperdiet neque, ac venenatis dui metus ut sapien. Donec vulputate, nisl a placerat sagittis, augue felis ornare nisl, viverra varius tortor lorem eu est."),
            Note("note2", true),
            Note("note3 Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ante sapien, dapibus in iaculis ut, tempor ut lacus. Vivamus efficitur mollis eros, eget bibendum felis venenatis sit amet. Maecenas vitae tortor odio. Praesent finibus risus et urna vehicula, eu ultricies purus venenatis. Duis risus sapien, tincidunt in euismod eget, laoreet sit amet sapien."),
            Note("note4 Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ante sapien, dapibus in iaculis ut, tempor ut lacus. Vivamus efficitur mollis eros, eget bibendum felis venenatis sit amet. Maecenas vitae tortor odio. Praesent finibus risus et urna vehicula, eu ultricies purus venenatis. Duis risus sapien, tincidunt in euismod eget, laoreet sit amet sapien."),
            Note("note5 Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut sed magna sed elit molestie rutrum nec sed purus. Etiam scelerisque magna orci, et blandit nunc egestas sagittis. Aliquam nulla purus, malesuada in pretium vitae, commodo facilisis nunc. Aenean vel lacus at sapien facilisis mattis non quis diam. Quisque et velit ipsum. Suspendisse molestie pharetra nisi a egestas. Pellentesque justo elit, mollis ac nulla ultrices, consequat aliquam nisi. Aenean euismod sodales commodo. Donec congue pharetra purus ut sollicitudin. Sed porta enim vel justo finibus rhoncus sit amet nec dui. Cras feugiat, turpis in rutrum lacinia, elit odio imperdiet neque, ac venenatis dui metus ut sapien. Donec vulputate, nisl a placerat sagittis, augue felis ornare nisl, viverra varius tortor lorem eu est."))

        // notes list view
        var notePaneList = VBox()
        var notesListView = CreateNotes(notes, true)
        for (note in notesListView) {
            notePaneList.children.add(note)
        }
        notePaneList.spacing = 10.0

        // notes grid view
        var notePaneGrid = FlowPane()
        var notesGridView = CreateNotes(notes, false)
        for (note in notesGridView) {
            notePaneGrid.children.add(note)
            FlowPane.setMargin(note, Insets(10.0, 10.0, 10.0, 10.0))
        }
        
        // put notes in scrollpane
        var notesScrollPane = if (isListView) {
            ScrollPane(notePaneList)
        } else {
            ScrollPane(notePaneGrid)
        }
        notesScrollPane.hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER;
        notesScrollPane.isFitToWidth = true

        // root pane
        BorderPane.setMargin(toolBar, Insets(10.0, 10.0, 10.0, 10.0))
        root.top = toolBar
        root.bottom = statusBar
        root.center = notesScrollPane

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