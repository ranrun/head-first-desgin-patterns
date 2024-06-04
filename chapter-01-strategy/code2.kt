/**
 * Strategy Pattern
 *
 * The Strategy Pattern defines a family of algorithms, encapsulates each one,
 * and makes them interchangeable Strategy let the algorithm vary independently
 * from client that use it.
 *
 * Design Principles
 *
 * - Identify the aspects  of your application that vary and separate them from
 *   what stays the same.
 * - Program to an interface, not an implementation.
 * - Favor composition over inheritance.
 *
 */

//
// behavior interfaces
//
interface FlyBehavior {
    fun fly()
}

interface QuackBehavior {
    fun quack()
}

//
// behaviors implementations
//
class FlyWithWings(): FlyBehavior {
    override fun fly() {
        println("I'm flying!!")
    }
}

class FlyNoWay(): FlyBehavior {
    override fun fly() {
        println("I can't fly")
    }
}

class FlyRocketPowered() : FlyBehavior {
    override fun fly() {
        println("I'm flying with a rocket!")
    }
}

class Quack(): QuackBehavior {
    override fun quack() {
        println("Quack")
    }
}

class MuteQuack(): QuackBehavior {
    override fun quack() {
        println("<< Silence >>")
    }
}

class Squeak(): QuackBehavior {
    override fun quack() {
        println("Squeak")
    }
}

//
// ducks
//
abstract class Duck {
    var flyBehavior: FlyBehavior = FlyWithWings()
    var quackBehavior: QuackBehavior = Quack()
    
    fun performFly() {
        flyBehavior.fly()
    }

    fun performQuack() {
        quackBehavior.quack()
    }

    fun setMyBehaviorFly(fb: FlyBehavior) {
        this.flyBehavior = fb
    }

    fun setBehaviorQuack(qb: QuackBehavior) {
        this.quackBehavior = qb
    }

    fun swim() {
        println("All ducks float, even decoys!")
    }
}

class MallardDuck(): Duck() {
    init {
        flyBehavior = FlyWithWings()
        quackBehavior = Quack()
    }

    fun display() {
        println("I'm a real Mallard duck")
    }
}

class RubberDucky(): Duck() {
    init {
        flyBehavior = FlyNoWay()
        quackBehavior = Squeak()
    }

    fun display() {
        println("Rubber ducky is so much fun!")
    }
}

class ModelDuck(): Duck() {
    init {
        flyBehavior = FlyNoWay()
        quackBehavior = Quack()
    }

    fun display() {
        println("I'm a model duck")
    }
}

// $ kotlinc code2.kt -include-runtime -d code2.jar
// $ java -jar code2.jar DuckSimulator
object DuckSimulator {
    @JvmStatic
    fun main(args: Array<String>) {
        println("--- Mallard Duck ---")
        val mallardDuck = MallardDuck()
        mallardDuck.performFly()
        mallardDuck.performQuack()
        mallardDuck.swim()
        mallardDuck.display()

        println("--- Rubber Ducky ---")
        val rubberDucky = RubberDucky()
        rubberDucky.performFly()
        rubberDucky.performQuack()
        rubberDucky.swim()
        rubberDucky.display()

        println("--- Model Duck ---")
        val modelDuck = ModelDuck()
        modelDuck.performFly()
        modelDuck.setMyBehaviorFly(FlyRocketPowered())
        modelDuck.performFly()
        modelDuck.performQuack()
        modelDuck.swim()
        modelDuck.display()
    }
}
