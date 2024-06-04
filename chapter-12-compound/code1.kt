/**
 * Compound Pattern
 *
 * Patterns are often used together and combined within the same design
 * solution.
 *
 * A compound pattern combines two or more patterns into a solution that
 * solves a recurring or general problem.
 *
 */

interface Quackable: QuackableObserver {
    fun quack()
}

interface QuackableObserver {
    fun registerObserver(observer: Observer)
    fun notifyObservers()
}

interface Observer {
    fun update(duck: QuackableObserver)
}

abstract class AbstractDuckFactor {
    abstract fun createMallardDuck(): Quackable
    abstract fun createRedheadDuck(): Quackable
    abstract fun createDuckCall(): Quackable
    abstract fun createRubberDuck(): Quackable
}

class MallardDuck : Quackable {
    val observable: Observable

    init {
        observable = Observable(this)
    }

    override fun quack() {
        println("Quack")
        notifyObservers()
    }

    override fun registerObserver(observer: Observer) {
        observable.registerObserver(observer)
    }

    override fun notifyObservers() {
        println("Mallard Notify")
        observable.notifyObservers()
    }

    override fun toString(): String {
        return "Mallard Duck"
    }
}

class RedheadDuck : Quackable {
    val observable: Observable

    init {
        observable = Observable(this)
    }

    override fun quack() {
        println("Quack")
        notifyObservers()
    }

    override fun registerObserver(observer: Observer) {
        observable.registerObserver(observer)
    }

    override fun notifyObservers() {
        observable.notifyObservers()
    }

    override fun toString(): String {
        return "Redhead Duck"
    }
}

class DuckCall : Quackable {
    val observable: Observable

    init {
        observable = Observable(this)
    }

    override fun quack() {
        println("Kwak")
        notifyObservers()
    }

    override fun registerObserver(observer: Observer) {
        observable.registerObserver(observer)
    }

    override fun notifyObservers() {
        observable.notifyObservers()
    }

    override fun toString(): String {
        return "Duck Call"
    }
}

class RubberDuck : Quackable {
    val observable: Observable

    init {
        observable = Observable(this)
    }

    override fun quack() {
        println("Squeak")
        notifyObservers()
    }

    override fun registerObserver(observer: Observer) {
        observable.registerObserver(observer)
    }

    override fun notifyObservers() {
        observable.notifyObservers()
    }

    override fun toString(): String {
        return "Rubber Duck"
    }
}

class Goose {
    fun honk() {
        println("Honk")
    }

    override fun toString(): String {
        return "Goose"
    }
}

class GooseAdapter(
    val goose: Goose
) : Quackable {
    val observable: Observable

    init {
        observable = Observable(this)
    }

    override fun quack() {
        goose.honk()
        notifyObservers()
    }

    override fun registerObserver(observer: Observer) {
        observable.registerObserver(observer)
    }

    override fun notifyObservers() {
        observable.notifyObservers()
    }
}

class QuackCounter(
    val duck: Quackable
) : Quackable {
    val observable: Observable

    init {
        observable = Observable(this)
    }

    companion object {
        var numberOfQuacks = 0
    }

    override fun quack() {
        duck.quack()
        numberOfQuacks = numberOfQuacks + 1
    }

    override fun registerObserver(observer: Observer) {
        duck.registerObserver(observer)
    }

    override fun notifyObservers() {
        duck.notifyObservers()
    }

    override fun toString(): String {
        return duck.toString()
    }
}

class DuckFactory : AbstractDuckFactor() {
    override fun createMallardDuck(): Quackable {
        return MallardDuck()
    }
    override fun createRedheadDuck(): Quackable {
        return RedheadDuck()
    }
    override fun createDuckCall(): Quackable {
        return DuckCall()
    }
    override fun createRubberDuck(): Quackable {
        return RubberDuck()
    }
}

class CountingDuckFactory : AbstractDuckFactor() {
    override fun createMallardDuck(): Quackable {
        return QuackCounter(MallardDuck())
    }
    override fun createRedheadDuck(): Quackable {
        return QuackCounter(RedheadDuck())
    }
    override fun createDuckCall(): Quackable {
        return QuackCounter(DuckCall())
    }
    override fun createRubberDuck(): Quackable {
        return QuackCounter(RubberDuck())
    }
}

class Flock : Quackable {
    val ducks = mutableListOf<Quackable>()
    val observable: Observable

    init {
        observable = Observable(this)
    }

    fun add(quacker: Quackable) {
        ducks.add(quacker)
    }

    override fun quack() {
        val iterator = ducks.iterator()
        while (iterator.hasNext()) {
            val quacker = iterator.next()
            quacker.quack()
            notifyObservers()
        }
    }

    override fun registerObserver(observer: Observer) {
        val iterator = ducks.iterator()
        while (iterator.hasNext()) {
            val duck: Quackable = iterator.next()
            duck.registerObserver(observer)
        }
    }

    override fun notifyObservers() {}

    override fun toString(): String {
        return "Flock of Ducks"
    }
}

class Observable(
    val duck: QuackableObserver
) : QuackableObserver {
    val observers = mutableListOf<Observer>()
    
    override fun registerObserver(observer: Observer) {
        observers.add(observer)
    }

    override fun notifyObservers() {
        val iterator = observers.iterator()
        while (iterator.hasNext()) {
            val observer = iterator.next()
            observer.update(duck)
        }
    }
}

class Quackologist : Observer {
    override fun update(duck: QuackableObserver) {
        println("Quackologist: ${duck.javaClass} just quacked.")
    }
}

// kotlinc code1.kt -include-runtime -d code1.jar
// java -cp code1.jar DuckSimulatorWithDecorator
class DuckSimulatorWithDecorator {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val simulator = DuckSimulatorWithDecorator()
            val duckFactory = CountingDuckFactory()
            simulator.simulate(duckFactory)
        }
    }

    fun simulate(duckFactory: AbstractDuckFactor) {
        val mallardDuck: Quackable = duckFactory.createMallardDuck()
        val redheadDuck: Quackable = duckFactory.createRedheadDuck()
        val duckCall: Quackable = duckFactory.createDuckCall()
        val rubberDuck: Quackable = duckFactory.createRubberDuck()
        val gooseDuck: Quackable = GooseAdapter(Goose())

        println("Duck Simulator: With Decorator")

        simulate(mallardDuck)
        simulate(redheadDuck)
        simulate(duckCall)
        simulate(rubberDuck)
        simulate(gooseDuck)

        println("\nThe ducks quacked ${QuackCounter.numberOfQuacks} times")
    }

    fun simulate(duck: Quackable) {
        duck.quack()
    }
}

// kotlinc code1.kt -include-runtime -d code1.jar
// java -cp code1.jar DuckSimulatorWithComposite
class DuckSimulatorWithComposite {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val simulator = DuckSimulatorWithComposite()
            val duckFactory = CountingDuckFactory()
            simulator.simulate(duckFactory)
        }
    }

    fun simulate(duckFactory: AbstractDuckFactor) {
        val redheadDuck: Quackable = duckFactory.createRedheadDuck()
        val duckCall: Quackable = duckFactory.createDuckCall()
        val rubberDuck: Quackable = duckFactory.createRubberDuck()
        val gooseDuck: Quackable = GooseAdapter(Goose())

        println("Duck Simulator: With Composite - Flock")

        val flockOfDucks = Flock()

        flockOfDucks.add(redheadDuck)
        flockOfDucks.add(duckCall)
        flockOfDucks.add(rubberDuck)
        flockOfDucks.add(gooseDuck)

        val flockOfMallards = Flock()

        val mallardOne: Quackable = duckFactory.createMallardDuck()
        val mallardTwo: Quackable = duckFactory.createMallardDuck()
        val mallardThree: Quackable = duckFactory.createMallardDuck()
        val mallardFour: Quackable = duckFactory.createMallardDuck()

        flockOfMallards.add(mallardOne)
        flockOfMallards.add(mallardTwo)
        flockOfMallards.add(mallardThree)
        flockOfMallards.add(mallardFour)

        flockOfDucks.add(flockOfMallards)

        println("\nDuck Simulator: Whole Flock Simulation")
        simulate(flockOfDucks)

        println("\nDuck Simulator: Mallard Flock Simulation")
        simulate(flockOfMallards)

        println("\nThe ducks quacked ${QuackCounter.numberOfQuacks} times")
    }

    fun simulate(duck: Quackable) {
        duck.quack()
    }
}

class DuckSimulatorWithObserver {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val simulator = DuckSimulatorWithObserver()
            val duckFactory = CountingDuckFactory()
            simulator.simulate(duckFactory)
        }
    }

    fun simulate(duckFactory: AbstractDuckFactor) {
        val redheadDuck: Quackable = duckFactory.createRedheadDuck()
        val duckCall: Quackable = duckFactory.createDuckCall()
        val rubberDuck: Quackable = duckFactory.createRubberDuck()
        val gooseDuck: Quackable = GooseAdapter(Goose())

        val flockOfDucks = Flock()

        flockOfDucks.add(redheadDuck)
        flockOfDucks.add(duckCall)
        flockOfDucks.add(rubberDuck)
        flockOfDucks.add(gooseDuck)

        val flockOfMallards = Flock()

        val mallardOne: Quackable = duckFactory.createMallardDuck()
        val mallardTwo: Quackable = duckFactory.createMallardDuck()
        val mallardThree: Quackable = duckFactory.createMallardDuck()
        val mallardFour: Quackable = duckFactory.createMallardDuck()

        flockOfMallards.add(mallardOne)
        flockOfMallards.add(mallardTwo)
        flockOfMallards.add(mallardThree)
        flockOfMallards.add(mallardFour)

        flockOfDucks.add(flockOfMallards)

        println("\nDuck Simulator: With Observer")

        val quackologist = Quackologist()
        flockOfDucks.registerObserver(quackologist)

        simulate(flockOfDucks)

        println("\nThe ducks quacked ${QuackCounter.numberOfQuacks} times")
    }

    fun simulate(duck: Quackable) {
        duck.quack()
    }
}
