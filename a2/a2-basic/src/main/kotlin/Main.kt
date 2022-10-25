import javafx.application.Application
import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.scene.Scene
import javafx.scene.layout.VBox
import javafx.stage.Stage

class Main : Application()  {
    override fun start(stage: Stage) {
        val view = View()
        val ctrl = Controller()
        stage.apply {
            title = "Hello, CS349!"
            scene = Scene(VBox(view, ctrl) , 165.0, 100.0)
        }.show ()
    }
}

object Model: Observable {
    private val listeners =
        mutableListOf<InvalidationListener?>()
    private var counter = 0
    fun increment() {
        ++counter
        listeners.forEach { it?.invalidated(this) }
    }
    fun get(): Int {
        return counter
    }
    override fun addListener(listener: InvalidationListener?) {
        listeners.add(listener)
    }
    override fun removeListener(listener: InvalidationListener?) {
        listeners.remove(listener)
    }
}