package ui.assignments.connectfour.ui

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import ui.assignments.connectfour.model.Model
import ui.assignments.connectfour.model.Model.onNextPlayer
import ui.assignments.connectfour.model.Player

class View(): Pane(), InvalidationListener {
    init {
        Model.addListener(this)
        invalidated(null)
    }

    override fun invalidated(observable: Observable?) {
        //background = Background(BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY))
        val gridPath = javaClass.getResource("/ui/assignments/connectfour/grid_8x7.png")?.toString()
        val gridNode = ImageView(gridPath).apply {
            translateX = 100.0
        }
        this.children.add(gridNode)
    }

}

object PlayerOneControlView: VBox(), InvalidationListener {
    init {
        onNextPlayer.addListener(this)
        invalidated(null)
    }

    override fun invalidated(observable: Observable?) {
        this.children.clear()

        val playerOneLabel = Label("Player #1")
        val gridPath = javaClass.getResource("/ui/assignments/connectfour/piece_red.png")?.toString()
        val gridNode = ImageView(gridPath)
        val pane = Pane()

        if (onNextPlayer.value == Player.ONE) {
            pane.children.add(gridNode)
        }
        this.children.addAll(playerOneLabel, pane)
    }
}

object PlayerTwoControlView: VBox(), InvalidationListener {
    init {
        onNextPlayer.addListener(this)
        invalidated(null)
    }

    override fun invalidated(observable: Observable?) {
        this.children.clear()

        val playerTwoLabel = Label("Player #2")
        val gridPath = javaClass.getResource("/ui/assignments/connectfour/piece_yellow.png")?.toString()
        val gridNode = ImageView(gridPath)
        val pane = Pane()

        if (onNextPlayer.value == Player.TWO) {
            pane.children.add(gridNode)
        }
        this.children.addAll(playerTwoLabel, pane)
    }
}

object StartButtonController: Button("Click here to start game!") {
    init {
        onAction = EventHandler {
            Model.startGame()
        }
    }

}