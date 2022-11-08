package ui.assignments.connectfour

import javafx.application.Application
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.layout.Border
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import javafx.stage.Stage
import ui.assignments.connectfour.ui.*

class ConnectFourApp : Application() {
    val root = BorderPane()

    override fun start(stage: Stage) {
        root.left = PlayerOneControlView
        root.right = PlayerTwoControlView
        root.center = StartButtonController
        root.bottom = GridView
        root.top = EndView
        BorderPane.setAlignment(EndView, Pos.CENTER)

        val scene = Scene(root, 1000.0, 900.0)
        stage.title = "CS349 - A3 Connect Four - zc3huang"
        stage.scene = scene
        stage.isResizable = false
        stage.show()
    }
}