package ui.assignments.connectfour

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.stage.Stage

class ConnectFourApp : Application() {
    override fun start(stage: Stage) {
        val scene = Scene(Pane(), 800.0, 600.0)
        stage.title = "CS349 - A3 Connect Four - watiam"
        stage.scene = scene
        stage.isResizable = false
        stage.show()
    }
}