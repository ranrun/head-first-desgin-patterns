/**
 * Command Pattern
 *
 * The Command Pattern encapsulates a request as an object, thereby letting you
 * parameterize other objects with different requests, queue or log requests,
 * and support undoable operations.
 *
 */

// command
interface Command {
    fun execute()
}

// receiver
class Light {
    fun on() {
        println("Light is on")
    }
    fun off() {
        println("Light is off")
    }
}

// invoker
class LightOnCommand(
    val light: Light
): Command {
    override fun execute() {
        light.on()
    }
}

class LightOffCommand(
    val light: Light
): Command {
    override fun execute() {
        light.off()
    }
}

// receiver
class GarageDoor {
    fun up() {
        println("Garage door opening")
    }
    fun down() {
        println("Garage door closing")
    }
    fun stop() {
        println("Garage door stopping")
    }
    fun lightOn() {
        println("Garage door light on")
    }
    fun lightOff() {
        println("Garage door light off")
    }
}

// invoker
class GarageDoorOpenCommand(
    val garageDoor: GarageDoor
): Command {
    override fun execute() {
        garageDoor.up()
    }
}

// client
class SimpleRemoteControl {
    var slot: Command? = null

    fun setCommand(command: Command) {
        slot = command
    }

    fun buttonWasPressed() {
        slot?.execute() ?: println("Cannot execute a null slot")
    }
}

// $ kotlinc code1.kt -include-runtime -d code1.jar
// $ java -jar code1.jar RemoteLoader
object RemoteLoader {
    @JvmStatic
    fun main(args: Array<String>) {
        val remote = SimpleRemoteControl()
        val light = Light()
        val garageDoor = GarageDoor()
        // concrete command
        val lightOn = LightOnCommand(light)
        // concrete command
        val garageOpen = GarageDoorOpenCommand(garageDoor)

        remote.setCommand(lightOn)
        remote.buttonWasPressed()
        remote.setCommand(garageOpen)
        remote.buttonWasPressed()
    }
}
