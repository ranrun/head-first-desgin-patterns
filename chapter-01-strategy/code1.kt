/**
 * Strategy Anti-pattern
 */

interface Duck {
    fun fly()
    fun quack()
}

class Mallard() : Duck {
    override fun fly() {
        println("Mallard fly")
    }
    override fun quack() {
        println("Mallard quack")
    }
}

class WildTurkey() : Duck {
    override fun fly() {
        println("Wild turkey short flight")
    }
    override fun quack() {
        println("Gobble, gobble")
    }
}

//
// main
//
fun main() {
    val mallard = Mallard()
    mallard.fly()
    mallard.quack()

    val wildTurkey = WildTurkey()
    wildTurkey.fly()
    wildTurkey.quack()
}
