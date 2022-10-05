import javafx.application.Application
import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.stage.Stage

class Main : Application()  {
    private val root = BorderPane()
    private var isListView = true
    private var isArchivedChecked = true
    private var sortAscending = true

    // notes
    private val notes = mutableListOf<Note>(
        Note("note1 Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut sed magna sed elit molestie rutrum nec sed purus. Etiam scelerisque magna orci, et blandit nunc egestas sagittis. Aliquam nulla purus, malesuada in pretium vitae, commodo facilisis nunc. Aenean vel lacus at sapien facilisis mattis non quis diam. Quisque et velit ipsum. Suspendisse molestie pharetra nisi a egestas. Pellentesque justo elit, mollis ac nulla ultrices, consequat aliquam nisi. Aenean euismod sodales commodo. Donec congue pharetra purus ut sollicitudin. Sed porta enim vel justo finibus rhoncus sit amet nec dui. Cras feugiat, turpis in rutrum lacinia, elit odio imperdiet neque, ac venenatis dui metus ut sapien. Donec vulputate, nisl a placerat sagittis, augue felis ornare nisl, viverra varius tortor lorem eu est. Donec congue pharetra purus ut sollicitudin. Sed porta enim vel justo finibus rhoncus sit amet nec dui. Cras feugiat, turpis in rutrum lacinia, elit odio imperdiet neque, ac venenatis dui metus ut sapien. Donec vulputate, nisl a placerat sagittis, augue felis ornare nisl, viverra varius tortor lorem eu est."),
        Note("note2", true),
        Note("note3 Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ante sapien, dapibus in iaculis ut, tempor ut lacus."),
        Note("note4 Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ante sapien, dapibus in iaculis ut, tempor ut lacus. Vivamus efficitur mollis eros, eget bibendum felis venenatis sit amet. Maecenas vitae tortor odio. Praesent finibus risus et urna vehicula, eu ultricies purus venenatis. Duis risus sapien, tincidunt in euismod eget, laoreet sit amet sapien."),
        Note("note5 Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut sed magna sed elit molestie rutrum nec sed purus. Etiam scelerisque magna orci, et blandit nunc egestas sagittis. Aliquam nulla purus, malesuada in pretium vitae, commodo facilisis nunc. Aenean vel lacus at sapien facilisis mattis non quis diam. Quisque et velit ipsum. Suspendisse molestie pharetra nisi a egestas. Pellentesque justo elit, mollis ac nulla ultrices, consequat aliquam nisi. Aenean euismod sodales commodo. Donec congue pharetra purus ut sollicitudin. Sed porta enim vel justo finibus rhoncus sit amet nec dui. Cras feugiat, turpis in rutrum lacinia, elit odio imperdiet neque, ac venenatis dui metus ut sapien. Donec vulputate, nisl a placerat sagittis, augue felis ornare nisl, viverra varius tortor lorem eu est."))
    // status bar
    var statusBar = Label(getStatusString())
    // notes list view
    private val notePaneList = VBox()
    private var notesListView = createNotes(notes, true)
    // notes grid view
    private val notePaneGrid = FlowPane()
    private var notesGridView = createNotes(notes, false)

    class Note(var text: String, var isArchived: Boolean = false)

    private fun createNotes(notes: MutableList<Note>, isListView: Boolean): MutableList<AnchorPane> {
        if (sortAscending) {
            notes.sortBy { n -> n.text.length }
        } else {
            notes.sortByDescending { n -> n.text.length }
        }

        val noteList = mutableListOf<AnchorPane>()
        for (note in notes) {
            if (isListView) {
                if (isArchivedChecked || !isArchivedChecked && !note.isArchived) {
                    noteList.add(createNoteListView(note))
                }
            } else {
                if (isArchivedChecked || !isArchivedChecked && !note.isArchived) {
                    noteList.add(createNoteGridView(note))
                }
            }
        }

        if (isListView) {
            noteList.add(0, createSpecialNoteListView(Note("")))
        } else {
            noteList.add(0, createSpecialNoteGridView(Note("")))
        }

        return noteList
    }

    private fun createSpecialNoteListView(note: Note): AnchorPane {
        val noteTextArea = TextArea().apply {
            this.prefHeight = 42.0
        }
        val createButton = Button("Create")
        createButton.setPrefSize(75.0, 42.0)
        createButton.onAction = EventHandler {
            val newNote = Note(noteTextArea.text)
            notes.add(newNote)
            statusBar.text = getStatusString()
            reloadRoot()
        }

        val noteAnchorPane = AnchorPane(noteTextArea, createButton).apply {
            this.border = Border(BorderStroke(Color.LIGHTSALMON, BorderStrokeStyle.SOLID, CornerRadii(10.0), BorderWidths(10.0)))
            this.prefHeight = 62.0
        }
        AnchorPane.setLeftAnchor(noteAnchorPane.children[0], 0.0)
        AnchorPane.setTopAnchor(noteAnchorPane.children[0], 0.0)
        AnchorPane.setRightAnchor(noteAnchorPane.children[0], 95.0)
        AnchorPane.setTopAnchor(noteAnchorPane.children[1], 0.0)
        AnchorPane.setRightAnchor(noteAnchorPane.children[1], 0.0)

        return noteAnchorPane
    }

    private fun createNoteListView(note: Note): AnchorPane {
        val noteLabel = Label(note.text)
        noteLabel.isWrapText = true
        noteLabel.prefWidth = 500.0

        val archiveCheckBox = CheckBox()
        archiveCheckBox.selectedProperty().addListener {
                _, _, newValue -> note.isArchived = newValue
            statusBar.text = getStatusString()
        }
        archiveCheckBox.isSelected = note.isArchived

        val noteAnchorPane = AnchorPane(noteLabel, archiveCheckBox, Label("Archived"))

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

    private fun createSpecialNoteGridView(note: Note): AnchorPane {
        val noteTextArea = TextArea()
        val createButton = Button("Create")
        createButton.setPrefSize(205.0, 20.0)
        createButton.onAction = EventHandler {
            val newNote = Note(noteTextArea.text)
            notes.add(newNote)
            statusBar.text = getStatusString()
            reloadRoot()
        }

        val noteAnchorPane = AnchorPane(noteTextArea, createButton).apply {
            this.border = Border(BorderStroke(Color.LIGHTSALMON, BorderStrokeStyle.SOLID, CornerRadii(10.0), BorderWidths(10.0)))
            this.setPrefSize(225.0, 225.0)
        }
        AnchorPane.setLeftAnchor(noteAnchorPane.children[0], 0.0)
        AnchorPane.setTopAnchor(noteAnchorPane.children[0], 0.0)
        AnchorPane.setRightAnchor(noteAnchorPane.children[0], 0.0)
        AnchorPane.setBottomAnchor(noteAnchorPane.children[1], 0.0)
        AnchorPane.setLeftAnchor(noteAnchorPane.children[1], 0.0)
        AnchorPane.setRightAnchor(noteAnchorPane.children[1], 0.0)

        return noteAnchorPane
    }

    private fun createNoteGridView(note: Note): AnchorPane {
        val noteLabel = Label(note.text)
        noteLabel.isWrapText = true
        noteLabel.setPrefSize(225.0, 180.0)

        val archiveCheckBox = CheckBox()
        archiveCheckBox.selectedProperty().addListener {
                _, _, newValue -> note.isArchived = newValue
            statusBar.text = getStatusString()
        }
        archiveCheckBox.isSelected = note.isArchived

        val noteAnchorPane = AnchorPane(noteLabel, archiveCheckBox, Label("Archived"))

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

    private fun reloadRoot() {
        if (isListView) {
            notePaneList.children.clear()
        } else {
            notePaneGrid.children.clear()
        }

        notesListView.clear()
        notesGridView.clear()
        notesListView = createNotes(notes, true)
        notesGridView = createNotes(notes, false)
        for (note in notesListView) {
            notePaneList.children.add(note)
        }
        notePaneList.spacing = 10.0

        for (note in notesGridView) {
            notePaneGrid.children.add(note)
            FlowPane.setMargin(note, Insets(10.0, 10.0, 10.0, 10.0))
        }

        // put notes in scrollpane
        val notesScrollPane = if (isListView) {
            ScrollPane(notePaneList)
        } else {
            ScrollPane(notePaneGrid)
        }
        notesScrollPane.hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        notesScrollPane.isFitToWidth = true

        root.center = notesScrollPane
    }

    private fun getStatusString(): String {
        var archivedNotes = 0
        for (note in notes) {
            if (!note.isArchived) {
                archivedNotes++
            }
        }

        return "${notes.size} note${if (notes.size == 1) "" else "s"}, $archivedNotes of which ${if (archivedNotes == 1) "is" else "are"} active"
    }

    override fun start(stage: Stage) {
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
            _, _, newValue -> isArchivedChecked = newValue
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

        reloadRoot()
        // root pane
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