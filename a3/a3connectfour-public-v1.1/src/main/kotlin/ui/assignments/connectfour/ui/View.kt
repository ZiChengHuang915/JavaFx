package ui.assignments.connectfour.ui

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseDragEvent
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.TextAlignment
import ui.assignments.connectfour.model.*
import ui.assignments.connectfour.model.Model.onNextPlayer
import ui.assignments.connectfour.model.Model.onPieceDropped

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
        val pane = Pane().apply{
            //background = Background(BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY))
            this.addEventHandler(MouseDragEvent.MOUSE_DRAGGED) {
                if (it.sceneX > 0 && it.sceneX < scene.width - 80.0) {
                    if (it.sceneX > 100 && it.sceneX <= 900) {
                        translateX = (it.sceneX.toInt() / 100 * 100 + 10).toDouble()
                    } else {
                        translateX = it.sceneX
                    }
                }
            }

            this.addEventHandler(MouseEvent.MOUSE_RELEASED) {
                if (it.sceneX > 100 && it.sceneX <= 900) {
                    val col = it.sceneX.toInt() / 100 - 1
                    Model.dropPiece(col)
                    println(onPieceDropped.value)
                }
            }
        }

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

        val playerTwoLabel = Label("Player #2").apply { textAlignment = TextAlignment.RIGHT }
        val gridPath = javaClass.getResource("/ui/assignments/connectfour/piece_yellow.png")?.toString()
        val gridNode = ImageView(gridPath)
        val pane = Pane().apply{
            //background = Background(BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY))
            this.addEventHandler(MouseDragEvent.MOUSE_DRAGGED) {
                if (it.sceneX > 0 && it.sceneX < scene.width - 80.0) {
                    if (it.sceneX > 100 && it.sceneX <= 900) {
                        translateX = (it.sceneX.toInt() / 100 * 100 + 10).toDouble() - (scene.width - 80.0)
                    } else {
                        translateX = it.sceneX - (scene.width - 80.0)
                    }
                }
            }

            this.addEventHandler(MouseEvent.MOUSE_RELEASED) {
                if (it.sceneX > 100 && it.sceneX <= 900) {
                    val col = it.sceneX.toInt() / 100 - 1
                    Model.dropPiece(col)
                    println(onPieceDropped.value)
                }
            }
        }

        if (onNextPlayer.value == Player.TWO) {
            pane.children.add(gridNode)
        }
        this.children.addAll(playerTwoLabel, pane)
        this.alignment = Pos.TOP_RIGHT
    }
}

object StartButtonController: Button("Click here to start game!") {
    init {
        onAction = EventHandler {
            Model.startGame()
            isVisible = false
        }
    }

}