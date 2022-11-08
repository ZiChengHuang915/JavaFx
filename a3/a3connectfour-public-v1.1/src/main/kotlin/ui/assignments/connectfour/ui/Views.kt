package ui.assignments.connectfour.ui

import javafx.animation.Interpolator
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.animation.TranslateTransition
import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.input.MouseDragEvent
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.text.TextAlignment
import javafx.util.Duration
import ui.assignments.connectfour.model.*
import ui.assignments.connectfour.model.Model.onGameDraw
import ui.assignments.connectfour.model.Model.onGameWin
import ui.assignments.connectfour.model.Model.onNextPlayer
import ui.assignments.connectfour.model.Model.onPieceDropped

object GridView: Pane(), InvalidationListener {
    init {
        invalidated(null)
    }

    fun unsuccessfulDropAnimation(currentX: Double) {
        val animation = TranslateTransition(Duration(500.0)).apply {
            interpolator = Interpolator.EASE_BOTH
            isAutoReverse = false
            cycleCount = 1
        }

        val drawable = Circle(currentX, 50.0, 40.0).apply {
            if (onNextPlayer.value == Player.ONE) {
                fill = Color.RED
            } else {
                fill = Color.YELLOW
            }
        }
        this.children.add(drawable)
        if (onNextPlayer.value == Player.ONE) {
            animation.byX = -currentX
        } else {
            animation.byX = scene.width - 80.0 - currentX
        }
        animation.byY = -150.0
        animation.node = drawable
        animation.setOnFinished {
            this.children.remove(drawable)
        }
        animation.play()
    }

    fun dropAnimation(row: Int, player: Player) {
        val animation = TranslateTransition(Duration(500.0)).apply {
            interpolator = Interpolator.EASE_BOTH
            isAutoReverse = false
            cycleCount = 1
        }

        val drawable = Circle(150.0 + row * 100, 50.0, 40.0).apply {
            if (player == Player.ONE) {
                fill = Color.RED
            } else {
                fill = Color.YELLOW
            }
        }
        this.children.add(drawable)
        animation.byY = onPieceDropped.value!!.y * 100.toDouble()
        animation.node = drawable
        animation.play()
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
            this.addEventHandler(MouseDragEvent.MOUSE_DRAGGED) {
                if (onGameWin.value == Player.NONE && onGameDraw.value == false) {
                    if (it.sceneX > 0 && it.sceneX < scene.width - 80.0) {
                        if (it.sceneX > 100 && it.sceneX <= 900) {
                            translateX = (it.sceneX.toInt() / 100 * 100 + 10).toDouble()
                        } else {
                            translateX = it.sceneX
                        }
                    }
                }
            }

            this.addEventHandler(MouseEvent.MOUSE_RELEASED) {
                if (onGameWin.value == Player.NONE && onGameDraw.value == false) {
                    if (it.sceneX > 100 && it.sceneX <= 900) {
                        val col = it.sceneX.toInt() / 100 - 1
                        Model.dropPiece(col)

                        if (onPieceDropped.value != null) {
                            GridView.dropAnimation(onPieceDropped.value!!.x, onPieceDropped.value!!.player)
                        } else {
                            GridView.unsuccessfulDropAnimation(it.sceneX)
                            translateX = 0.0
                            this.isVisible = false

                            val timeline = Timeline(KeyFrame(Duration.millis(500.0), {
                                this.isVisible = true
                            }))
                            timeline.cycleCount = 1
                            timeline.play()
                        }
                    }
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
                if (onGameWin.value == Player.NONE && onGameDraw.value == false) {
                    if (it.sceneX > 0 && it.sceneX < scene.width - 80.0) {
                        if (it.sceneX > 100 && it.sceneX <= 900) {
                            translateX = (it.sceneX.toInt() / 100 * 100 + 10).toDouble() - (scene.width - 80.0)
                        } else {
                            translateX = it.sceneX - (scene.width - 80.0)
                        }
                    }
                }
            }

            this.addEventHandler(MouseEvent.MOUSE_RELEASED) {
                if (onGameWin.value == Player.NONE && onGameDraw.value == false) {
                    if (it.sceneX > 100 && it.sceneX <= 900) {
                        val col = it.sceneX.toInt() / 100 - 1
                        Model.dropPiece(col)

                        if (onPieceDropped.value != null) {
                            GridView.dropAnimation(onPieceDropped.value!!.x, onPieceDropped.value!!.player)
                        } else {
                            GridView.unsuccessfulDropAnimation(it.sceneX)
                            translateX = 0.0
                            this.isVisible = false

                            val timeline = Timeline(KeyFrame(Duration.millis(500.0), {
                                this.isVisible = true
                            }))
                            timeline.cycleCount = 1
                            timeline.play()
                        }
                    }
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

object EndView: Label(), InvalidationListener {
    init {
        isVisible = false
        textAlignment = TextAlignment.CENTER
        alignment = Pos.CENTER
        onGameDraw.addListener(this)
        onGameWin.addListener(this)
        invalidated(null)
    }
    override fun invalidated(observable: Observable?) {
        if (onGameDraw.value) {
            this.text = "Game is a Draw!"
            isVisible = true
        } else if (onGameWin.value == Player.ONE) {
            this.text = "Player #1 wins!"
            isVisible = true
        } else if (onGameWin.value == Player.TWO) {
            this.text = "Player #2 wins!"
            isVisible = true
        }
    }
}