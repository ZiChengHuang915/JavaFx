import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.stage.Stage

class Main : Application()  {
    override fun start(stage: Stage) {
        val root = GridPane()
        val statusBar = Label("temp label")
        root.add(statusBar, 0, 0)

        stage.title = "CS349 - A1 Notes - zc3huang"
        stage.scene = Scene(root, 800.0, 600.0)
        stage.show()
        stage.apply {
            minWidth = 640.0
            minHeight = 480.0
        }

    }
}