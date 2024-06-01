//
// adapter pattern
//

interface Duck {
    fun quack()
    fun fly()
}

class MallardDuck : Duck {
    override fun quack() {
        println("Quack")
    }
    override fun fly() {
        println("I'm flying")
    }
}

interface Turkey {
    fun gobble()
    fun fly()
}

class WildTurkey : Turkey {
    override fun gobble() {
        println("Gobble gobble")
    }
    override fun fly() {
        println("I'm flying a short distance")
    }
}

class TurkeyAdapter(
    val turkey: Turkey
) : Duck {
    override fun quack() {
        turkey.gobble()
    }
    override fun fly() {
        List(5) {
            turkey.fly()
        }
    }
}

fun testDuck(duck: Duck) {
    duck.quack()
    duck.fly()
}

//
// main
//
fun main() {
    val duck = MallardDuck()

    val turkey = WildTurkey()
    val turkeyAdapter = TurkeyAdapter(turkey)

    println("The Turkey says...")
    turkey.gobble()
    turkey.fly()

    println("The Duck says...")
    testDuck(duck)

    println("The TurkeyAdapter says...")
    testDuck(turkeyAdapter)
}