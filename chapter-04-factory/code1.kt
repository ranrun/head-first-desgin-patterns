/**
 * Factory Pattern
 *
 * The Factor Method Pattern defines an interface for creating an object, but
 * lets subclasses decide which class to instantiate. Factory Method lets a
 * class defer instantiation to subclasses.
 *
 * Design Principle
 *
 * - Depend on abstractions. Do not denpend on concrete classes.
 *
 */

interface Clams
class FreshClams() : Clams
class FrozenClams() : Clams

interface Cheese
class MozzarellaCheese() : Cheese
class ReggianoCheese() : Cheese

interface Dough {}
class ThinCrustDough() : Dough
class ThickCrustDough() : Dough

interface Pepperoni
class SlicedPepperoni() : Pepperoni

interface Sauce
class PlumTomatoSauce() : Sauce
class MarinaraSauce() : Sauce

interface Veggies
class BlackOlives() : Veggies
class EggPlant() : Veggies
class Garlic() : Veggies
class Mushroom() : Veggies
class Onion() : Veggies
class RedPepper() : Veggies
class Spinach() : Veggies

interface PizzaIngredientFactory {
    fun createDough(): Dough
    fun createSauce(): Sauce
    fun createCheese(): Cheese
    fun createVeggies(): List<Veggies>
    fun createPepperoni(): Pepperoni
    fun createClams(): Clams
}

class NYPizzaIngredientFactory : PizzaIngredientFactory {
    override fun createDough(): Dough {
        return ThinCrustDough()
    }

    override fun createSauce(): Sauce {
        return MarinaraSauce()
    }

    override fun createCheese(): Cheese {
        return ReggianoCheese()
    }

    override fun createVeggies(): List<Veggies> {
        return listOf<Veggies>(Garlic(), Onion(), Mushroom(), RedPepper())
    }

    override fun createPepperoni(): Pepperoni {
        return SlicedPepperoni()
    }

    override fun createClams(): Clams {
        return FreshClams()
    }
}

class ChicagoPizzaIngredientFactory : PizzaIngredientFactory {
    override fun createDough(): Dough {
        return ThickCrustDough()
    }

    override fun createSauce(): Sauce {
        return PlumTomatoSauce()
    }

    override fun createCheese(): Cheese {
        return MozzarellaCheese()
    }

    override fun createVeggies(): List<Veggies> {
        return listOf<Veggies>(BlackOlives(), EggPlant(), Spinach())
    }

    override fun createPepperoni(): Pepperoni {
        return SlicedPepperoni()
    }

    override fun createClams(): Clams {
        return FrozenClams()
    }
}

abstract class Pizza {
    var name: String? = null
    var clams: Clams? = null
    var cheese: Cheese? = null
    var dough: Dough? = null
    var sauce: Sauce? = null
    var pepperoni: Pepperoni? = null
    var veggies: List<Veggies>? = null

    abstract fun prepare()

    fun bake() {
        println("Bake for 25 minutes at 350")
    }

    fun cut() {
        println("Cutting the pizaa into diagonal slices")
    }

    fun box() {
        println("Place pizza in offical PizzaStore box")
    }

    override fun toString(): String {
        return "name: $name, cheese: $cheese, dough: $dough, sauce: $sauce, veggies: $veggies, clams: $clams"
    }
}

class CheesePizza(val ingredientFactory: PizzaIngredientFactory) : Pizza() {
    override fun prepare() {
        cheese = ingredientFactory.createCheese()
        clams = ingredientFactory.createClams()
        dough = ingredientFactory.createDough()
        sauce = ingredientFactory.createSauce()
    }
}

class ClamPizza(val ingredientFactory: PizzaIngredientFactory) : Pizza() {
    override fun prepare() {
        cheese = ingredientFactory.createCheese()
        clams = ingredientFactory.createClams()
        dough = ingredientFactory.createDough()
        sauce = ingredientFactory.createSauce()
        veggies = ingredientFactory.createVeggies()
    }
}

class PepperoniPizza(val ingredientFactory: PizzaIngredientFactory) : Pizza() {
    override fun prepare() {
        cheese = ingredientFactory.createCheese()
        clams = ingredientFactory.createClams()
        dough = ingredientFactory.createDough()
        sauce = ingredientFactory.createSauce()
        veggies = ingredientFactory.createVeggies()
    }
}

class VeggiePizza(val ingredientFactory: PizzaIngredientFactory) : Pizza() {
    override fun prepare() {
        cheese = ingredientFactory.createCheese()
        clams = ingredientFactory.createClams()
        dough = ingredientFactory.createDough()
        sauce = ingredientFactory.createSauce()
        veggies = ingredientFactory.createVeggies()
    }
}

interface PizzaStore {
    fun createPizza(item: String): Pizza
}

class NYPizzaStore : PizzaStore {
    override fun createPizza(item: String) : Pizza {
        val nyPizzaIngredientFactory = NYPizzaIngredientFactory()
        var pizza: Pizza? = null
        if (item == "cheese") {
            pizza = CheesePizza(nyPizzaIngredientFactory)
            pizza.name = "New York Style Cheese Pizza"
        } else if (item == "clam") {
            pizza = ClamPizza(nyPizzaIngredientFactory)
            pizza.name = "New York Style Clam Pizza"
        } else if (item == "pepperoni") {
            pizza = PepperoniPizza(nyPizzaIngredientFactory)
            pizza.name = "New York Style Pepperoni Pizza"
        } else if (item == "veggie") {
            pizza = VeggiePizza(nyPizzaIngredientFactory)
            pizza.name = "New York Style Veggie Pizza"
        }
        return pizza!!
    }
}

class ChicagoPizzaStore : PizzaStore {
    override fun createPizza(item: String) : Pizza {
        val chicagoPizzaIngredientFactory = ChicagoPizzaIngredientFactory()
        var pizza: Pizza? = null
        if (item == "cheese") {
            pizza = CheesePizza(chicagoPizzaIngredientFactory)
            pizza.name = "Chicago Style Cheese Pizza"
        } else if (item == "clam") {
            pizza = ClamPizza(chicagoPizzaIngredientFactory)
            pizza.name = "Chicago Style Clam Pizza"
        } else if (item == "pepperoni") {
            pizza = PepperoniPizza(chicagoPizzaIngredientFactory)
            pizza.name = "Chicago Style Pepperoni Pizza"
        } else if (item == "veggie") {
            pizza = VeggiePizza(chicagoPizzaIngredientFactory)
            pizza.name = "Chicago Style Veggie Pizza"
        }
        return pizza!!
    }
}

// $ kotlinc code1.kt -include-runtime -d code1.jar
// $ java -jar code1.jar PizzaTestDrive
object PizzaTestDrive {
    @JvmStatic
    fun main(args: Array<String>) {
        val nyPizzaFactory = NYPizzaStore()
        var cheesePizza = nyPizzaFactory.createPizza("cheese")
        cheesePizza.prepare()
        println("cheesePizza: $cheesePizza")

        var clamPizza = nyPizzaFactory.createPizza("clam")
        clamPizza.prepare()
        println("clamPizza: $clamPizza")

        var veggiePizza = nyPizzaFactory.createPizza("veggie")
        veggiePizza.prepare()
        println("veggiePizza: $veggiePizza")

        val chicagoPizzaFactory = ChicagoPizzaStore()
        cheesePizza = chicagoPizzaFactory.createPizza("cheese")
        cheesePizza.prepare()
        println("cheesePizza: $cheesePizza")

        clamPizza = chicagoPizzaFactory.createPizza("clam")
        clamPizza.prepare()
        println("clamPizza: $clamPizza")

        veggiePizza = chicagoPizzaFactory.createPizza("veggie")
        veggiePizza.prepare()
        println("veggiePizza: $veggiePizza")
    }
}