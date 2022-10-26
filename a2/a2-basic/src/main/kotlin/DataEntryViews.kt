import Model.selectedDatasetIndex
import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox

object DataEntryNameView : Label(), InvalidationListener {
    init {
        padding = Insets(10.0)
        Model.addListener(this)
        invalidated(null)
    }
    override fun invalidated(observable: Observable?) {
        text = "Dataset name: " + Model.getCurrentDatasetName()
    }
}

object DataEntryRowView: VBox(), InvalidationListener {
    private val rows = mutableListOf<HBox>()
    init {
        Model.addListener(this)
        invalidated(null)
    }

    override fun invalidated(observable: Observable?) {
        children.clear()
        rows.clear()

        // Set the data entry rows for each entry
        for (i in 0 until Model.datasets[selectedDatasetIndex].entries.size) {
            // TextField for each entry to edit values
            val currentTextField = TextField().apply {
                padding = Insets(0.0, 5.0, 0.0, 5.0)
                minHeight = 20.0
            }
            currentTextField.text = Model.datasets[selectedDatasetIndex].entries[i].toString()
            currentTextField.textProperty().addListener {
                    _, _, newValue ->
                val newValueDouble = newValue.toDoubleOrNull()
                if (newValueDouble != null) {
                    Model.datasets[selectedDatasetIndex].modifyEntryAtIndex(i, newValue.toDouble())
                    VisualizationView.invalidated(null)
                }
            }

            // Deletion Button for each entry
            val currentDeleteButton = Button("X").apply {
                if (Model.datasets[selectedDatasetIndex].entries.size == 1) {
                    isDisable = true
                }
                padding = Insets(0.0, 5.0, 0.0, 5.0)
                minHeight = 20.0
            }
            currentDeleteButton.onAction = EventHandler {
                Model.datasets[selectedDatasetIndex].removeEntryAtIndex(i)
                Model.refreshView()
                invalidated(null)
            }

            val currentRow = HBox(Label("Entry #$i").apply { padding = Insets(0.0, 5.0, 0.0, 5.0) },
                currentTextField,
                currentDeleteButton).apply {
                padding = Insets(5.0)
            }
            HBox.setHgrow(currentTextField, Priority.ALWAYS)

            rows.add(currentRow)
            children.add(rows[i])
        }
    }
}
