/**
 * Decorator Pattern
 *
 * The Decorator Pattern attaches additional responsibilities to an objcet
 * dynamically. Decorators provide a flexible alternative to subclassing for
 * extending functionality.
 *
 * Design Principle
 *
 * - Classes should be open for extension but closed for modification.
 *
 */

//
// interfaces
//
interface Beverage {
    val description: String
    fun cost(): Double
}

interface CondimentDecorator : Beverage {
    var beverage: Beverage
}

//
// concert classes
//
class Espresso : Beverage {
    override val description = "Espresso"
    override fun cost(): Double {
        return 1.99
    }
}

class HouseBlend : Beverage {
    override val description = "House blend"
    override fun cost(): Double {
        return 0.89
    }
}

class DarkRoast : Beverage {
    override val description = "Dark roast"
    override fun cost(): Double {
        return 0.99
    }
}

class Decaf : Beverage {
    override val description = "Dark roast"
    override fun cost(): Double {
        return 1.49
    }
}

//
// decorators
//
class Mocha : CondimentDecorator {
    override var beverage: Beverage
    override var description: String

    constructor(beverage: Beverage) {
        this.beverage = beverage
        this.description = beverage.description + ", Mocha"
    }

    override fun cost(): Double {
        return beverage.cost() + 0.20
    }
}

class Whip : CondimentDecorator {
    override var beverage: Beverage
    override var description: String

    constructor(beverage: Beverage) {
        this.beverage = beverage
        this.description = beverage.description + ", Whip"
    }

    override fun cost(): Double {
        return beverage.cost() + 0.10
    }
}

class Soy : CondimentDecorator {
    override var beverage: Beverage
    override var description: String

    constructor(beverage: Beverage) {
        this.beverage = beverage
        this.description = beverage.description + ", Soy"
    }

    override fun cost(): Double {
        return beverage.cost() + 1.00
    }
}

// $ kotlinc code1.kt -include-runtime -d code1.jar
// $ java -jar code1.jar StarbuzzCoffee
object StarbuzzCoffee {
    @JvmStatic
    fun main(args: Array<String>) {
        println("--- decorator ---")

        val espresso = Espresso()
        println("espresso description: ${espresso.description}"
            + ", cost: ${espresso.cost()}")

        val beverage2 = Mocha(Mocha(Whip(DarkRoast())))
        println("beverage2 description: ${beverage2.description}"
            + ", cost: ${beverage2.cost()}")

        val beverage3 = Soy(Mocha(Whip(HouseBlend())))
        println("beverage3 description: ${beverage3.description}"
            + ", cost: ${beverage3.cost()}")
    }
}